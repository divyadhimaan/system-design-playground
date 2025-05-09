# Google Docs - Collaborative Editor Design


## Requirements
1. Storing, retrieving, and editing documents

2. Sharing permissions: READ, EDIT, LINK-SHARING

3. Notifications on email

4. Version History

5. Spell Checking*

6. Comments Support

7. Offline and Collaborative Editing


## Document Schema

We can essentially store document info as below. But the data column will be very big and is not queries as often as the other metadata. 
![Alt text](./../../images/gd-1.png)

### How would we store the metadata of the document?

### 1. Splitting the metadata
A RDBMS should be used to store document metadata - for faster access.
![Alt text](./../../images/gd-2.png)

A SQL database is used for storing content/data of the document.
![Alt text](./../../images/gd-3.png)


> Problems:
> - There are going to be alot of alter queries, since metadata keeps changing.
>  - JOINs are required.
> 
> Thus this way of storing will be expensive and inefficient.

### 2. Storing as json

This way we can store the data and metadata of the document as a json.
![Alt text](./../../images/gd-4.png)


## Storing Document Content

### How large can a document be?

- A document can be up to 1.02 million characters(according to google docs specs). But we can define a document limit of 1 million chars.
- Also keeping in mind images and other media, they donot contribute to the char limit. But a limit can be added (like 100MB).

### How do we store a document of any size?

- `Chunking`: This allows large documents to scale and makes real-time collaboration efficient. 
To manage document content efficiently, we should use a tree-based structure rather than a linked list:

  - `Rope Data Structure`: A balanced binary tree where each leaf node stores a text chunk. This allows efficient random access, insertion, and deletion in O(log n) time.

  - Advantages over LinkedList: Supports both forward and backward traversal, avoids propagation issues during edits, and scales well for documents with millions of characters.

  - Real-world systems (e.g., collaborative editors) use similar tree or piece-table structures to handle concurrent editing and large-scale content management.



### What type of database is suitable for this?

- NoSQL DB (e.g., MongoDB, Cassandra, DynamoDB)
- Good if you want flexible schema + high write throughput.
-  Used in systems where eventual consistency is acceptable for some parts


## Version History

Keeping the original document in the storage as is, we would maintain a seperate table for storing the versions of the document. (version-id -> content)
### Versioning Strategy

- Delta-based saving (per edit or per batch):
  - Store small diffs (deltas) for each user edit (e.g., insert/delete ops).

  - These are lightweight and make real-time collaboration efficient.
  - Deltas also allow the undo feature to be used.
  
- Full snapshot versions (checkpointing):
  - Store a full snapshot of the document after every N edits or T time interval.
  - Example:
    - Every 100 edits or every 30 minutes, whichever comes first (Cron Job)
  - Snapshots make it faster to restore or go back in history.
  - We have to keep in mind not to cause `Thundering herd problem`.
  
- We should have a hybrid model of storing both.
  - Efficient: Storing deltas keeps storage small.
  - Recoverable: Snapshots prevent the need to replay millions of deltas.
  - Scalable: Works even for massive docs with thousands of updates per hour.
  
> We store every edit as a delta (insert/delete op), and periodically checkpoint the document as a full snapshot every 100-500 edits or every 5-10 minutes to balance storage efficiency and recovery speed.


#### Thundering Herds in Crons

- Thundering Herd happens when many cron jobs (or tasks) all start at the same time (e.g., at 00:00).

- They all compete for CPU, DB, disk, or network at once → causing spikes and system overload.


    > Example - Suppose you have 10,000 users and every user’s daily backup cron is scheduled for midnight (00:00).
    > 
    > Result at 00:00:
    > - 10,000 jobs hit the server/DB at the same second
    > - Causes traffic spike, DB lock contention, possible failures or slowdowns.


- Solution
  - Avoid all jobs starting at once -> Spread them evenly over time.

  - Use `cron indexes` (a unique number per job) to calculate staggered timings.(Staggered time means spreading tasks over different times instead of running them all at once.)
  - cron index -> (total documents/time)
  
