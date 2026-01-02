# Redis Cache

> Redis is an in-memory, low-latency data store used primarily for caching, fast data access, coordination, and real-time workloads.

## Problem

| Problems at scale                         | how redis helps                                          |
|-------------------------------------------|----------------------------------------------------------|
| Databases become latency bottlenecks      | Offload reads from databases                             |
| Repeated reads overwhelm storage          | Reduce response latency from milliseconds → microseconds |
| Stateless services need shared fast state | Enable fast, shared, ephemeral state                     |


## Use Cases

- Use Redis when you see:
  - High read-to-write ratio
  - Frequently accessed but rarely updated data
  - Strict latency SLAs (<10 ms)
  - Need for counters, sessions, rate limits, locks
- Avoid Redis when:
  - Data must be permanently durable
  - Large objects or heavy analytical queries are required

## High Level Architecture

`Client → Service → Redis
                 ↓ (miss)
               Database
                 ↓
               Redis (populate)
`
- Redis sits between application and database, absorbing read pressure.

## Core Components & Responsibilities

| Component             | Responsibility                 |
|-----------------------|--------------------------------|
| Redis Server          | Stores data in memory          |
| Client Library        | Handles serialization, retries |
| Eviction Policy       | Manages memory pressure        |
| Persistence (RDB/AOF) | Optional durability            |
| Replicas              | Read scaling & failover        |
| Redis Cluster         | Horizontal sharding            |

## Scalability

- Vertical scaling limited by RAM
- Horizontal scaling via Redis Cluster (hash-slot sharding)
- Reads scale using replicas
- Writes scale via partitioning keys

- Bottleneck:
  - Hot keys
  - Single-threaded command execution

## Reliability & Fault Tolerance

- Redis improves performance but reduces reliability
  - Node failure → cache loss
  - Replication lag → stale reads
  - Persistence adds recovery but increases latency
- Mitigations:
  - Replicas + failover
  - Graceful cache miss handling
  - DB as source of truth


## Common Failure Scenarios

| Problem            | Impact               | Mitigation                                                                                                                                             |
|--------------------|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| Cache stampede     | DB overload          | **Request Coalescing (Cache Stampede Protection):** Ensure only one request recomputes a hot cache entry while others wait or reuse the result.        |
| Hot keys           | Single node meltdown | **Key Sharding (Hot Key Mitigation):** Split a logical hot key into multiple physical keys to distribute read load and avoid single-key bottlenecks.   |
| TTL expiry burst   | Latency spikes       | **Staggered TTLs + Background Refresh:** Add jitter to TTL values and proactively refresh hot keys before expiry to avoid simultaneous expirations.    |
| Eviction misconfig | Critical data loss   | **Proper Eviction Policy + Data Tiering:** Use appropriate eviction policies (e.g., volatile-LRU) and avoid caching critical data without DB fallback. |
| Redis restart      | Cold cache           | **Pre-Warming (Cold Cache Avoidance):** Load critical and frequently accessed keys into Redis before or immediately after traffic is routed.           |


## FAQs

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

## Glossary

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
