# Redis Cache and Memcached


## Redis Overview
> Redis is an in-memory, low-latency data store used primarily for caching, fast data access, coordination, and real-time workloads.

### Problem

| Problems at scale                         | how redis helps                                          |
|-------------------------------------------|----------------------------------------------------------|
| Databases become latency bottlenecks      | Offload reads from databases                             |
| Repeated reads overwhelm storage          | Reduce response latency from milliseconds → microseconds |
| Stateless services need shared fast state | Enable fast, shared, ephemeral state                     |


### Use Cases

- Use Redis when you see:
  - High read-to-write ratio
  - Frequently accessed but rarely updated data
  - Strict latency SLAs (<10 ms)
  - Need for counters, sessions, rate limits, locks
- Avoid Redis when:
  - Data must be permanently durable
  - Large objects or heavy analytical queries are required

### High Level Architecture

`Client → Service → Redis
                 ↓ (miss)
               Database
                 ↓
               Redis (populate)
`
- Redis sits between application and database, absorbing read pressure.

### Core Components & Responsibilities

| Component             | Responsibility                 |
|-----------------------|--------------------------------|
| Redis Server          | Stores data in memory          |
| Client Library        | Handles serialization, retries |
| Eviction Policy       | Manages memory pressure        |
| Persistence (RDB/AOF) | Optional durability            |
| Replicas              | Read scaling & failover        |
| Redis Cluster         | Horizontal sharding            |

### Scalability

- Vertical scaling limited by RAM
- Horizontal scaling via Redis Cluster (hash-slot sharding)
- Reads scale using replicas
- Writes scale via partitioning keys

- Bottleneck:
  - Hot keys
  - Single-threaded command execution

### Reliability & Fault Tolerance

- Redis improves performance but reduces reliability
  - Node failure → cache loss
  - Replication lag → stale reads
  - Persistence adds recovery but increases latency
- Mitigations:
  - Replicas + failover
  - Graceful cache miss handling
  - DB as source of truth


### Common Failure Scenarios

| Problem            | Impact               | Mitigation                                                                                                                                             |
|--------------------|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| Cache stampede     | DB overload          | **Request Coalescing (Cache Stampede Protection):** Ensure only one request recomputes a hot cache entry while others wait or reuse the result.        |
| Hot keys           | Single node meltdown | **Key Sharding (Hot Key Mitigation):** Split a logical hot key into multiple physical keys to distribute read load and avoid single-key bottlenecks.   |
| TTL expiry burst   | Latency spikes       | **Staggered TTLs + Background Refresh:** Add jitter to TTL values and proactively refresh hot keys before expiry to avoid simultaneous expirations.    |
| Eviction misconfig | Critical data loss   | **Proper Eviction Policy + Data Tiering:** Use appropriate eviction policies (e.g., volatile-LRU) and avoid caching critical data without DB fallback. |
| Redis restart      | Cold cache           | **Pre-Warming (Cold Cache Avoidance):** Load critical and frequently accessed keys into Redis before or immediately after traffic is routed.           |


### Design FAQs

> `Question`: Cache-aside vs write-through?
> 
> `Answer`: I usually prefer cache-aside. The application reads from Redis first, and on a miss, it fetches from the database and populates the cache. Writes go directly to the database, keeping it as the source of truth.
This keeps Redis as an optimization layer—if Redis fails, writes are unaffected. The trade-off is possible stale reads, which is acceptable for most read-heavy systems.
> 
> I’d consider write-through only when strong read consistency is required and higher write latency is acceptable.

> `Question`: TTL vs Manual Invalidation?
>
> `Answer`: I prefer TTL-based expiration for most systems because it’s simpler and more resilient to missed invalidation paths. TTL guarantees eventual correctness and limits staleness automatically.
Manual invalidation gives stronger consistency but increases operational complexity and failure risk.
>
> For critical data, I often combine TTL with explicit invalidation on writes.

> `Question`: Strong Consistency or Eventual Consistency?
>
> `Answer`: 
> - Redis-based systems typically operate with eventual consistency, which is acceptable for caches, feeds, and session data. This allows high availability and low latency.
> - If strong consistency is required, I restrict reads to the primary or use synchronous replication, but that comes at the cost of latency and availability.
> - Most Redis use cases consciously trade consistency for performance.

> `Question`: Single Node vs Redis Cluster?
>
> `Answer`: 
> - For small-scale or non-critical workloads, a single Redis node keeps latency low and operations simple.
> - At scale, I move to Redis Cluster to shard data horizontally and add replicas for availability. The trade-off is increased operational complexity and stricter key design constraints.
> - Cluster choice is driven primarily by data size and throughput, not just traffic.


> `Question`: Persistence Required or Not?
>
> `Answer`:
> - By default, I treat Redis as a disposable cache and avoid persistence, relying on the database as the source of truth. This gives the best performance and simplest recovery model.
> - I enable persistence only when cache rebuild is expensive or some temporary data loss is unacceptable.
> - Persistence improves recovery time but slightly erodes Redis’s latency advantage.

---

## Memcached Overview

> Memcached is a distributed, in-memory key–value cache designed for extremely fast reads with simple data models and no persistence.

### Problem?

- At scale:
  - Databases become read bottlenecks
  - Repeated queries waste compute
  - Low-latency reads are required (<1–2 ms)
    Memcached offloads hot, simple data from the database to reduce latency and increase throughput.

