# Consistent Hashing

In distributed systems, data/items must be distributed across multiple servers/nodes. 
## Naive Approach

- Use a modulo hash function to map each item to a specific server/node. 
- For example, 
  - if you have `N` servers, you could use `serverIndex = hash(item) % N` to determine which server should store the item, where `N` is the size of server pool.
- This approach works well when the size of the pool is fixed and data distribution is even.

## Problem

- Problem arises when nodes are added or removed (total number of nodes change [`N`]).
- But with above approach almost all keys will be remapped to different nodes in case of change in `N`. This can lead to significant data movement and is not scalable.
- This leads to cache misses, high rehashing cost, and load imbalance.

> We need a hashing mechanism that minimizes re-distribution of keys when nodes change.

## Consistent Hashing

- A hashing technique that ensures:
  - Only a small fraction of keys need to be remapped when nodes join/leave. 
  - Keys are distributed more evenly across servers.

> A special kind of hashing such that when when a hash table is resized and consistent hashing is used, only K/n keys need to be remapped on average, where K is the number of keys and n is the number of slots.


### Key Idea about the technique

| Step                                                                                                                                                 | Depiction                                                          | 
|------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------|
| **Hash Ring**: Represent the hash space as a **hash ring** (0 to 2³² – 1).                                                                           | ![hash-rings](../../images/consitentHashing/hash-ring.png)         | 
| **Hash servers**: Using hash function `f`, we amp servers based on server IP or name onto the ring. Below image shows 4 servers mapped on hash ring. | ![hash-servers](../../images/consitentHashing/hash-servers.png)    | 
| **Hash Keys**: Both keys and nodes (servers) are placed on the ring using same hash function.                                                        | ![hash-keys](../../images/consitentHashing/hash-keys.png)          |
| Each key is assigned to the next node (server) clockwise on the ring.                                                                                | ![finding-servers](../../images/consitentHashing/hash-finding.png) | 

--- 
## Handling Node Changes

### 1. Adding a Node (server)

- Adding a new server involves:
  - Hashing the new server to find its position on the ring.
  - The new server takes over responsibility for keys that fall between it and its predecessor on the ring.
- Only the keys that map to the new server need to be moved, minimizing disruption.
- In below figure, after a new `server 4` is added, only `key0` needs to be remapped.


  ![adding-server](../../images/consitentHashing/adding-server.png)

### 2. Removing a Node (server)

- When a server is removed, its keys are reassigned to the next server clockwise on the ring.
- Again, only the keys that were assigned to the removed server need to be moved.
- In below figure, after `server 1` is removed, only `key1` needs to be remapped.


  ![removing-server](../../images/consitentHashing/removing-server.png)
