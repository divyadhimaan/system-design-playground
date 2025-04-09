# Caching

Caching is storing frequently accessed data in temporary storage.

Two important things for caching are:
- Cache Policy 
  - Write Policy:  How to sync for writes among cache and DB
  - Eviction/Replacement Policy, What to kick out. (LRU, LFU, ..)

## Why Caching?
- Caching improves page load times and can reduce the load on your servers and databases. 
  
- Databases often benefit from a uniform distribution of reads and writes across its partitions. Popular items can skew the distribution, causing bottlenecks. Putting a cache in front of a database can help absorb uneven loads and spikes in traffic.

- Benefits
  - Reduce network calls
  - Avoid repeated communication
  - Reduce DB load

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

## Placement for Cache

- In memory Cache
- Global Cache
- DB cache
- Distributed cache 