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
- [Tinder Design](./design-problems/tinder/main.md)
- [Whatsapp Design](./design-problems/whatsapp-design/main.md)



## Resources

A collection of materials referred to while learning:

- System Design Course by Gaurav Sen
- [System Design Primer](https://github.com/donnemartin/system-design-primer)
- [System Desin Interview - Alex Xu (Part 1)](https://shorturl.at/4coTo)
<!-- - [Grokking the System Design Interview](https://www.designgurus.io/course/system-design) -->


## Articles
- [Optimizing Netflix APIs](https://netflixtechblog.com/optimizing-the-netflix-api-5c9ac715cf19)
- [Service Discovery](https://www.f5.com/company/blog/nginx/service-discovery-in-a-microservices-architecture)

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