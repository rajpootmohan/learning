# Designing Twitter Search

## 1. What is Twitter Search?
Twitter users can update their status whenever they like. Each status (called tweet) consists of plain text and our goal is to design a system that allows searching over all the user tweets.

## 2. Requirements and Goals of the System
1. Let’s assume Twitter has 1.5 billion total users with 800 million daily active users.
2. On average Twitter gets 400 million tweets every day.
3. The average size of a tweet is 300 bytes.
4. Let’s assume there will be 500M searches every day.
5. The search query will consist of multiple words combined with AND/OR.

## 3. Capacity Estimation and Constraints

1. Since we have 400 million new tweets every day and each tweet on average is 300 bytes, the total storage we need, will be:
	`400M * 300 => 120GB/day`
2. Total storage per second:
	`120GB / 24hours / 3600sec ~= 1.38MB/second`

## 4. System APIs
`search(api_dev_key, search_terms, maximum_results_to_return, sort, page_token)`

## 5. High Level Design
At the high level, we need to store all the statues in a database and also build an index that can keep track of which word appears in which tweet. This index will help us quickly find tweets that users are trying to search.

## 6. Detailed Component Design

### 1. Storage

1. We need to store 120GB of new data every day. Given this huge amount of data, we need to come up with a data partitioning scheme that will be efficiently distributing the data onto multiple servers.
	`120GB * 365days * 5years ~= 200TB`
2. Let’s start with a simplistic design where we store the tweets in a MySQL database. We can assume that we store the tweets in a table having two columns, TweetID and TweetText. Let’s assume we partition our data based on TweetID. If our TweetIDs are unique system-wide, we can define a hash function that can map a TweetID to a storage server where we can store that tweet object.
3. __How can we create system-wide unique TweetIDs?__ If we are getting 400M new tweets each day, then how many tweet objects we can expect in five years?
	`400M * 365 days * 5 years => 730 billion`
4. This means we would need a five bytes number to identify TweetIDs uniquely. Let’s assume we have a service that can generate a unique TweetID whenever we need to store an object.

### 2. Index
1. Since our tweet queries will consist of words, let’s build the index that can tell us which word comes in which tweet object. Let’s first estimate how big our index will be. If we want to build an index for all the English words and some famous nouns like people names, city names, etc., and if we assume that we have around 300K English words and 200K nouns, then we will have 500k total words in our index. Let’s assume that the average length of a word is five characters. If we are keeping our index in memory, we need 2.5MB of memory to store all the words:
	`500K * 5 => 2.5 MB`
2. Let’s assume that we want to keep the index in memory for all the tweets from only past two years. Since we will be getting 730B tweets in 5 years, this will give us 292B tweets in two years. Given that each TweetID will be 5 bytes, how much memory will we need to store all the TweetIDs?
	`292B * 5 => 1460 GB`
3. let’s assume we will have around 15 words in each tweet that need to be indexed. This means each TweetID will be stored 15 times in our index. So total memory we will need to store our index:
	`(1460 * 15) + 2.5MB ~= 21 TB`
4. __Sharding based on Words:__ While building our index, we will iterate through all the words of a tweet and calculate the hash of each word to find the server where it would be indexed. To find all tweets containing a specific word we have to query only the server which contains this word.We have a couple of issues with this approach:
	* What if a word becomes hot? Then there will be a lot of queries on the server holding that word. This high load will affect the performance of our service.
	* Over time, some words can end up storing a lot of TweetIDs compared to others, therefore, maintaining a uniform distribution of words while tweets are growing is quite tricky.
5. __Sharding based on the tweet object:__ While storing, we will pass the TweetID to our hash function to find the server and index all the words of the tweet on that server. While querying for a particular word, we have to query all the servers, and each server will return a set of TweetIDs. A centralized server will aggregate these results to return them to the user.

![Detailed Component Design](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/twitter_search_detailed_design.png)

## 7. Fault Tolerance
1. What will happen when an index server dies? We can have a secondary replica of each server and if the primary server dies it can take control after the failover. Both primary and secondary servers will have the same copy of the index.

## 8. Cache
To deal with hot tweets we can introduce a cache in front of our database. We can use Memcached, which can store all such hot tweets in memory. Application servers, before hitting the backend database, can quickly check if the cache has that tweet. Based on clients’ usage patterns, we can adjust how many cache servers we need. For cache eviction policy, Least Recently Used (LRU) seems suitable for our system.

## 9. Load Balancing
We can add a load balancing layer at two places in our system.
1. Between Clients and Application servers
2. Between Application servers and Backend server.

## 10. Ranking
1. How about if we want to rank the search results by social graph distance, popularity, relevance, etc?
2. Let’s assume we want to rank tweets by popularity, like how many likes or comments a tweet is getting, etc. In such a case, our ranking algorithm can calculate a ‘popularity number’ (based on the number of likes etc.) and store it with the index. Each partition can sort the results based on this popularity number before returning results to the aggregator server. The aggregator server combines all these results, sorts them based on the popularity number, and sends the top results to the user.
