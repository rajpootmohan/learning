# Designing Youtube or Netflix

## 1. Requirements

### Functional​ ​Requirements:
1. Users should be able to upload videos.
2. Users should be able to share and view videos.
3. Users should be able to perform searches based on video titles.
4. Our services should be able to record stats of videos, e.g., likes/dislikes, total number of views, etc.
5. Users should be able to add and view comments on videos.


### Non-Functional​ ​Requirements:
1. The system should be highly reliable, any video uploaded should not be lost.
2. The system should be highly available. Consistency can take a hit (in the interest of availability); if a user doesn’t see a video for a while, it should be fine.
3. Users should have a real time experience while watching videos and should not feel any lag.

### Extended​ ​Requirements:
1. Video recommendations, most popular videos, channels, subscriptions, watch later, favorites, etc.

## 2. Capacity​ ​Estimation​ ​and​ ​Constraints
Let’s assume we have 1.5 billion total users, 800 million of whom are daily active users. If, on average, a user views five videos per day then the total video-views per second would be:
`800M * 5 / 86400 sec => 46K videos/sec`

Let’s assume our upload:view ratio is 1:200, i.e., for every video upload we have 200 videos viewed, giving us 230 videos uploaded per second.
`46K / 200 => 230 videos/sec`

### 1. Storage​ ​estimates:
Let’s assume that every minute 500 hours worth of videos are uploaded to Youtube. If on average, one minute of video needs 50MB of storage (videos need to be stored in multiple formats), the total storage needed for videos uploaded in a minute would be:
`500 hours * 60 min * 50MB => 1500 GB/min (25 GB/sec)`

### 2. Bandwidth​ ​estimates:
With 500 hours of video uploads per minute and assuming each video upload takes a bandwidth of 10MB/min, we would be getting 300GB of uploads every minute.
`500 hours * 60 mins * 10MB => 300GB/min (5GB/sec)`

## 3. ​System​ ​APIs
```
uploadVideo(api_dev_key, video_title, vide_description, tags[], category_id, default_language, 
                        recording_details, video_contents)
searchVideo(api_dev_key, search_query, user_location, maximum_videos_to_return, page_token)
streamVideo(api_dev_key, video_id, offset, codec, resolution)
```

## 4. High Level​ ​Design
1. __Processing Queue__: Each uploaded video will be pushed to a processing queue to be de-queued later for encoding, thumbnail generation, and storage.
2. __Encoder__: To encode each uploaded video into multiple formats.
3. __Thumbnails generator__: To generate a few thumbnails for each video.
4. __Video and Thumbnail storage__: To store video and thumbnail files in some distributed file storage.
5. __User Database__: To store user’s information, e.g., name, email, address, etc.
6. __Video metadata storage__: A metadata database to store all the information about videos like title, file path in the system, uploading user, total views, likes, dislikes, etc. It will also be used to store all the video comments.

![High level Design](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/youtube_high_level_design.png)

## 5. Database Schema

### Video metadata storage - MySql

Videos metadata can be stored in a SQL database. The following information should be stored with each video:

1. VideoID
2. Title
3. Description
4. Size
5. Thumbnail
6. Uploader/User
7. Total number of likes
8. Total number of dislikes
9. Total number of views

For each video comment, we need to store following information:

1. CommentID
2. VideoID
3. UserID
4. Comment
5. TimeOfCreation

### User data storage - MySql
1. UserID,
2. Name,
3. email,
4. address,
5. age,
6. registration details etc.

## 6. Detailed Component Design
The service would be read-heavy, so we will focus on building a system that can retrieve videos quickly. We can expect our read:write ratio to be 200:1, which means for every video upload there are 200 video views.

### 1. Where would videos be stored?
Videos can be stored in a distributed file storage system like HDFS or GlusterFS.

### 2. How should we efficiently manage read traffic?
1. We should segregate our read traffic from write traffic.
2. Since we will have multiple copies of each video, we can distribute our read traffic on different servers.
3. master-slave configurations.

### 3. Where would thumbnails be stored?
1. There will be a lot more thumbnails than videos.
2. If we assume that every video will have five thumbnails, we need to have a very efficient storage system that can serve a huge read traffic.
3. Thumbnails are small files with, say, a maximum 5KB each.
4. Read traffic for thumbnails will be huge compared to videos. Users will be watching one video at a time, but they might be looking at a page that has 20 thumbnails of other videos.
5. __Bigtable__ can be a reasonable choice here as it combines multiple files into one block to store on the disk and is very efficient in reading a small amount of data.

### 4. Video Uploads
Since videos could be huge, if while uploading the connection drops we should support resuming from the same point.

### 5. Video Encoding
Newly uploaded videos are stored on the server and a new task is added to the processing queue to encode the video into multiple formats. Once all the encoding will be completed the uploader will be notified and the video is made available for view/sharing.

![Detailed Component Design](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/youtube_component_design.png)

## 7. Metadata Sharding
Since we have a huge number of new videos every day and our read load is extremely high, therefore, we need to distribute our data onto multiple machines so that we can perform read/write operations efficiently.

### 1. Sharding based on UserID
We can try storing all the data for a particular user on one server.Below are the issues:
1. What if a user becomes popular? There could be a lot of queries on the server holding that user; this could create a performance bottleneck. This will also affect the overall performance of our service.
2. Over time, some users can end up storing a lot of videos compared to others. Maintaining a uniform distribution of growing user data is quite tricky.

### 2. Sharding based on VideoID
Our hash function will map each VideoID to a random server where we will store that Video’s metadata. To find videos of a user we will query all servers and each server will return a set of videos. A centralized server will aggregate and rank these results before returning them to the user. This approach solves our problem of popular users but shifts it to popular videos.

## 8. Video Deduplication
With a huge number of users uploading a massive amount of video data our service will have to deal with widespread video duplication. The proliferation of duplicate videos can have an impact on many levels:
1. Data Storage: We could be wasting storage space by keeping multiple copies of the same video.
2. Caching: Duplicate videos would result in degraded cache efficiency by taking up space that could be used for unique content.
3. Network usage: Duplicate videos will also increase the amount of data that must be sent over the network to in-network caching systems.

### How to resolve duplication videos?
1. As soon as any user starts uploading a video, our service can run video matching algorithms (e.g., Block Matching, Phase Correlation, etc.) to find duplications.
2. If we already have a copy of the video being uploaded, we can either stop the upload and use the existing copy or continue the upload and use the newly uploaded video if it is of higher quality. 
3. If the newly uploaded video is a subpart of an existing video or, vice versa, we can intelligently divide the video into smaller chunks so that we only upload the parts that are missing.

## 9. Load Balancer (LB)
1. We should use Consistent Hashing among our cache servers.

## 10. Cache
We can introduce a cache for metadata servers to cache hot database rows. Using Memcache to cache the data and Application servers before hitting database can quickly check if the cache has the desired rows. Least Recently Used (LRU) can be a reasonable cache eviction policy for our system. Under this policy, we discard the least recently viewed row first.

### How can we build more intelligent cache?
If we go with 80-20 rule, i.e., 20% of daily read volume for videos is generating 80% of traffic, meaning that certain videos are so popular that the majority of people view them

## 11. Content Delivery Network (CDN)
A CDN is a system of distributed servers that deliver web content to a user based in the geographic locations of the user, the origin of the web page and a content delivery server.