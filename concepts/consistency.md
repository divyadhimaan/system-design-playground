# Consistency 

In distributed systems, consistency means how up-to-date a piece of data is.

## Why is consistency important? 

- A highly consistent system reflects all updates to data, while an inconsistent system provides stale data. 
- Consistency is important because highly consistent systems are easier to reason about and provide a better user experience.


# Consistency Levels
## Linearizable Consistency

### Definition
- Linearizable consistency is the strongest consistency model in distributed systems. 
- It ensures that every read operation reflects the result of the latest completed write operation.
- This means any read will see all changes made by previous write operations, regardless of which node in the system processed the request.

### Example

Suppose `x = 10` initially. Now:

```
write x = 13
write x = 17
read x → returns 17
write x = 1
read x → returns 1
```

A linearizable system guarantees the following:
- The read after `write x = 17` will **not return 13** or **10**, because `17` is the most recent write.
- The read after `write x = 1` will return `1`.


### Benefits  
- Predictable and intuitive behavior — what you write last is what you read next.
- Strongest consistency guarantee: simplifies debugging and system correctness.
- Ideal for use cases like financial transactions, leader elections, inventory counts, etc.

### Drawbacks  
- **High latency**: Requires coordination between all replicas to confirm latest writes.
- **Low availability**: If even one replica is unreachable, the system may delay or reject requests.
- **Scalability limitations**: Coordination and ordering overhead grows with more nodes.

### Implementation  
- **Single-threaded server**: Simplest way to achieve linearizability — all reads and writes pass through a single ordered thread.
- **Consensus protocols**: Use Paxos or Raft to maintain a globally agreed-upon order of operations.
- **Replicated state machines**: All replicas apply operations in the same order.
- **Quorum-based systems**: Ensure majority of nodes agree on the value before confirming read/write (e.g., read/write quorums).

## Eventual Consistency

#### Definition  
- Eventual consistency is a **weaker consistency model** used in distributed systems. 
- It allows the system to return **stale (outdated) data** temporarily, with the guarantee that, if no new updates are made to the data, **all replicas will eventually become consistent** and reflect the latest value.

- During the inconsistency window, different nodes may return different values for the same data, but the system will **converge** to the latest state over time.

### Use Case  
Used in systems where `high availability`, `scalability`, and `low latency` are more important than immediate consistency — especially in scenarios where **temporary staleness is acceptable**.

Examples include social media feeds, product recommendation engines, or shopping cart updates.

### Example  
Suppose a system initially has `x = 5`. Now:
```
write x = 10 # initiated
read x → returns 5 # processed before write takes effect
(read returns stale data)
...time passes...
read x → returns 10 # all replicas eventually updated
```
![Alt text](./../images/consistency-1.png)

Here, the read is served `before` the write has fully propagated to all replicas, so stale data (`x = 5`) is returned. Later, the system catches up, and all subsequent reads return the correct data (`x = 10`).

### Benefits  
- **Highly available**: Can serve reads even during network partitions.
- **Low latency**: Faster response since reads can be served from any replica.
- **Scalable**: Suitable for large-scale distributed systems.

### Drawbacks  
- Clients may observe stale data temporarily.
- Not suitable for scenarios requiring strong consistency (e.g., financial apps).
- Developers must handle convergence and possible read-after-write inconsistencies.

### Implementation  
- **Asynchronous replication**: Writes propagate in the background across replicas.
- **Concurrent processing**: Reads and writes processed in parallel using multi-threaded or multi-node setups.
- **Conflict resolution mechanisms**: For resolving divergent states (e.g., last-write-wins, vector clocks, CRDTs).
- Used by systems like **Amazon DynamoDB**, **Cassandra**, **Riak**, and **Couchbase**.


## Causal Consistency

### Definition  
- Causal consistency is a **middle-ground consistency model** where operations that are **causally related** must be seen by all processes in the same order. 
- However, operations that are **not causally related** can be seen in different orders on different nodes.

- In simpler terms, if one operation potentially influences another (e.g., a read depending on a previous write), then all nodes must process them in that causal order. 
- If there’s no causal link, then the order doesn't matter.

### Use Case  
- Causal consistency is used in distributed systems that need to maintain **data correctness across related operations** without the performance cost of strict consistency models like linearizability.

- It is especially useful in collaborative applications, chat apps, and systems that require **partial ordering** of events.

### Real Example  
Consider the following operations:

```
1. update x = 20
2. update y = 10
3. read x
4. update x = 2
5. read y
```

- `read x` must reflect the result of `update x = 20` because they are causally related.
- `update y` is independent of `x`, so it can be executed in any order relative to operations on `x`.

Hence:
- Operations (1, 3, 4) are executed on one thread/server.
- Operations (2, 5) are executed on another thread/server.

This preserves **causal ordering** for related operations, but allows **parallelism** for unrelated ones.

### Benefits  
- **Stronger than eventual consistency**: related updates maintain order.
- **Better performance than linearizable consistency**: avoids global coordination and waiting for unrelated writes.
- **Supports availability and partition tolerance** in distributed systems.

### Drawbacks  
- **Fails for aggregation queries** involving multiple keys or IDs.
- **Complex to reason about** when operations span multiple unrelated entities.
- **Partial ordering** is harder to implement and debug than total or no ordering.


### Aggregation Limitation Example

Consider a table:

| uid | value |
|-----|--------|
| 1   | 20     |
| 2   | 10     |
| 3   | 30     |

Now the operations:

```
i. read sum -> returns 60
ii. update uid 1 = 10
iii. read sum -> expected: 50
iv. update uid 1 = 5
```

Depending on the order of execution:
- If reads (i, iii) happen before updates, sum is 60 → 60 (wrong).
- If reads happen after all updates, sum is 45 → 45 (wrong).
- Only one specific ordering will give the correct result (60 → 50).

This inconsistency happens because **causal consistency tracks per-key dependencies**, but **aggregation involves multiple keys**, leading to incorrect results.

## Implementation  
- **Track causal dependencies**: using vector clocks, version vectors, or Lamport timestamps.
- **Group related operations** on the same key or context together to preserve their order.
- Systems like **Cassandra (with tuning)**, **COPS**, **Bayou**, and **Orleans** offer causal consistency features.
- **Client-based tracking**: Clients may carry dependency metadata to help servers maintain order.
 