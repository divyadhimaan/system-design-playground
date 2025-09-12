# Unique ID generator

A service or algorithm that generates globally unique identifiers across distributed systems.
- Properties can vary:
  - Globally unique (no collisions). 
  - K-sortable (IDs roughly increase with time → helps in DB sharding). 
  - Compact (smaller than UUIDs for efficiency). 
  - Stateless vs Stateful depending on design.
  

> A naive approach is to use a primary key in a central database with auto-increment. But this creates a bottleneck and single point of failure.

## Problem Statement
- In large-scale distributed systems, every entity (user, order, transaction, message, etc.) needs a unique identifier.
- Challenges:
  - **Collisions**: If multiple nodes generate IDs independently, they might overlap. 
  - **Scalability**: Must support billions of IDs efficiently. 
  - **Ordering**: Sometimes we need time-ordered IDs (e.g., Kafka offsets, Twitter Snowflake). 
  - **Availability**: System should not depend on a single machine or DB auto-increment (bottleneck, SPOF). 
  - **Low latency**: ID generation must be very fast, ideally <1ms.

## Step 1: Understand Requirements and Design Scope

> Q. What are characteristics of the unique IDs?
> 
> A. IDs must be globally unique and k-sortable (k: number of servers).

> Q. For each new record, does ID increment by 1?
> 
> A. The ID increments by timestamp, not by 1. IDs should be ordered by date.


> Q. Do IDs only contain numbers, or can they include letters/symbols?
> 
> A. IDs are only numeric.

> Q. What is the expected scale of ID generation (e.g., number of IDs per second)?
> 
> A. The system should handle up to 10,000 ID requests per second.


### Requirements
- IDs must be unique across distributed systems.
- IDs should be k-sortable (roughly ordered by date).
- IDs should be numeric.
- The system should handle up to 10,000 ID requests per second.
- IDs should fit within 64 bits.
- The system should be highly available and fault-tolerant.
- The system should have low latency (ID generation should be <1ms).