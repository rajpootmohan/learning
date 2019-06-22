# Designing Facebook’s Newsfeed

## 1. What is Facebook’s newsfeed?
A Newsfeed is the constantly updating list of stories in the middle of Facebook’s homepage. It includes status updates, photos, videos, links, app activity, and ‘likes’ from people, pages, and groups that a user follows on Facebook. In other words, it is a compilation of a complete scrollable version of your friends’ and your life story from photos, videos, locations, status updates, and other activities.

## 2. Requirements and Goals of the System

### Functional requirements:
1. Newsfeed will be generated based on the posts from the people, pages, and groups that a user follows.
2. A user may have many friends and follow a large number of pages/groups.
3. Feeds may contain images, videos, or just text.
4. Our service should support appending new posts as they arrive to the newsfeed for all active users.

### Non-functional requirements:
1. Our system should be able to generate any user’s newsfeed in real-time - maximum latency seen by the end user would be 2s.
2. A post shouldn’t take more than 5s to make it to a user’s feed assuming a new newsfeed request comes in.

## 3. Capacity Estimation and Constraints

__Traffic estimates__: Let’s assume 300M daily active users with each user fetching their timeline an average of five times a day. This will result in 1.5B newsfeed requests per day or approximately 17,500 requests per second.

__Storage estimates__: On average, let’s assume we need to have around 500 posts in every user’s feed that we want to keep in memory for a quick fetch. Let’s also assume that on average each post would be 1KB in size. This would mean that we need to store roughly 500KB of data per user. To store all this data for all the active users we would need 150TB of memory. If a server can hold 100GB we would need around 1500 machines to keep the top 500 posts in memory for all active users.

## 4. System APIs
`getUserFeed(api_dev_key, user_id, since_id, count, max_id, exclude_replies)`

## 5. Database Design

There are three primary objects: User, Entity (e.g. page, group, etc.), and FeedItem (or Post). Here are some observations about the relationships between these entities:

1. A User can follow other entities and can become friends with other users.
2. Both users and entities can post FeedItems which can contain text, images, or videos.
3. Each FeedItem will have a UserID which will point to the User who created it. For simplicity, let’s assume that only users can create feed items, although, on Facebook Pages can post feed item too.
4. Each FeedItem can optionally have an EntityID pointing to the page or the group where that post was created.

![Facebook newsfeed database design](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/facebook_newsfeed_db_design.png)

## 6. High Level System Design

### 1. Feed generation

Newsfeed is generated from the posts (or feed items) of users and entities (pages and groups) that a user follows. So, whenever our system receives a request to generate the feed for a user (say Jane), we will perform the following steps:

1. Retrieve IDs of all users and entities that Jane follows.
2. Retrieve latest, most popular and relevant posts for those IDs. These are the potential posts that we can show in Jane’s newsfeed.
3. Rank these posts based on the relevance to Jane. This represents Jane’s current feed.
4. Store this feed in the cache and return top posts (say 20) to be rendered on Jane’s feed.
5. On the front-end, when Jane reaches the end of her current feed, she can fetch the next 20 posts from the server and so on.

### 2. Feed publishing

At a high level, we will need following components in our Newsfeed service:

1. Web servers: To maintain a connection with the user. This connection will be used to transfer data between the user and the server.
2. Application server: To execute the workflows of storing new posts in the database servers. We will also need some application servers to retrieve and to push the newsfeed to the end user.
3. Metadata database and cache: To store the metadata about Users, Pages, and Groups.
4. Posts database and cache: To store metadata about posts and their contents.
5. Video and photo storage, and cache: Blob storage, to store all the media included in the posts.
6. Newsfeed generation service: To gather and rank all the relevant posts for a user to generate newsfeed and store in the cache. This service will also receive live updates and will add these newer feed items to any user’s timeline.
7. Feed notification service: To notify the user that there are newer items available for their newsfeed.

![Facebook newsfeed Architecture](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/facebook_newsfeed_architecture.png)

## 7. Detailed Component Design

### A. Feed generation

Let’s take the simple case of the newsfeed generation service fetching most recent posts from all the users and entities that Jane follows. Here are issues with this design for the feed generation service:

1. Crazy slow for users with a lot of friends/follows as we have to perform sorting/merging/ranking of a huge number of posts.
2. We generate the timeline when a user loads their page. This would be quite slow and have a high latency.
For live updates, each status update will result in feed updates for all followers. This could result in high backlogs in our Newsfeed Generation Service.
3. For live updates, the server pushing (or notifying about) newer posts to users could lead to very heavy loads, especially for people or pages that have a lot of followers. To improve the efficiency, we can pre-generate the timeline and store it in a memory.

#### 1. Offline generation for newsfeed

