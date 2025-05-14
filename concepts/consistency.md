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