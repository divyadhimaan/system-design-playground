# Databases

> A database is a structured collection of data. 

## Key Characteristics
- Usually consistent
- Available to incoming requests
- 


### Why is database important?

- Efficient Scaling - Difficult to store large amount of data without DB.
- Data Integrity - DB can maintain data consistency
- Data Security - DB supports security and compliance requirements.
- Data Analytics - DB is used for data analytics.
  
### Is Database a server?

  - A database itself is not a server, but it typically runs on a database server, which is a specialized service or system that handles data storage, retrieval, and management.
  - It is a best practice to separate the database from application logic, meaning the database should run independently of the application server. This offers
    - Improved Scalability
    - Easier deployment and management
    - Increased Readability
  
### Should Database be in memory? 

-  When to use an in-memory database:
    - High-speed applications like real-time analytics, caching layers, gaming leaderboards, or financial trading systems.
    - When low latency is crucial.
    - If the data set is small enough to fit in memory.
    - Examples: Redis, Memcached, H2 (for testing).
- When not ideal:
    - For large data sets that exceed available RAM.
    - When durability is a must — since memory is volatile, data can be lost on crash or power failure (unless special mechanisms are used).
    - For long-term persistent storage like user data, logs, or business records.


### How data is stored (storage)? 

- Pages and Block storage
  - Databases break down tables into pages or blocks.
  - These are units of storage on disk (or in memory), helping the DB manage and cache data efficiently.
- Indexes 
  - Indexes are stored separately but point to the actual data locations.
  - They help speed up queries by allowing quick lookups
- B+ Trees (in relational DB)
- Tables
  - Data is stored in tables (like Excel sheets) with rows and columns.
  - Each row = a record.
  - Tables are stored on disk in data pages 
- NoSQL Databases
  - Document DBs (like MongoDB): store data as JSON-like documents.
  - Key-Value stores (like Redis): store data as simple key-value pairs.
  - Columnar DBs (like Cassandra): store data column-wise instead of row-wise, good for analytics.
  - Graph DBs (like Neo4j): store data as nodes and relationships.

### How do databases read data (retrieval) ? 

 - Using indexes - faster reads by avoiding full scans.
 - full table scans - slower, but sometimes necessary if the query touches many rows or uses non-indexed columns.
 - Query Optimizer - Usually databases have query optimizers that decides whether to use an index or not.
  