1. We can have dedicated servers that are continuously generating users’ newsfeed and storing them in memory. So, whenever a user requests for the new posts for their feed, we can simply serve it from the pre-generated, stored location. Using this scheme, user’s newsfeed is not compiled on load, but rather on a regular basis and returned to users whenever they request for it.

2. Whenever these servers need to generate the feed for a user, they will first query to see what was the last time the feed was generated for that user. Then, new feed data would be generated from that time onwards. We can store this data in a hash table where the “key” would be UserID and “value” would be a STRUCT like this:

	```
	Struct {
	    LinkedHashMap<FeedItemID, FeedItem> feedItems;
	    DateTime lastGenerated;
	}
	```

3. We can store FeedItemIDs in a data structure similar to Linked HashMap or TreeMap, which can allow us to not only jump to any feed item but also iterate through the map easily. Whenever users want to fetch more feed items, they can send the last FeedItemID they currently see in their newsfeed, we can then jump to that FeedItemID in our hash-map and return next batch/page of feed items from there.

#### 2. How many feed items should we store in memory for a user’s feed?
we can decide to store 500 feed items per user, but this number can be adjusted later based on the usage pattern

#### 3. Should we generate (and keep in memory) newsfeeds for all users?

There will be a lot of users that don’t login frequently. Here are a few things we can do to handle this:

1. a more straightforward approach could be, to use a LRU based cache that can remove users from memory that haven’t accessed their newsfeed for a long time.
2. a smarter solution can figure out the login pattern of users to pre-generate their newsfeed, e.g., at what time of the day a user is active and which days of the week does a user access their newsfeed? etc.

### B. Feed publishing
The process of pushing a post to all the followers is called a fanout. By analogy, the push approach is called fanout-on-write, while the pull approach is called fanout-on-load.

#### 1. “Pull” model or Fan-out-on-load:
This method involves keeping all the recent feed data in memory so that users can pull it from the server whenever they need it. Clients can pull the feed data on a regular basis or manually whenever they need it. Possible problems with this approach are a) New data might not be shown to the users until they issue a pull request, b) It’s hard to find the right pull cadence, as most of the time pull requests will result in an empty response if there is no new data, causing waste of resources.

#### 2. “Push” model or Fan-out-on-write
For a push system, once a user has published a post, we can immediately push this post to all the followers. The advantage is that when fetching feed you don’t need to go through your friend’s list and get feeds for each of them. It significantly reduces read operations. To efficiently handle this, users have to maintain a Long Poll request with the server for receiving the updates. A possible problem with this approach is that when a user has millions of followers (a celebrity-user) the server has to push updates to a lot of people.

#### 3. Hybrid
An alternate method to handle feed data could be to use a hybrid approach, i.e., to do a combination of fan-out-on-write and fan-out-on-load. Specifically, we can stop pushing posts from users with a high number of followers (a celebrity user) and only push data for those users who have a few hundred (or thousand) followers. For celebrity users, we can let the followers pull the updates. Since the push operation can be extremely costly for users who have a lot of friends or followers, by disabling fanout for them, we can save a huge number of resources.

__Should we always notify users if there are new posts available for their newsfeed?__ It could be useful for users to get notified whenever new data is available. However, on mobile devices, where data usage is relatively expensive, it can consume unnecessary bandwidth. Hence, at least for mobile devices, we can choose not to push data, instead, let users “Pull to Refresh” to get new posts.

## 8. Feed Ranking

The most straightforward way to rank posts in a newsfeed is by the creation time of the posts, but today’s ranking algorithms are doing a lot more than that to ensure “important” posts are ranked higher. The high-level idea of ranking is first to select key “signals” that make a post important and then to find out how to combine them to calculate a final ranking score.

More specifically, we can select features that are relevant to the importance of any feed item, e.g., number of likes, comments, shares, time of the update, whether the post has images/videos, etc., and then, a score can be calculated using these features. This is generally enough for a simple ranking system. A better ranking system can significantly improve itself by constantly evaluating if we are making progress in user stickiness, retention, ads revenue, etc.

## 9. Data Partitioning

### A. Sharding posts and metadata
Since we have a huge number of new posts every day and our read load is extremely high too, we need to distribute our data onto multiple machines such that we can read/write it efficiently. For sharding our databases that are storing posts and their metadata, we can have a similar design as discussed under Designing Twitter.

### Sharding feed data
For feed data, which is being stored in memory, we can partition it based on UserID. We can try storing all the data of a user on one server. When storing, we can pass the UserID to our hash function that will map the user to a cache server where we will store the user’s feed objects. Also, for any given user, since we don’t expect to store more than 500 FeedItmeIDs, we will not run into a scenario where feed data for a user doesn’t fit on a single server. To get the feed of a user, we would always have to query only one server. For future growth and replication, we must use Consistent Hashing.

