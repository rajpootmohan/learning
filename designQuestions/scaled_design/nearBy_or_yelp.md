# Designing Yelp or Nearby Friends

## 1. Why Yelp or Proximity Server?
Proximity servers are used to discover nearby attractions like places, events, etc.

## 2. Requirements and Goals of the System

### Functional Requirements:
1. Users should be able to add/delete/update Places.
2. iven their location (longitude/latitude), users should be able to find all nearby places within a given radius.
3. Users should be able to add feedback/review about a place. The feedback can have pictures, text, and a rating.

### Non-functional Requirements:
1. Users should have a real-time search experience with minimum latency.
2. Our service should support a heavy search load. There will be a lot of search requests compared to adding a new place.

## 3. Database Schema
Each location can have the following fields:

- LocationID (8 bytes): Uniquely identifies a location.
- Name (256 bytes)
- Latitude (8 bytes)
- Longitude (8 bytes)
- Description (512 bytes)
- Category (1 byte): E.g., coffee shop, restaurant, theater, etc.

Although a four bytes number can uniquely identify 500M locations, with future growth in mind, we will go with 8 bytes for LocationID.

Total size: 8 + 256 + 8 + 8 + 512 + 1 => 793 bytes

We also need to store reviews, photos, and ratings of a Place. We can have a separate table to store reviews for Places:

- LocationID (8 bytes)
- ReviewID (4 bytes): Uniquely identifies a review, assuming any location will not have more than 2^32 reviews.
- ReviewText (512 bytes)
- Rating (1 byte): how many stars a place gets out of ten.

## 4. System APIs
```
search(api_dev_key, search_terms, user_location, radius_filter, maximum_results_to_return, 
    category_filter, sort, page_token)
```

## 5. Basic System Design and Algorithm
At a high level, we need to store and index each dataset described above (places, reviews, etc.). For users to query this massive database, the indexing should be read efficient, since while searching for the nearby places users expect to see the results in real-time.

### A. SQL solution

One simple solution could be to store all the data in a database like MySQL. Each place will be stored in a separate row, uniquely identified by LocationID. Each place will have its longitude and latitude stored separately in two different columns, and to perform a fast search; we should have indexes on both these fields.

__How efficient would this query be?__ We have estimated 500M places to be stored in our service. Since we have two separate indexes, each index can return a huge list of places and performing an intersection on those two lists won’t be efficient. 

### B. Grids

We can divide the whole map into smaller grids to group locations into smaller sets. Each grid will store all the Places residing within a specific range of longitude and latitude. This scheme would enable us to query only a few grids to find nearby places. Based on a given location and radius, we can find all the neighboring grids and then query these grids to find nearby places.

![NearBy Grid](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/nearBy_grid.png)

__What could be a reasonable grid size?__ Grid size could be equal to the distance we would like to query since we also want to reduce the number of grids. If the grid size is equal to the distance we want to query, then we only need to search within the grid which contains the given location and neighboring eight grids. Since our grids would be statically defined (from the fixed grid size), we can easily find the grid number of any location (lat, long) and its neighboring grids.

__Should we keep our index in memory?__ Maintaining the index in memory will improve the performance of our service. We can keep our index in a hash table where ‘key’ is the grid number and ‘value’ is the list of places contained in that grid.

__How much memory will we need to store the index?__ Let’s assume our search radius is 10 miles; given that the total area of the earth is around 200 million square miles, we will have 20 million grids. We would need a four bytes number to uniquely identify each grid and, since LocationID is 8 bytes, we would need 4GB of memory (ignoring hash table overhead) to store the index.

`(4 * 20M) + (8 * 500M) ~= 4 GB`

### C. Dynamic size grids
Let’s assume we don’t want to have more than 500 places in a grid so that we can have a faster searching. So, whenever a grid reaches this limit, we break it down into four grids of equal size and distribute places among them. This means thickly populated areas like downtown San Francisco will have a lot of grids, and sparsely populated area like the Pacific Ocean will have large grids with places only around the coastal lines.

![NearBy Quad Tree](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/nearBy_quad_tree.png)

__What data-structure can hold this information?__ A tree in which each node has four children can serve our purpose. Each node will represent a grid and will contain information about all the places in that grid. If a node reaches our limit of 500 places, we will break it down to create four child nodes under it and distribute places among them.

__How will we build a QuadTree?__ We will start with one node that will represent the whole world in one grid. Since it will have more than 500 locations, we will break it down into four nodes and distribute locations among them. We will keep repeating this process with each child node until there are no nodes left with more than 500 locations.

__How will we find the grid for a given location?__ We will start with the root node and search downward to find our required node/grid. At each step, we will see if the current node we are visiting has children. If it has, we will move to the child node that contains our desired location and repeat this process. If the node does not have any children, then that is our desired node.

__How will we find neighboring grids of a given grid?__ Since only leaf nodes contain a list of locations, we can connect all leaf nodes with a doubly linked list. This way we can iterate forward or backward among the neighboring leaf nodes to find out our desired locations. Another approach for finding adjacent grids would be through parent nodes. We can keep a pointer in each node to access its parent, and since each parent node has pointers to all of its children, we can easily find siblings of a node. We can keep expanding our search for neighboring grids by going up through the parent pointers.
Once we have nearby LocationIDs, we can query the backend database to find details about those places.

