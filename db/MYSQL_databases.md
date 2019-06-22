# Database

## Relational database management system (RDBMS)
A relational database like SQL is a collection of data items organized in tables. ACID is a set of properties of relational database transactions.

- Atomicity - Each transaction is all or nothing.
- Consistency - Any transaction will bring the database from one valid state to another.
- Isolation - Executing transactions concurrently has the same results as if the transactions were executed serially.
- Durability - Once a transaction has been committed, it will remain so.

There are many techniques to scale a relational database: master-slave replication, master-master replication, federation, sharding, denormalization, and SQL tuning.

### Master-slave replication
The master serves reads and writes, replicating writes to one or more slaves, which serve only reads. Slaves can also replicate to additional slaves in a tree-like fashion. If the master goes offline, the system can continue to operate in read-only mode until a slave is promoted to a master or a new master is provisioned.

#### Disadvantage: Master-slave
- Additional logic is needed to promote a slave to a master.
- There is a potential for loss of data if the master fails before any newly written data can be replicated to other nodes.
- Writes are replayed to the read replicas. If there are a lot of writes, the read replicas can get bogged down with replaying writes and can't do as many reads.
- The more read slaves, the more you have to replicate, which leads to greater replication lag.

### Master-master replication
Both masters serve reads and writes and coordinate with each other on writes. If either master goes down, the system can continue to operate with both reads and writes.

#### Disadvantage: Master-master
- You'll need a load balancer or you'll need to make changes to your application logic to determine where to write.
- Most master-master systems are either loosely consistent (violating ACID) or have increased write latency due to synchronization.
- Conflict resolution comes more into play as more write nodes are added and as latency increases.
- There is a potential for loss of data if the master fails before any newly written data can be replicated to other nodes.

### Federation
Federation (or functional partitioning) splits up databases by function. For example, instead of a single, monolithic database, you could have three databases: forums, users, and products, resulting in less read and write traffic to each database and therefore less replication lag. With no single central master serializing writes you can write in parallel, increasing throughput.

#### Disadvantage: Federation
- Federation is not effective if your schema requires huge functions or tables.
- You'll need to update your application logic to determine which database to read and write.
- Joining data from two databases is more complex with a server link.
- Federation adds more hardware and additional complexity.

### Sharding
Sharding distributes data across different databases such that each database can only manage a subset of the data. Taking a users database as an example, as the number of users increases, more shards are added to the cluster.

#### Disadvantage: Sharding
- You'll need to update your application logic to work with shards, which could result in complex SQL queries.
- Data distribution can become lopsided in a shard. For example, a set of power users on a shard could result in increased load to that shard compared to others.
- Joining data from multiple shards is more complex.
- Sharding adds more hardware and additional complexity.

### Denormalization
Denormalization attempts to improve read performance at the expense of some write performance. Redundant copies of the data are written in multiple tables to avoid expensive joins. Some RDBMS such as PostgreSQL and Oracle support materialized views which handle the work of storing redundant information and keeping redundant copies consistent.

#### Disadvantage: Denormalization
- Data is duplicated.
- Constraints can help redundant copies of information stay in sync, which increases complexity of the database design.
- A denormalized database under heavy write load might perform worse than its normalized counterpart.

### SQL tuning
It's important to benchmark and profile to simulate and uncover bottlenecks.
- __Benchmark__ - Simulate high-load situations with tools such as ab.
- __Profile__ - Enable tools such as the slow query log to help track performance issues.

#### Tighten up the schema
- MySQL dumps to disk in contiguous blocks for fast access.
- Use CHAR instead of VARCHAR for fixed-length fields.
	- CHAR effectively allows for fast, random access, whereas with VARCHAR, you must find the end of a string before moving onto the next one.
- Use TEXT for large blocks of text such as blog posts. TEXT also allows for boolean searches. Using a TEXT field results in storing a pointer on disk that is used to locate the text block.
- Use INT for larger numbers up to 2^32 or 4 billion.
- Use DECIMAL for currency to avoid floating point representation errors.
- Avoid storing large BLOBS, store the location of where to get the object instead.
- Set the NOT NULL constraint where applicable to improve search performance.

#### Use good indices
- Columns that you are querying (SELECT, GROUP BY, ORDER BY, JOIN) could be faster with indices. 
- Indices are usually represented as self-balancing B-tree that keeps data sorted and allows searches, sequential access, insertions, and deletions in logarithmic time.
- Placing an index can keep the data in memory, requiring more space.
- Writes could also be slower since the index also needs to be updated.
- Writes could also be slower since the index also needs to be updated.

#### Avoid expensive joins
- Denormalize where performance demands it.

#### Partition tables
- Break up a table by putting hot spots in a separate table to help keep it in memory.

#### Tune the query cache
- In some cases, the query cache could lead to performance issues.