- We assign each cron job a unique cron index and compute its schedule by spreading execution time across available minutes/hours using modulo arithmetic, effectively avoiding thundering herd problems.


## Cost Optimization

> We compress document versions to save storage and speed up transfer, while caching the most recent version in-memory for fast access. We balance compression level to avoid excessive CPU cost during decompression.

### 1. Compression
   - `Why?` Storing full document versions or deltas consumes a lot of storage.

   - `What we do`: Compress versions before storing (using gzip, brotli, zstd).

   - Result (Pros): 
     - Storage cost drops (smaller files).
     - Faster network transfer (less data over wire).
   - Cons:
     - Extra CPU cost: Decompressing takes processing time
     - Slower reads if overdone:	Frequent decompressing can slow queries
   - Use lightweight compression for frequently accessed data, and strong compression for cold storage.

### 2. Caching the Most Recent Version
  - `Why?` Most reads/edits happen on the latest version.

  - `What we do:` Cache latest version in-memory (using Redis, Memcached).

  - Result (Pros):
    - Instant access to most common version.
    - Reduces DB/storage load.
  
## Logging Operations

> We log user operations (insert, delete, format) as lightweight events for fast writes. Reads reconstruct the document by applying logs on top of periodic snapshots, allowing efficient versioning and collaborative editing.

>To balance log size and read speed, we periodically create snapshots and prune old logs.

`Why?` Instead of rewriting the whole document on every change -> we log only the user operations (insert, delete, format, etc.)

- This gives us:
  - Fast writes (small logs instead of big writes)

  - Fast reads (can reconstruct latest version quickly)

- Working
  - Each Edit = Logged Operation
    - User types "a" -> we log:
{ op: "insert", char: "a", pos: 5, user: "u1", timestamp: T }
    - User deletes -> { op: "delete", pos: 3, user: "u2", timestamp: T2 }
  - Log Structure
    - Stored as an ordered list (by timestamp or operation version).
    - Often backed by:
      - Log-structured storage (like LSM Trees)
      - Or append-only systems (like Kafka, Write-Ahead Log)


### Benefits

| Feature                 | Why it's Good                                         |
| ----------------------- | ----------------------------------------------------- |
| **Fast writes**         | Just append small operation logs (cheap I/O)          |
| **Fast reads**          | Can replay logs or use snapshot + logs                |
| **Collaborative edits** | Multiple user ops merge nicely with OT/CRDT           |
| **Versioning**          | Easy to reconstruct any past version by replaying ops |


### Challenges

| Problem             | Solution                                               |
| ------------------- | ------------------------------------------------------ |
| **Log grows big**   | Periodic **snapshots** (checkpointing)                 |
| **Merge conflicts** | Use **Operational Transform (OT)** or **CRDT**         |
| **Latency**         | Cache **latest document state** (logs applied up to T) |


## Concurrent Writes with locks

- If **2 users edit same doc**:
  - Write locks = only 1 can edit -> others must wait (`pessimistic lock`).
  - `Optimistic lock` = both edit, but loser must retry -> **wasteful**.
- **Exclusive locks** on the document = kills concurrent writes -> **too slow**.
-  **Locking doesn’t scale** for real-time collaborative editing.


- Locking (optimistic or pessimistic) is **not suitable** for collaborative docs.
- Need to use **operation-based syncing models** like **OT (Operational Transformation)** or **CRDT (Conflict-free Replicated Data Types)** instead of locking.


|                       | **Optimistic Locking**                          | **Pessimistic Locking**                        |
|-----------------------|---------------------------------------------------|--------------------------------------------------|
| **How it works**   | Reads record with version, checks version before write | Locks record exclusively until transaction finishes |
| **Atomicity**      | Updates only if version matches                   | No concurrent writes allowed during lock         |
| **On conflict**    | Aborts, transaction must retry or fail             | Blocks other writers until lock is released      |
| **Best for**       | High-volume systems, stateless 3-tier apps         | Direct DB connections (2-tier), distributed TxIDs |
| **Drawbacks**      | Wasteful retries if high concurrency               | Slow, causes contention and risk of deadlocks    |

