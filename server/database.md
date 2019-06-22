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

