# Content Delivery Network (CDN)

- [Problem CDN Solves](#problem-cdn-solves)
- [What is a CDN?](#what-is-a-cdn)
- [Core Components](#core-components)
- [How a CDN Works](#how-a-cdn-works)
- [Benefits](#benefits)
- [Challenges](#challenges)
- [Common Use Cases](#common-use-cases)
- [Popular CDN Providers](#popular-cdn-providers)
- [Choosing a CDN](#choosing-a-cdn)
- [Summary](#summary)
---

## Problem CDN Solves
- Single origin server causes **high latency** for distant users
- Leads to **slow load times**, buffering, and poor user experience
- Network distance directly impacts performance

---

## What is a CDN?
- **CDN (Content Delivery Network)** = geographically distributed network of servers
- Serves content from the **nearest server** to the user
- Reduces latency and improves performance & availability

---

## Core Components
- **Edge Servers (PoPs)**  
  Cache and deliver content close to users

- **Origin Server**  
  Source of original content

- **DNS**  
  Routes user requests to the nearest edge server

---

## How a CDN Works
1. User requests content
2. DNS resolves request to nearest CDN edge server
3. **Cache Hit** → Content served from edge
4. **Cache Miss** → Fetch from origin, store in cache
5. Subsequent requests served from edge

- Uses **TTL (Time To Live)** to refresh cached content

---

## Benefits
- Faster load times (low latency)
- Reduced load on origin server
- High availability & fault tolerance
- Better handling of traffic spikes
- Global content delivery
- Security features (DDoS, WAF, bot protection)

---

## Challenges
- Added system complexity
- CDN cost increases with bandwidth & traffic
- Cache invalidation must be managed carefully

---

## Common Use Cases
- Websites with global users
- Video streaming & OTT platforms
- Online gaming (low latency)
- Media & news platforms
- Software & OS updates

---

## Popular CDN Providers
- **Akamai** – Enterprise-scale, large global network
- **Cloudflare** – Free tier, security-focused
- **Fastly** – Real-time caching, edge compute
- **AWS CloudFront** – AWS-native integration
- **Google Cloud CDN**
- **Azure CDN**

---

## Choosing a CDN
- Small to medium apps → Cloudflare / Fastly
- Cloud-native apps → CloudFront / GCP / Azure CDN
- Enterprise scale → Akamai

---

## Summary
CDNs improve **performance, scalability, reliability, and security**  
by serving content closer to users and reducing origin load.


---

[Back to Concepts](../Readme.md#concepts)