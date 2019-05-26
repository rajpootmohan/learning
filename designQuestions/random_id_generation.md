# How to get Distributed Unique Primary Keys?

## Why?
We’d love to go on using MySQL auto-incrementing columns for primary keys like everyone else, but MySQL can’t guarantee uniqueness across physical and logical databases.

## GUIDs or UUIDs?
Given the need for globally unique ids the obvious question is, why not use GUIDs? Mostly because GUIDs are big, and they index badly in MySQL.

## Consistent Hashing?
Some projects like Amazon’s Dynamo provide a consistent hashing ring on top of the datastore to handle the GUID/sharding issue. This is better suited for write-cheap environments (e.g. LSMTs), while MySQL is optimized for fast random reads.

## Centralizing Auto-Increments
If we can’t make MySQL auto-increments work across multiple databases, what if we just used one database? If we inserted a new row into this one database every time someone uploaded a photo we could then just use the auto-incrementing ID from that table as the primary key for all of our databases.

## How flickr manage?

A Flickr ticket server is a dedicated database server, with a single database on it, and in that database there are tables like Tickets32 for 32-bit IDs, and Tickets64 for 64-bit IDs.
The Tickets64 schema looks like:
```
CREATE TABLE `Tickets64` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `stub` char(1) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `stub` (`stub`)
) ENGINE=MyISAM;
```
`SELECT * from Tickets64` returns a single row that looks something like:
```
+-------------------+------+
| id                | stub |
+-------------------+------+
| 72157623227190423 |    a |
+-------------------+------+
```
When I need a new globally unique 64-bit ID I issue the following SQL:
```
REPLACE INTO Tickets64 (stub) VALUES ('a');
SELECT LAST_INSERT_ID();
```

## High Availability for flickr
We achieve “high availability” by running two ticket servers. At this write/update volume replicating between the boxes would be problematic, and locking would kill the performance of the site. We divide responsibility between the two boxes by dividing the ID space down the middle, evens and odds, using:
```
TicketServer1:
auto-increment-increment = 2
auto-increment-offset = 1

TicketServer2:
auto-increment-increment = 2
auto-increment-offset = 2
```
We round robin between the two servers to load balance and deal with down time.

## Another Idea from twitter: SNOWFLAKE

### Problem Statement
* we’re working to replace many of these systems with the Cassandra distributed database or horizontally sharded MySQL (using gizzard)
* Unlike MySQL, Cassandra has no built-in way of generating unique ids – nor should it, since at the scale where Cassandra becomes interesting, it would be difficult to provide a one-size-fits-all solution for ids. Same goes for sharded MySQL.
* We needed something that could generate tens of thousands of ids per second in a highly available manner.These ids need to be roughly sortable.

### Solution
To generate the roughly-sorted 64 bit ids in an uncoordinated manner, we settled on a composition of: timestamp, worker number and sequence number.

