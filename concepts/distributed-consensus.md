# Distributed Consensus with Paxos

### Pre-requisites
- [Consistency Levels](./consistency.md#consistency-levels)
- [Quorum](./consistency.md#quorum)
- [Transaction Isolation Levels](./consistency.md#isolation-levels)
- [CAP Theorem](./../Readme.md#cap-theorem-brewers-theorem)
- [ACID Properties](./../Readme.md#acid-properties)

## Two Phase Commit

| Phase        | Description                                                                 | Use Case Example                    |
|--------------|-----------------------------------------------------------------------------|-------------------------------------|
| Phase 1: Prepare | Coordinator asks all participants if they can commit (vote yes/no).         | Coordinating DB changes across services |
| Phase 2: Commit / Abort | If all vote yes → send commit; if any vote no → send abort to all.     | Distributed transactions in banking apps |

## Basics

### Consensus
Paxos is designed to **achieve consensus** in a distributed system through **majority agreement** (more than 50% of nodes must agree on a value).


### Distributed Nature
Operates in environments with potential **failures and unreliable message delivery**. Handles node crashes and network issues.


### Use Cases 
Used in systems like **Apache Zookeeper** and **Google Chubby** for tasks such as **distributed locking** and maintaining **order in distributed logs**.

### Importance

Distributed consensus is complex due to **failures and timeouts**. Paxos offers a **fault-tolerant way to reach agreement** even in such scenarios. 