### When to Use Memcached

- Use Memcached when:
  - High read-to-write ratio
  - Frequently accessed but rarely updated data
  - Simple key–value data models
  - Strict latency SLAs (<2 ms)
- Avoid Memcached when:
  - You need persistence
  - Complex data structures are required
  - Atomic operations or coordination are needed


### Critical Design Decisions

1. Cache-Aside Only
   - Memcached supports cache-aside only
   - No write-through or write-behind
2. TTL Is Mandatory
   - No manual invalidation guarantees
   - TTL controls correctness window
3. No Replication
   - If a node dies → cache lost
   - App must handle misses gracefully


---

## Memcached vs Redis

| Aspect      | Memcached        | Redis                |
|-------------|------------------|----------------------|
| Data model  | Simple key–value | Rich structures      |
| Persistence | None             | Optional             |
| Replication | No               | Yes                  |
| Latency     | Slightly lower   | Very low             |
| Use case    | Pure cache       | Cache + coordination |

---

## FAQs

| #  | Question                                                | Interview-Ready Answer                                                                                                                                                                                                                |
|----|---------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1  | Why use Redis instead of directly hitting the database? | Redis reduces read latency and offloads traffic from the database by serving hot data from memory. This improves tail latency and prevents DB bottlenecks under read-heavy workloads, while the database remains the source of truth. |
| 2  | Is Redis a database or a cache?                         | Redis is best treated as a cache or fast data store, not a primary database. Although it supports persistence, most production systems use it as an optimization layer and rely on a durable DB for correctness.                      |
| 3  | What consistency guarantees does Redis provide?         | Redis typically provides eventual consistency, especially with replicas due to replication lag. Strong consistency is possible by reading from the primary only, but this reduces availability and increases latency.                 |
| 4  | How do you prevent cache stampede in Redis?             | By using request coalescing so only one request recomputes a missing key, combined with TTL jitter and rate-limited database fallback.                                                                                                |
| 5  | What happens if Redis goes down?                        | Cache misses increase and traffic falls back to the database. The system must degrade gracefully using DB fallback, rate limiting, and optionally serving stale or default responses.                                                 |
| 6  | How does Redis scale?                                   | Redis scales vertically via RAM and horizontally using Redis Cluster, which shards keys across nodes using hash slots and supports replicas for availability.                                                                         |
| 7  | What are hot keys and how do you handle them?           | Hot keys are frequently accessed keys that overload a single Redis node. They are mitigated using key sharding, request-level caching, or local in-process caches.                                                                    |
| 8  | Do you enable persistence in Redis?                     | By default, no. Redis is treated as disposable. Persistence is enabled only when cache rebuild is expensive or temporary data loss is unacceptable, accepting the latency trade-off.                                                  |
| 9  | What eviction policy would you choose and why?          | Typically `volatile-lru` or `allkeys-lru`, depending on whether all keys have TTLs. LRU policies help retain hot data under memory pressure.                                                                                          |
| 10 | Can Redis be used for distributed locking?              | Yes, but carefully. Redis-based locks are suitable for best-effort coordination, not strict correctness. Timeouts and failure handling are critical to avoid deadlocks.                                                               |

| # | Question                                          | Interview-Ready Answer                                                                                                                                              |
|---|---------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1 | When would you choose Memcached over Redis?       | When you need a pure, ultra-fast cache with simple key–value access, no persistence, no replication, and minimal operational complexity.                            |
| 2 | What are the limitations of Memcached?            | It has no persistence, no replication, no complex data structures, and no coordination primitives. Node failures result in cache loss.                              |
| 3 | How does Memcached scale?                         | Horizontally via client-side consistent hashing. Each node is independent, allowing linear scaling but causing cache churn when nodes are added or removed.         |
| 4 | What happens when a Memcached node fails?         | All keys on that node are lost and requests fall back to the database. Systems must be designed assuming cache loss is normal.                                      |
| 5 | How do you handle cache stampede with Memcached?  | At the application layer using request coalescing, TTL jitter, and rate-limited DB fallback, since Memcached has no coordination features.                          |
| 6 | Is Memcached strongly consistent?                 | No. It provides eventual consistency only. Stale reads are expected and acceptable.                                                                                 |
| 7 | Why is Memcached considered “simpler” than Redis? | It supports only basic key–value operations, has no persistence or replication, and no server-side logic, resulting in extremely low latency and simple operations. |
| 8 | Can Memcached be used as a primary data store?    | No. Data loss is expected and unrecoverable. It must always sit in front of a durable database.                                                                     |

---
### Glossary

### RDB vs AOF Persistence

| Aspect            | RDB                      | AOF                   |
|-------------------|--------------------------|-----------------------|
| Full Form         | Redis Database Snapshot  | Append-Only File      |
| Definition        | Point-in-time snapshots  | Log of all write ops  |
| Persistence Model | Periodic snapshot        | Write-ahead log       |
| Data Loss Risk    | High (between snapshots) | Low (seconds or less) |
| Disk Usage        | Small                    | Large                 |
| Restart Speed     | Fast                     | Slower                |
| Write Latency     | Very low                 | Slightly higher       |
| Human Readable    | No                       | Yes                   |
| Durability        | Medium                   | High                  |
