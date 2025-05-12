# System Design

This repository contains notes, diagrams, and code snippets created while learning system design concepts. It's intended as a personal reference for revision and interview preparation. Topics include high-level design patterns, scalability principles, common system architectures, and more.

## Table of Contents

- [System Design](#system-design)
  - [Table of Contents](#table-of-contents)
  - [Concepts](#concepts)
  - [System Design Problems](#system-design-problems)
  - [Resources](#resources)
  - [Articles](#articles)
  - [How to Use This Repo](#how-to-use-this-repo)
  - [Contributing](#contributing)
  - [Glossary](#glossary)
    - [Consistent Hashing](#consistent-hashing)
    - [FFMPEG](#ffmpeg)
    - [AVI](#avi)
    - [Homebrew](#homebrew)
    - [HLS](#hls)
    - [DASH](#dash)


## Concepts

- [Databases](./concepts/databases.md)
- [Consistency in Distributed Systems](./concepts/consistency.md)
- [Caches](./concepts/caching.md)
- [Networks](./concepts/networks.md)
- [Data Replication and Migration](./concepts/data-replication-and-migration.md)
- [Security in Distributed Systems](./concepts/security.md)
- [Observability in Distributed Systems](./concepts/observability.md)
- [Rate Limiting](./concepts/rate-limiting.md)
- [Tradeoffs](./concepts/tradeoffs.md)


## System Design Problems
- [Email Service](./design-problems/emailing-service/main.md)
- [Tinder Design - Recommendation](./design-problems/tinder/main.md)
- [Whatsapp Design - Chat Application](./design-problems/whatsapp-design/main.md)
- [Google Docs - Collaborative Editor Design](./design-problems/google-docs/main.md)
- [Uber - Cab Aggregator App](./design-problems/uber/main.md)
- [Recommendation Engine](./design-problems/recommendation-engine/main.md)



## Resources

A collection of materials referred to while learning:

- System Design Course by Gaurav Sen
- [System Design Primer](https://github.com/donnemartin/system-design-primer)
- [System Desin Interview - Alex Xu (Part 1)](https://shorturl.at/4coTo)
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

### Consistent Hashing

Consistent Hashing maps servers to the key space and assigns requests(mapped to relevant buckets, called load) to the next clockwise server. Servers can then store relevant request data in them while allowing the system flexibility and scalability.

### FFMPEG
FFmpeg is a powerful, free, and open-source multimedia framework used for decoding, encoding, transcoding, and streaming audio and video files.

### AVI

AVI, or Audio Video Interleave, is a multimedia container format developed by Microsoft to store both audio and video data in a single file, allowing for synchronized playback. 

### Homebrew

Homebrew is a package manager for macOS (and Linux) that simplifies the installation, updating, and management of software on your system.

### HLS
- HLS, or HTTP Live Streaming, is a widely used video streaming protocol developed by Apple that delivers audio and video content over the internet. 
- It's known for its adaptability to changing network conditions, reliability, and compatibility with various devices and browsers. 
- HLS works by breaking down video files into smaller segments, which are then downloaded and played sequentially by a video player.

### DASH

- Dynamic Adaptive Streaming over HTTP (DASH) is a streaming technology that adapts video quality in real-time based on network conditions, delivering high-quality video over the internet using standard HTTP servers. 
- It achieves this by breaking down video content into segments, each with varying bitrates, and allowing the client device to dynamically select the most appropriate segment for the current network bandwidth