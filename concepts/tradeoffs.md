# System Design Tradeoffs

- [System Design Tradeoffs](#system-design-tradeoffs)
  - [Pull vs Push Architecture](#pull-vs-push-architecture)
    - [Tradeoff](#tradeoff)
    - [Hybrid Model](#hybrid-model)
      - [Why Use Hybrid Model?](#why-use-hybrid-model)
  - [Memory vs Latency](#memory-vs-latency)
    - [Tradeoff](#tradeoff-1)
    - [Medium Approach (Best of Both)](#medium-approach-best-of-both)
  - [Throughput vs Latency](#throughput-vs-latency)
    - [Tradeoff](#tradeoff-2)
    - [Balanced Approach](#balanced-approach)
  - [Consistency vs. Availability](#consistency-vs-availability)
    - [Tradeoff](#tradeoff-3)
  - [| **Tradeoff Scenario**      | User might get “error” or wait if data isn’t synced          | User gets older data instantly (better UX, less accurate)  |](#-tradeoff-scenario-------user-might-get-error-or-wait-if-data-isnt-synced-----------user-gets-older-data-instantly-better-ux-less-accurate--)
    - [Balanced Approach (in Distributed Systems)](#balanced-approach-in-distributed-systems)

## Pull vs Push Architecture

### Tradeoff
| Aspect                     | Pull Architecture                                          | Push Architecture                                           |
|---------------------------|-------------------------------------------------------------|-------------------------------------------------------------|
| **Who initiates**         | Client initiates the data request                          | Server pushes data to client                                |
| **Latency**               | Higher latency (depends on user action)                    | Lower latency (real-time updates)                           |
| **Complexity**            | Simpler to implement and scale                             | More complex (requires managing connections/subscriptions)  |
| **Resource Usage**        | Efficient server usage (no need to track clients)          | Heavier server/network load                                 |
| **User Experience**       | Data updates only when user requests                       | Immediate, real-time updates                                |
| **Failure Handling**      | Easier to handle offline clients                           | Harder — need to handle retries, disconnections             |
| **Example in Instagram**  | Pull-to-refresh feed to get latest posts  | Push notifications (likes, DMs), real-time messaging        |
| Benefits | Lesser Peak load on system, client decides | low latency, consumes less memory(for stale notification)

---

### Hybrid Model
A hybrid model often uses message queues or event streams to balance between push and pull architectures, combining the benefits of both.

| Aspect                      | Hybrid Approach (Using Message Queue)                            |
|----------------------------|-------------------------------------------------------------------|
| **Core Idea**              | Server pushes events to a queue; clients pull from or are notified via the queue |
| **How it works**           | Producer (e.g., user action) → sends message → Message Queue (e.g., Kafka, RabbitMQ) → Consumer (client or another service) |
| **Push Usage**             | Server pushes messages to queue in real-time                      |
| **Pull Usage**             | Consumers can **poll or subscribe** to queue depending on setup   |
| **Scalability**            | Highly scalable; decouples producers & consumers                  |
| **Latency**                | Near real-time if consumers subscribe; delayed if polling         |
| **Reliability**            | Reliable delivery with retry, acknowledgement, dead-letter queues |
| **Use Case in Instagram**  | DM service → New message → Kafka topic → Notification service → Push Notification |
|                            | Feed update → message stored in queue → user pulls latest posts   |

#### Why Use Hybrid Model?
- Combines **real-time push** (notifications, alerts) with **on-demand pull** (feed, profile data)
- Offers **loose coupling**, better **fault tolerance**, and **scalability**
- Allows different services to react to the same event (e.g., analytics, moderation, notification)


## Memory vs Latency

### Tradeoff
| Aspect                      | High Memory Usage (Caching)                                  | Low Memory Usage (Less/No Caching)                         |
|----------------------------|---------------------------------------------------------------|------------------------------------------------------------|
| **Latency**                | Very low latency (e.g., ~10ms for cached Instagram data)     | Higher latency due to DB or remote fetch                  |
| **Resource Usage**         | More RAM required, higher infrastructure cost                | Less memory, cheaper on resources                         |
| **Scalability**            | Scales better for read-heavy workloads                       | May become a bottleneck under high load                   |
| **Consistency**            | Might serve stale data unless invalidation is handled        | Always latest data (if hitting source directly)           |
| **Example in Instagram**   | Popular profiles/followers count cached in memory            | Lesser-known profiles fetched on-demand                   |
| **Failure Handling**       | Resilient if DB goes down (cache-aside)                      | Complete failure if backend is unavailable                |

---

### Medium Approach (Best of Both)
- **Keep hot data in cache** using:
  - **Eviction policies** (LRU, LFU, TTL-based)
  - **Write policies** (write-through, write-behind)
- Adjust cache size based on **user activity patterns**
- Use **tiered storage**: in-memory cache (e.g., Redis) + fallback to DB


## Throughput vs Latency

### Tradeoff

| Aspect                      | High Throughput                                              | Low Latency                                                 |
|----------------------------|--------------------------------------------------------------|-------------------------------------------------------------|
| **Definition**             | Number of requests processed per unit time (e.g., req/sec)  | Time taken to complete a single request                    |
| **Focus**                  | Maximize system capacity                                     | Minimize response time per request                         |
| **Suitable for**           | Bulk processing, batch uploads, analytics                    | Real-time systems, interactive user experiences            |
| **Performance Metric**     | Requests per second (RPS), messages per sec, bandwidth       | Milliseconds per operation                                 |
| **Optimization Strategy**  | Use queues, batch jobs, parallelism                          | In-memory caching, low-latency DBs, fast networks          |
| **Example in Instagram**   | Processing likes/views in bulk in background (e.g., analytics update) | Loading a user’s feed or story instantly (~ms latency)     |
| **Tradeoff Scenario**      | Increasing throughput may add queuing/wait time per request  | Lower latency might limit how many requests are handled concurrently |

---

### Balanced Approach
- Use **asynchronous processing**: fast response + high throughput in background.
- Queue background tasks (e.g., story views, analytics) while serving UI immediately.
- Tune based on use-case: low latency for frontend, high throughput for backend jobs.


## Consistency vs. Availability

### Tradeoff

| Aspect                      | Consistency                                                   | Availability                                                |
|----------------------------|---------------------------------------------------------------|-------------------------------------------------------------|
| **Definition**             | All nodes return the most recent write (no stale data)       | System continues to serve requests even if some nodes fail |
| **User Experience**        | Always accurate, up-to-date data                             | System is responsive, even if some data might be outdated  |
| **Failure Scenario**       | Might reject requests if not all nodes agree                 | May serve stale data but won’t reject requests             |
| **Suitable for**           | Banking, inventory, order processing                         | Social media, content delivery                             |
| **Example in Instagram**   | Ensuring a DM is only marked “seen” after delivery confirmation | Viewing a feed or stories even if one region’s DB is down |
| **Tradeoff Scenario**      | User might get “error” or wait if data isn’t synced          | User gets older data instantly (better UX, less accurate)  |
---
### Balanced Approach (in Distributed Systems)
- Use **eventual consistency** where strong consistency isn’t critical.
- Design **fallback mechanisms** (e.g., show cached feed if DB is unreachable).
- **Partitioned system** may prefer availability (AP) or consistency (CP) depending on context.
