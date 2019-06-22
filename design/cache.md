# Cache

## What is Cache?
- Caching improves page load times and can reduce the load on your servers and databases.
- the dispatcher will first lookup if the request has been made before and try to find the previous result to return, in order to save the actual execution.
- Databases often benefit from a uniform distribution of reads and writes across its partitions.
- Popular items can skew the distribution, causing bottlenecks.
- Putting a cache in front of a database can help absorb uneven loads and spikes in traffic.

## Client caching
- Caches can be located on the client side (OS or browser), server side, or in a distinct cache layer.

## CDN caching
- CDNs are considered a type of cache.

## Web server caching
- Reverse proxies and caches such as Varnish can serve static and dynamic content directly. Web servers can also cache requests, returning responses without having to contact application servers.

## Database Caching
- Your database usually includes some level of caching in a default configuration, optimized for a generic use case. Tweaking these settings for specific usage patterns can further boost performance.

## Application caching
- There are multiple levels you can cache that fall into two general categories: database queries and objects:
	- Row level
	- Query-level
	- Fully-formed serializable objects
	- Fully-rendered HTML

## Caching at the database query level
- Whenever you query the database, hash the query as a key and store the result to the cache. This approach suffers from expiration issues:
	- Hard to delete a cached result with complex queries
	- If one piece of data changes such as a table cell, you need to delete all cached queries that might include the changed cell

## Caching at the object level
- See your data as an object, similar to what you do with your application code. Have your application assemble the dataset from the database into a class instance or a data structure(s):
	- Remove the object from cache if its underlying data has changed
	- Allows for asynchronous processing: workers assemble objects by consuming the latest cached object

## When to update the cache
Since you can only store a limited amount of data in cache, you'll need to determine which cache update strategy works best for your use case.

### 1. Cache-aside or Lazy-loading
- The application is responsible for reading and writing from storage. The cache does not interact with storage directly. The application does the following:
	- Look for entry in cache, resulting in a cache miss
	- Load entry from the database
	- Add entry to cache
	- Return entry
- Cache-aside is also referred to as lazy loading.
- Only requested data is cached, which avoids filling up the cache with data that isn't requested.
- Disadvantage:
	- Each cache miss results in three trips, which can cause a noticeable delay.
	- Data can become stale if it is updated in the database. This issue is mitigated by setting a time-to-live (TTL) which forces an update of the cache entry, or by using write-through.
	- When a node fails, it is replaced by a new, empty node, increasing latency.

### 2. Write-through
- The application uses the cache as the main data store, reading and writing data to it, while the cache is responsible for reading and writing to the database:
	- Application adds/updates entry in cache
	- Cache synchronously writes entry to data store
	- Return
- Write-through is a slow overall operation due to the write operation, but subsequent reads of just written data are fast. 
- Users are generally more tolerant of latency when updating data than reading data. Data in the cache is not stale.
- Disadvantage
	- When a new node is created due to failure or scaling, the new node will not cache entries until the entry is updated in the database. Cache-aside in conjunction with write through can mitigate this issue.
	- Most data written might never be read, which can be minimized with a TTL.

### 3. Write-behind (write-back)
- In write-behind, the application does the following:
	- Add/update entry in cache
	- Asynchronously write entry to the data store, improving write performance
- Disadvantage
	- There could be data loss if the cache goes down prior to its contents hitting the data store.
	- It is more complex to implement write-behind than it is to implement cache-aside or write-through.

### 4. Refresh-ahead
- You can configure the cache to automatically refresh any recently accessed cache entry prior to its expiration.
- Refresh-ahead can result in reduced latency vs read-through if the cache can accurately predict which items are likely to be needed in the future.
- Disadvantage
	- Not accurately predicting which items are likely to be needed in the future can result in reduced performance than without refresh-ahead.

## Disadvantage(s): cache
- Need to maintain consistency between caches and the source of truth such as the database through cache invalidation.
- Cache invalidation is a difficult problem, there is additional complexity associated with when to update the cache.
- Need to make application changes such as adding Redis or memcached.




