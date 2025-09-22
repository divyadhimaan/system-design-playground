# System Design

This repository contains notes, diagrams, and code snippets created while learning system design concepts. It's intended as a personal reference for revision and interview preparation. Topics include high-level design patterns, scalability principles, common system architectures, and more.

## Table of Contents

- [System Design](#system-design)
  - [Table of Contents](#table-of-contents)
  - [Concepts](#concepts)
  - [Additional Concepts](#additional-concepts)
  - [System Design Problems](#system-design-problems)
  - [Resources](#resources)
  - [Articles](#articles)
  - [How to Use This Repo](#how-to-use-this-repo)
  - [Contributing](#contributing)
  - [Glossary](#glossary)
    - [CAP Theorem (Brewer's Theorem)](#cap-theorem-brewers-theorem)
      - [CP - consistency and partition tolerance](#cp---consistency-and-partition-tolerance)
      - [AP - availability and partition tolerance](#ap---availability-and-partition-tolerance)
    - [ACID Properties](#acid-properties)
    - [Consistent Hashing](#consistent-hashing)
    - [FFMPEG](#ffmpeg)
    - [AVI](#avi)
    - [Homebrew](#homebrew)
    - [HLS](#hls)
    - [DASH](#dash)
    - [PSTN (Public Switched Telephone Network)](#pstn-public-switched-telephone-network)
    - [VoIP (Voice over Internet Protocol)](#voip-voice-over-internet-protocol)
    - [SIP (Session Initiation Protocol)](#sip-session-initiation-protocol)

## Concepts
- [Databases](./concepts/databases.md)
- [Consistency in Distributed Systems](./concepts/consistency.md)
- [Caches](./concepts/caching.md)
- [Networks](./concepts/networks.md)
- [Data Replication and Migration](./concepts/data-replication-and-migration.md)
- [Security in Distributed Systems](./concepts/security.md)
- [Observability in Distributed Systems](./concepts/observability.md)
- [Distributed Consensus](./concepts/distributed-consensus.md)
- [Rate Limiting](./concepts/rate-limiting.md)
- [Tradeoffs](./concepts/tradeoffs.md)

## Additional Concepts
- [API design](./concepts/api-design.md)

## System Design Problems
- [Email Service](./design-problems/emailing-service/main.md)
- [Tinder Design - Recommendation](./design-problems/tinder/main.md)
- [Whatsapp Design - Chat Application](./design-problems/whatsapp-design/main.md)
- [Google Docs - Collaborative Editor Design](./design-problems/google-docs/main.md)
- [Uber - Cab Aggregator App](./design-problems/uber/main.md)
- [Workflow Management and Recommendation](./design-problems/workflow-management/main.md)
- [Whatsapp Calling](./design-problems/whatsapp-calling-app/main.md)
- [Rate Limiter](./design-problems/rate-limiter/main.md)
- [Consistent Hashing](./design-problems/consistent-hashing/main.md)
- [Key Value Store](./design-problems/key-value-store/main.md)
- [Unique ID generator](./design-problems/unique-id-generator/main.md)
- [Web Crawler](./design-problems/web-crawler/main.md)

## Resources
A collection of materials referred to while learning:

- System Design Course by Gaurav Sen
- [System Design Primer](https://github.com/donnemartin/system-design-primer)
- [System Design Interview - Alex Xu (Part 1)](./resources/system-design-interview-an-insider-guide-by-Alex-Yu.pdf)
<!-- - [Grokking the System Design Interview](https://www.designgurus.io/course/system-design) -->

## Articles
- [Optimizing Netflix APIs](https://netflixtechblog.com/optimizing-the-netflix-api-5c9ac715cf19)
- [Service Discovery](https://www.f5.com/company/blog/nginx/service-discovery-in-a-microservices-architecture)
- [Estimations - Numbers everyone should know](https://highscalability.com/numbers-everyone-should-know/)

## How to Use This Repo
- Browse by topic directories or use the table of contents above.
- Diagrams are stored under `/diagrams` and topic-specific folders.
- Use markdown files as revision notes before interviews.
- Refer to example architectures for designing systems in projects.


## Contributing
This is a personal learning repo, but feel free to open issues or PRs if you spot something helpful to add.

---

## Glossary
### CAP Theorem (Brewer's Theorem)
In a distributed computer system, you can only support two of the following guarantees:

- `Consistency` - Every read receives the most recent write or an error (All nodes see the same data at the same time.)
- `Availability` - Every request receives a response, without guarantee that it contains the most recent version of the information (The system is always responsive.)
- `Partition Tolerance` - The system continues to operate despite network failures or delays.
(No data loss or crash due to communication break between nodes.)

Networks aren't reliable, so you'll need to support partition tolerance. A distributed system must be partition-tolerant (P). So, a practical tradeoff is between Consistency (C) and Availability (A).

#### CP - consistency and partition tolerance
- Waiting for a response from the partitioned node might result in a timeout error. 
- CP is a good choice if your business needs require atomic reads and writes.
- Banking systems prefer CP

#### AP - availability and partition tolerance
- Responses return the most readily available version of the data available on any node, which might not be the latest. 
- Writes might take some time to propagate when the partition is resolved.
- social media feeds prefer AP.

### ACID Properties
ACID is a set of properties that ensure reliable, consistent, and safe transactions in a database system.

| Property    | Ensures...                           | Example                          | Use Case                                 |
|-------------|--------------------------------------|----------------------------------|------------------------------------------|
| Atomicity   | All or none execution                | Debit fails → entire txn rolled back | Bank transfers                          |
| Consistency | Valid state transitions              | No violation of constraints      | E-commerce inventory updates             |
| Isolation   | No interference between transactions | Prevents dirty reads/race conditions | Online booking systems                  |
| Durability  | Changes survive system failures      | Data saved after commit          | Order confirmations in retail apps      |

---
### Consistent Hashing
Consistent Hashing maps servers to the key space and assigns requests(mapped to relevant buckets, called load) to the next clockwise server. Servers can then store relevant request data in them while allowing the system flexibility and scalability.

---
### FFMPEG
FFmpeg is a powerful, free, and open-source multimedia framework used for decoding, encoding, transcoding, and streaming audio and video files.


---
### AVI

AVI, or Audio Video Interleave, is a multimedia container format developed by Microsoft to store both audio and video data in a single file, allowing for synchronized playback. 

---

### Homebrew

Homebrew is a package manager for macOS (and Linux) that simplifies the installation, updating, and management of software on your system.

---
### HLS
- HLS, or HTTP Live Streaming, is a widely used video streaming protocol developed by Apple that delivers audio and video content over the internet. 
- It's known for its adaptability to changing network conditions, reliability, and compatibility with various devices and browsers. 
- HLS works by breaking down video files into smaller segments, which are then downloaded and played sequentially by a video player.
---

### DASH

- Dynamic Adaptive Streaming over HTTP (DASH) is a streaming technology that adapts video quality in real-time based on network conditions, delivering high-quality video over the internet using standard HTTP servers. 
- It achieves this by breaking down video content into segments, each with varying bitrates, and allowing the client device to dynamically select the most appropriate segment for the current network bandwidth

---

### PSTN (Public Switched Telephone Network)
- Traditional circuit-switched telephone network.
- Requires **telephone lines** and **dedicated bandwidth** per call.
- **Cost depends** on distance and duration.
- Generally **reliable but expensive**.
- Example: Landline phone systems.

---
### VoIP (Voice over Internet Protocol)
- Transmits voice calls over the **internet** using **packet-switching**.
- Requires only **internet connectivity** (no phone lines).
- **Cost-effective** and often **free**.
- **Scalable and flexible**, but quality depends on internet speed.
- Example: Skype, Zoom, WhatsApp calls.

---
### SIP (Session Initiation Protocol)
- **Signaling protocol** used to establish, manage, and terminate **VoIP** calls.
- Handles **call setup, teardown, and modifications**.
- Works with other protocols (e.g., RTP for media transmission).
- Used for **voice, video, and messaging sessions** over IP.
- Example: Used in VoIP softphones and enterprise telephony systems.

---

### NTP (Network Time Protocol)
- Protocol for synchronizing clocks of computer systems over packet-switched, variable-latency data networks.
- Uses a hierarchical system of time sources, with primary servers connected to highly accurate reference clocks.
- Ensures accurate timekeeping for applications like logging, authentication, and scheduling.
- Example: Synchronizing server times in data centers.
---
### Redis Lua Scripts
- Lua = lightweight, high-performance, embeddable scripting language. 
- Designed to be fast, portable, and simple.
- Often used in:
  - Game engines (e.g., Roblox, World of Warcraft mods). 
  - Embedded systems. 
  - Databases (e.g., Redis) for scripting logic.
- In System Design, Redis supports Lua scripting to run custom logic atomically inside Redis.
- Instead of multiple round-trips (client ↔ Redis), you can:
  - Upload Lua script.
  - Execute on server in one step.
- Why Lua Scripts:
  - Ensures atomicity: script executes as a single operation.
  - Reduce network overhead: One call instead of multiple client-server requests.
- Example:
  - Say you want to check if a key exists, and if not, set it.
  - Without Lua:
    - Client does GET key. 
    - If not exists → does SET key value. 
    - Problem: race condition (another client may set in between).
  - With Lua:
  ```lua
  if redis.call("EXISTS", KEYS[1]) == 0 then
  return redis.call("SET", KEYS[1], ARGV[1])
  else
  return nil
  end
  ```
  - Call from client
  ```bash
  EVAL "if redis.call('EXISTS', KEYS[1]) == 0 then return redis.call('SET', KEYS[1], ARGV[1]) else return nil end" 1 myKey myValue
  ```
  
---
