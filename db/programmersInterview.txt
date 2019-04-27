NATURAL KEY / BUSINESS KEY / DOMAIN KEY : A natural key is a key composed of columns that actually have a logical relationship to other columns within a table.

REFERENTIAL INTEGRITY: Referential integrity is a relational database concept in which multiple tables share a relationship based on the data stored in the tables, and that relationship must remain consistent.

DIFFERENCE BETWEEN HAVING AND WHERE CLAUSE: where clause can not be used with aggregates, but the having clause can. One way to think of it is that the having clause is an additional filter to the where clause.
 
SELECTIVITY: how many different values are available in the given sample set. Selectivity of index = cardinality/(number of records) * 100%

CARDINALITY: refers to the relationship that one table can have with another table.These relationships include: many-to-many, many-to-one/one-to-many, or one-to-one. In SQL, the cardinality of a column in a given table refers to the number of unique values that appear in the table for that column. So, remember that the cardinality is a number.

DATA MINING: Data mining is the process of finding patterns in a given data set. These patterns can often provide meaningful and insightful data to whoever is interested in that data. Data mining is used today in a wide variety of contexts – in fraud detection, as an aid in marketing campaigns, and even supermarkets use it to study their consumers.

DATA WAREHOUSING: Data warehousing can be said to be the process of centralizing or aggregating data from multiple sources into one common repository.

TERNARY or THREE VALUED LOGIC: logical operations in SQL have 3 possible values NOT 2 possible values. What are those 3 possible values? They are TRUE, FALSE, and UNKNOWN. The UNKNOWN value, as it’s name suggests, simply means that a value is unknown or unrepresentable.

SUB-QUERY: A subquery is a SELECT statement that is nested within another statement – that’s why it’s called a subquery, because it’s like having a query within another query .

DERIVED TABLES or INLINE VIEW: A derived table is basically a subquery, except it is always in the FROM clause of a SQL statement.

UNCORELLATED QUERY: Subquery can be run independently of the outer query. Basically, the subquery has no relationship with the outer query.

CORELLATED QUERY: a correlated subquery has the opposite property – the subquery can not be run independently of the outer query. First, a row is processed in the outer query. Then, for that particular row the subquery is executed – so for each row processed by the outer query, the subquery will also be processed.

CLUSTERED INDEX: A clustered index determines the order in which the rows of the table will be stored on disk – and it actually stores row level data in the leaf nodes of the index itself. A table can have only one clustered index. The query will run much faster than if the rows were being stored in some random order on the disk.

NON-CLUSTERED INDEX: A non-clustered index has no effect on which the order of the rows will be stored. Non clustered indexes store both a value and a pointer to the actual row that holds that value.

BLOCKS: A block is the smallest unit of data that an operating system can either write to a file or read from a file.

PAGES: Pages are used by some operating systems instead of blocks. A page is basically a virtual block. And, pages have a fixed size – 4K and 2K are the most commonly used sizes. So, the two key points to remember about pages is that they are virtual blocks and they have fixed sizes.

LOCK CONTENTION: There are locks on the data, sessions that exist at the same time (concurrent sessions) are essentially competing for the right to apply updates on the same data, because that data may be locked by any given session.

LOCK ESCALAITON: Lock escalation occurs when database locks are raised to higher “levels”, because a particular database session places an increasing number of locks on the same types of database objects.

MYISAM:
    1. MYISAM supports Table-level Locking.
    2. MyISAM designed for need of speed.
    3. MyISAM does not support foreign keys hence we call MySQL with MYISAM is DBMS.
    4. MyISAM stores its tables, data and indexes in diskspace using separate three different files. (tablename.FRM, tablename.MYD, tablename.MYI).
    5. MYISAM not supports transaction. You cannot commit and rollback with MYISAM. Once you issue a command it’s done.
    6. MYISAM supports fulltext search.
    7. You can use MyISAM, if the table is more static with lots of select and less update and delete.

INNODB:
    1. InnoDB supports Row-level Locking.
    2. InnoDB designed for maximum performance when processing high volume of data.
    3. InnoDB support foreign keys hence we call MySQL with InnoDB is RDBMS.
    4. InnoDB stores its tables and indexes in a tablespace.
    5. InnoDB supports transaction. You can commit and rollback with InnoDB.
    6. After v5.6, InnoDB support full text search.

DATABASE PERFORMANCE TUNING:
	1. Be careful using triggers.
	2. Use suitable data types.
	3. create suitable indexes.

FULL TABLE SCAN POSSIBILITY:
	1. query does not have where clause,
	2. there is no index created on search column,
	3. where clause using operations like <>, != , NOT, LIKE

TUNING SQL QUERY:
	1. reduces the rows that are returned by query.
	2. get rid of un-necessary columns and tables.
	3. order of predicates in where clause.
	4. order of table names when taking joins.
	5. read query optimizers query
	6. use indexed columns

TUNING INDEX:
	1. Do not use too many indexes.
	2. not to include columns that are repeatedly updated in an index.
	3. creating indexes on foreign key columns can improve performace.
	4. create indexes for columns that are repeatedly used in predicates.
	5. get rid of over-lapping indexes
	6. consider deleting an index when loading huge amounts of data into a table.
	7. Ensure that the index you create have high selectivity

SYSTEM vs OBJECT PRIVILEGES: system privileges are used for server and database privileges. But object privileges are used to grant privileges on database objects like tables, stored procedures, indexes, etc.
