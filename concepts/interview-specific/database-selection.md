# Database Selection

## First Principle

> There is no perfect DB — you choose based on access patterns + consistency needs + scale.

Interviewer wants to see thought process, not just tech names.

---

## Start With This Decision Tree

| Workload                  | Choose                                   |
|---------------------------|------------------------------------------|
| **Read-heavy**            | Cache + Read replicas + SQL/NoSQL        |
| **Write-heavy**           | NoSQL (Cassandra, DynamoDB), sharded SQL |
| **Analytics**             | OLAP DB (BigQuery, Snowflake)            |
| **Complex queries/joins** | SQL (PostgreSQL, MySQL)                  |
| **Key-value lookups**     | Redis, DynamoDB                          |
| **Document-based data**   | MongoDB, Couchbase                       |
| **Time-series**           | TimescaleDB, InfluxDB                    |
| **Graph relations**       | Neo4j                                    |

---

## Start by Identifying Access Patterns

Ask yourself:

- Do I need joins? 
- Do I need transactions? 
- Is consistency or availability more important? 
- Do I have high write throughput? 
- Will the data grow horizontally?

This alone eliminates 70% of the options.

---

## SQL vs NoSQL (Interview-standard reasoning)

Choose SQL when:
- Strong consistency needed
- Joins and relational queries
- ACID transactions 
- Moderate scale but needs correctness 
- Example systems: payments, orders, banking

Choose NoSQL when:

- Need horizontal scaling 
- High write throughput 
- Schema-flexible 
- Eventual consistency is fine 
- Example systems: feeds, logs, messaging, analytics, IoT


---
## NoSQL Types (and when to pick)
### 1. Key-Value (Redis, DynamoDB)

- Ultra-fast lookups 
- Sessions, caching, tokens 
- Good for hot data

### 2. Document Store (MongoDB)

- Semi-structured data 
- User profiles, products, JSON blobs

### 3. Columnar Store (Cassandra, BigTable)

- High write scale
- Time-series, logging, metrics

### 4. Graph DB (Neo4j)
- Friends-of-friends, recommendations, fraud graphs


---

## Consistency vs Availability (CAP Thinking)

- If CA needed:
  - Single region SQL
  - Financial transactions

- If CP needed:
  - MongoDB with majority writes 
  - HBase 
  - Zookeeper-type systems

- If AP needed:
  - Cassandra 
  - DynamoDB 
  - Systems prioritizing availability


--- 

## Interview Template

> Interviewer: Which DB would you choose for an Instagram-like feed?
> 
> Answer: The system is write-heavy and needs horizontal scaling.
Posts are append-only, and strong consistency is not required for the feed.
A column-family NoSQL store like Cassandra fits because it offers high write throughput, tunable consistency, and time-series storage.
For user profile or relational data, I would still maintain PostgreSQL.

> Interviewer: Why PostgreSQL
> 
> Answer: User profiles require strong consistency, data integrity, relational queries, and unique constraints. 
> PostgreSQL is perfect here because it gives ACID transactions, powerful indexing, and clean relational modeling without needing massive horizontal scaling.

---

## Database Choice Justification

1. Start with the Access Pattern

    > First, let’s look at the access pattern. This system primarily needs {read/write-heavy? joins? transactions? time-series? key-value lookups?}.

2. State the Consistency Need

    > The system needs {strong consistency / eventual consistency / tunable consistency}, especially for {critical part of system}.

3. Justify SQL vs NoSQL 
   - If SQL:
    > A relational store fits well because the data is structured, relationships matter, and transactions + correctness are important.

   - If NoSQL:
    > A NoSQL store fits because we need horizontal scaling, flexible schema, and extremely high write/read throughput.

4. Tie It to the Specific Use Case

   > For {user profiles / orders / payments}, integrity & atomic updates matter → SQL like PostgreSQL.
   > For {feeds / logs / messaging / analytics}, scale & write throughput matter → NoSQL.

5. Choose the DB + Reason (2 strong points only)

    > I’d choose {database name} because it gives:
   > {Reason 1: e.g., high write throughput, ACID, global indexes} 
   > {Reason 2: e.g., horizontal scaling, low-latency lookups, rich indexing}

6. Mention Complementary DBs (Polyglot Persistence)

    > We don’t need a single DB for everything. For best performance:
   > {Database A} for {component} 
   > {Database B} for {component}
   > This shows you think like a real system designer.

7. Optional (Bonus)

> We can add cache (Redis) in front for hot reads and reduce DB load.
> We’ll monitor DB using slow query logs + auto-scaling + read replicas.