# Consistency 

In distributed systems, consistency means how up-to-date a piece of data is.

## Why is consistency important? 

- A highly consistent system reflects all updates to data, while an inconsistent system provides stale data. 
- Consistency is important because highly consistent systems are easier to reason about and provide a better user experience.


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