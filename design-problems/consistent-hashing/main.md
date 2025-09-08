# Consistent Hashing

In distributed systems, data/items must be distributed across multiple servers/nodes. 
## Naive Approach

- Use a modulo hash function to map each item to a specific server/node. 
- For example, 
  - if you have 4 servers, you could use `hash(item) % 4` to determine which server should store the item.

## Problem

- A common challenge is to ensure that when nodes are added or removed (total number of nodes change [`N`]), the distribution of data remains balanced and minimizes disruption.
- But with above approach almost all keys will be remapped to different nodes in case of change in `N`. This can lead to significant data movement and is not scalable.
- This leads to cache misses, high rehashing cost, and load imbalance.

> We need a hashing mechanism that minimizes re-distribution of keys when nodes change.

## Consistent Hashing