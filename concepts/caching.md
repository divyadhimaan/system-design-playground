# Caching

- [Caching](#caching)
  - [Introduction](#introduction)
  - [Why Caching is important?](#why-caching-is-important)
  - [How Caching works?](#how-caching-works)
  - [What are drawbacks of caching?](#what-are-drawbacks-of-caching)
  - [Placement for Cache in a Distributed System](#placement-for-cache-in-a-distributed-system)
  - [Types of caches](#types-of-caches)
    - [Local Cache](#local-cache)
    - [Global Cache](#global-cache)
  - [Write Policy](#write-policy)
    - [Cache Aside](#cache-aside)
    - [Write Back Policy (Write Behind)](#write-back-policy-write-behind)
    - [Write Through Policy](#write-through-policy)
    - [Write Around Policy](#write-around-policy)
    - [Comparison For Write Policies](#comparison-for-write-policies)
    - [Example Use Cases](#example-use-cases)
  - [Replacement Policy](#replacement-policy)


## Introduction 

Caching is storing frequently accessed data in temporary storage.

The purpose of the cache is to improve both latency (time interval to perform a single operation) and throughput (operation processing rates).

Two important things for caching are:
- Cache Policy 
  - Write Policy:  How to sync for writes among cache and DB
  - Eviction/Replacement Policy, What to kick out. (LRU, LFU, ..)

## Why Caching is important?
- Caching improves page load times and can reduce the load on your servers and databases. 
  
- Databases often benefit from a uniform distribution of reads and writes across its partitions. Popular items can skew the distribution, causing bottlenecks. Putting a cache in front of a database can help absorb uneven loads and spikes in traffic.

<details>
    <summary>Benefits</summary>

  - Reduce network calls
  - Avoid repeated communication
  - Reduce DB load
</details>

  

## How Caching works?
The application is responsible for reading and writing from storage. The cache does not interact with storage directly. The application does the following:

  - Look for entry in cache, resulting in a cache miss
  - Load entry from the database
  - Add entry to cache
  - Return entry

## What are drawbacks of caching?

- In case Cache doesn't store data accessed by user (poor hit rate)
- Need to maintain consistency between caches and the source of truth such as the database through cache invalidation (Eventual Consistency)
- Potential Thrashing - 

## Placement for Cache in a Distributed System

- In memory Cache
- Global Cache
- DB cache
- Distributed cache 

---

## Types of caches
### In-Memory Cache
- In-memory caches store data in the main memory (RAM) for extremely fast access.
- These caches are typically used for session management, storing frequently accessed objects, and as a front for databases.
> Examples: Redis and Memcached.

### Distributed Cache
- A distributed cache spans multiple servers and is designed to handle large-scale systems.
- It ensures that cached data is available across different nodes in a distributed system.
> Examples: Redis Cluster and Amazon ElastiCache.

### Client-Side Cache
- Client-side caching involves storing data on the client device, typically in the form of cookies, local storage, or application-specific caches.

- This is commonly used in web browsers to cache static assets like images, scripts, and stylesheets.

### Database Cache
- Database caching involves storing frequently queried database results in a cache.
- This reduces the number of queries made to the database, improving performance and scalability.

### Content Delivery Network (CDN)
- CDN is used to store copies of content on servers distributed across different geographical locations.
- This reduces latency by serving content from a server closer to the user.

---

## Caching Strategies

- `Read-Through Cache`: The application first checks the cache for data. If it's not there (a cache miss), it retrieves the data from the database and updates the cache.
- `Write-Through Cache`: Data is written to both the cache and the database simultaneously, ensuring consistency but potentially impacting write performance.
- `Write-Back Cache`: Data is written to the cache first and later synchronized with the database, improving write performance but risking data loss.
- `Cache-Aside (Lazy Loading)`: The application is responsible for reading and writing from both the cache and the database.

### Read Through

- In the Read Through strategy, the cache acts as an intermediary between the application and the database.
- When the application requests data, it first looks in the cache.
- If data is available (cache hit), it’s returned to the application.
- If the data is not available (cache miss), the cache itself is responsible for fetching the data from the database, storing it, and returning it to the application.


### Comparison For Write Policies

| Feature                 | Write-Through                    | Write-Back                               | Write-Around                           |
|-------------------------|----------------------------------|------------------------------------------|----------------------------------------|
| **Write Location**      | Cache ✅ + Memory ✅               | Cache ✅ only (Memory updated later)      | Memory ✅ only (Cache bypassed)         |
| **Read After Write**    | Fast ✅ (data in cache)           | Fast ✅ (data in cache)                   | Slow ❌ (data not in cache yet)         |
| **Write Latency**       | Slower ❌ (writes go to 2 places) | Fast ✅ (writes hit cache only)           | Fast ✅                                 |
| **Cache Pollution**     | Possible ❌ (caches all writes)   | Possible ❌                               | Avoided ✅ (writes don't pollute cache) |
| **Data Consistency**    | High ✅ (always in sync)          | Lower ❌ (risk if cache lost before sync) | High ✅ (memory is always up to date)   |
| **Recovery Complexity** | Low ✅                            | High ❌ (needs dirty block tracking)      | Low ✅                                  |
| **Use Case**            | Strong consistency needed        | High write performance, low consistency  | Write-once or infrequently read data   |



### Example Use Cases

| Scenario                                | Recommended Strategy       |
|-----------------------------------------|----------------------------|
| Financial transactions, logs            | Write-Through              |
| Gaming state or temporary calculations  | Write-Back                 |
| Logging, rarely read audit trails       | Write-Around               |


## Cache Eviction Policies

Cache replacement policies are strategies used to decide which data to remove from the cache when the cache is full and new data needs to be added.

 The goal is to maximize hit rate (how often requested data is found in cache).

 - Common Replacement Policies
   - **LRU (Least Recently Used)** - LRU evicts the least recently accessed data when the cache is full. It assumes that recently used data will likely be used again soon.
   - **LFU (Least Frequently Used)** - LFU evicts data that has been accessed the least number of times, under the assumption that rarely accessed data is less likely to be needed.
   - **FIFO (First In, First Out)** - FIFO evicts the oldest data in the cache first, regardless of how often or recently it has been accessed.
   - **Time-to-Live (TTL)** - TTL is a time-based eviction policy where data is removed from the cache after a specified duration, regardless of usage.
   - **MRU (Most Recently Used)** - Removes the most recently used item
   - **ARC (Adaptive Replacement Cache)** - Balances between LRU and LFU - Smart choice when workload patterns vary

Memcached used Segmented LRU (LRU+LFU).

---
## Challenges and Considerations

1. **Cache Coherence**: Ensuring that data in the cache remains consistent with the source of truth (e.g., the database).
2. **Cache Invalidation**: Determining when and how to update or remove stale data from the cache. 
3. **Cold Start**: Handling scenarios when the cache is empty, such as after a system restart. 
4. **Cache Eviction Policies**: Deciding which items to remove when the cache reaches capacity (e.g., Least Recently Used, Least Frequently Used). 
5. **Cache Penetration**: Preventing malicious attempts to repeatedly query for non-existent data, potentially overwhelming the backend. 
6. **Cache Stampede**: Managing situations where many concurrent requests attempt to rebuild the cache simultaneously.