__What will be the search workflow?__ We will first find the node that contains the user’s location. If that node has enough desired places, we can return them to the user. If not, we will keep expanding to the neighboring nodes (either through the parent pointers or doubly linked list) until either we find the required number of places or exhaust our search based on the maximum radius.

__How much memory will be needed to store the QuadTree?__ For each Place, if we cache only LocationID and Lat/Long, we would need 12GB to store all places.

`24 * 500M => 12 GB`

Since each grid can have a maximum of 500 places, and we have 500M locations, how many total grids we will have?

`500M / 500 => 1M grids`

Which means we will have 1M leaf nodes and they will be holding 12GB of location data. A QuadTree with 1M leaf nodes will have approximately 1/3rd internal nodes, and each internal node will have 4 pointers (for its children). If each pointer is 8 bytes, then the memory we need to store all internal nodes would be:

`1M * 1/3 * 4 * 8 = 10 MB`

__How would we insert a new Place into our system?__ Whenever a new Place is added by a user, we need to insert it into the databases as well as in the QuadTree. If our tree resides on one server, it is easy to add a new Place, but if the QuadTree is distributed among different servers, first we need to find the grid/server of the new Place and then add it there.

## 6. Data Partitioning

### Sharding based on regions
We can divide our places into regions (like zip codes), such that all places belonging to a region will be stored on a fixed node. To store a place we will find the server through its region and, similarly, while querying for nearby places we will ask the region server that contains user’s location. This approach has a couple of issues:
1. What if a region becomes hot?
2. Over time, some regions can end up storing a lot of places compared to others. Hence, maintaining a uniform distribution of places, while regions are growing is quite difficult.

### Sharding based on LocationID
Our hash function will map each LocationID to a server where we will store that place. While building our QuadTree, we will iterate through all the places and calculate the hash of each LocationID to find a server where it would be stored. To find places near a location, we have to query all servers and each server will return a set of nearby places. A centralized server will aggregate these results to return them to the user.

![NearBy partition](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/nearBy_parition.png)

## 7. Replication and Fault Tolerance

We can create replicas using master-slave architecture.

__What if both primary and secondary servers die at the same time?__ We have to allocate a new server and rebuild the same QuadTree on it. How can we do that, since we don’t know what places were kept on this server? The brute-force solution would be to iterate through the whole database and filter LocationIDs using our hash function to figure out all the required places that will be stored on this server. This would be inefficient and slow; also, during the time when the server is being rebuilt, we will not be able to serve any query from it, thus missing some places that should have been seen by users.

__How can we efficiently retrieve a mapping between Places and QuadTree server?__ We have to build a reverse index that will map all the Places to their QuadTree server. We can have a separate QuadTree Index server that will hold this information. We will need to build a HashMap where the ‘key’ is the QuadTree server number and the ‘value’ is a HashSet containing all the Places being kept on that QuadTree server. We need to store LocationID and Lat/Long with each place because information servers can build their QuadTrees through this. Notice that we are keeping Places’ data in a HashSet, this will enable us to add/remove Places from our index quickly. So now, whenever a QuadTree server needs to rebuild itself, it can simply ask the QuadTree Index server for all the Places it needs to store. This approach will surely be quite fast. We should also have a replica of the QuadTree Index server for fault tolerance. If a QuadTree Index server dies, it can always rebuild its index from iterating through the database.

## 8. Cache
To deal with hot Places, we can introduce a cache in front of our database.

## 9. Load Balancing (LB)
1. We can add LB layer at two places in our system 1) Between Clients and Application servers and 2) Between Application servers and Backend server.
2. Initially, a simple Round Robin approach can be adopted.
3. A problem with Round Robin LB is, it won’t take server load into consideration. If a server is overloaded or slow, the load balancer will not stop sending new requests to that server. To handle this, a more intelligent LB solution would be needed that periodically queries backend server about their load and adjusts traffic based on that.

## 10. Ranking
How about if we want to rank the search results not just by proximity but also by popularity or relevance?

__How can we return most popular places within a given radius?__ Let’s assume we keep track of the overall popularity of each place. An aggregated number can represent this popularity in our system, e.g., how many stars a place gets out of ten (this would be an average of different rankings given by users)? We will store this number in the database as well as in the QuadTree. While searching for the top 100 places within a given radius, we can ask each partition of the QuadTree to return the top 100 places with maximum popularity. Then the aggregator server can determine the top 100 places among all the places returned by different partitions.

Remember that we didn’t build our system to update place’s data frequently. With this design, how can we modify the popularity of a place in our QuadTree? Although we can search a place and update its popularity in the QuadTree, it would take a lot of resources and can affect search requests and system throughput. Assuming the popularity of a place is not expected to reflect in the system within a few hours, we can decide to update it once or twice a day, especially when the load on the system is minimum.

