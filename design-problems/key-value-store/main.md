# Key Value Store

- A **key-value store** is a type of _non-relational_ database that uses a simple key-value pair to store data. 
- Each key is unique and is used to retrieve the corresponding value. 
- Keys can be plaint text or hashed values, while values can be any type of data, such as strings, numbers, JSON objects, or binary data.
- Key-value stores are designed for high performance, scalability, and flexibility, making them suitable for various applications such as caching, session management, and real-time analytics.

## Step 1: Requirements and Design Scope

- Support basic operations: `put(key, value)` and  `get(key)`.
- The size of key-value pair is small (few KBs).
- Ability to store big data (few TBs).
- High availability: System responds quickly even during failures.
- High Scalability: System can be scaled to support large data set.
- Automatic Scaling: System should automatically scale up/down based on load.
- Tunable Consistency: Ability to choose between strong and eventual consistency based on use case.
- Low Latency: Fast read and write operations.

## Step 2: Proposing High Level Design
### Designing a Single server Key-Value Store

- Store key-value pairs in hash table, which keeps everything in memory.
- Memory access is fast, so read and write operations are quick.
- But memory is expensive and limited, so this design is not suitable for large data sets.
- Optimizations?
  - Data Compression: Compress data before storing to save memory.
  - Store only frequently accessed data in memory, and move less frequently accessed data to disk.
- Even with these optimizations, a single server key-value store is not suitable for large data sets.

### Designing a Distributed Key-Value Store

- To handle large data sets, we need to distribute data across multiple servers.
- We need to understand CAP theorem before designing a distributed key-value store.

#### CAP Theorem

- It states that in a distributed system, you can only achieve two out of the following three guarantees:
  - `Consistency`: Every read receives the most recent write or an error. All clients see same data at the same time no matter which node they connect to.
  - `Availability`: Every request receives a response, without guarantee that it contains the most recent version of the information. Clients get a response, even if some nodes aee down.
  - `Partition Tolerance`: The system continues to operate despite network failures or delays.

- Possible combinations are:
  - `CP`- Consistency and Partition Tolerance: System remains consistent even during network partitions, but may not be available.
  - `AP`- Availability and Partition Tolerance: System remains available even during network partitions, but may not be consistent.
  - `CA`- Consistency and Availability: System is consistent and available, but cannot handle network partitions. 

- Since network failures are inevitable in distributed systems, CA is not a practical choice. So distributed systems must choose between `CP` and `AP`.

#### Choosing between CP and AP
- Example
  - If there are 3 servers (n1, n2, n3) and a client wants to write a key-value pair (k1, v1). Lets say k1 is written to n3, but not propagated yet to n1 and n2.
  - Now, if n3 goes down and a client wants to read k1.
    - In `CP` system, the client will not get a response because it cannot guarantee consistency. It will wait until n3 is back up.
    - In `AP` system, the client will get a response from n1 or n2, but it may not be the most recent value of k1.
- For a key-value store, we can choose either `CP` or `AP` based on the use case.
- Choosing between AP and CP depends on the specific requirements of the application.
- For example:
  - A banking application would prefer `CP` to ensure data consistency.
  - A social media application would prefer `AP` to ensure availability.