# Networks

- [Networks](#networks)
  - [Introduction](#introduction)
  - [Where Network is used?](#where-network-is-used)
  - [OSI Model](#osi-model)
    - [Physical Layer](#physical-layer)
    - [Data Link Layer](#data-link-layer)
    - [Network Layer](#network-layer)
    - [Transport Layer](#transport-layer)
    - [Session/Behavioral layer](#sessionbehavioral-layer)
    - [Presentation Layer](#presentation-layer)
    - [Application Layer](#application-layer)
    - [Layer Breakdown](#layer-breakdown)
  - [IP Address](#ip-address)
  - [MAC address](#mac-address)
  - [ISP - Internet Service Provider](#isp---internet-service-provider)
  - [DNS - Domain Name System](#dns---domain-name-system)
    - [What is DNS?](#what-is-dns)
    - [Working of DNS](#working-of-dns)
  - [CDN - Content Delivery Network](#cdn---content-delivery-network)
    - [How CDN works?](#how-cdn-works)
    - [Functions of CDN](#functions-of-cdn)
    - [Benefis of CDN](#benefis-of-cdn)
  - [HTTP](#http)
  - [Internet Protocols](#internet-protocols)
    - [TCP/IP](#tcpip)
    - [UDP](#udp)
    - [TCP vs UDP](#tcp-vs-udp)
  - [How do Servers communicate internally?](#how-do-servers-communicate-internally)
  - [How do we query the microservcies?](#how-do-we-query-the-microservcies)
    - [REST](#rest)
    - [GraphQL](#graphql)
    - [REST vs GraphQL](#rest-vs-graphql)
  - [How do microservices communicate internally?](#how-do-microservices-communicate-internally)
  - [Video Transmission](#video-transmission)
    - [Why HTTP is not helpful?](#why-http-is-not-helpful)
    - [HTTP - Dash](#http---dash)
    - [Web RTC](#web-rtc)
      - [How does peer-to-peer communication work?](#how-does-peer-to-peer-communication-work)
  - [Requirements for Network Protocol](#requirements-for-network-protocol)
  - [Glossary](#glossary)
    - [DDoS - distributed denial-of-service](#ddos---distributed-denial-of-service)
    - [NIC - Network Interface Card](#nic---network-interface-card)
    - [NAT - Network Address Translation](#nat---network-address-translation)
    - [Websocket](#websocket)
    - [XMPP](#xmpp)
    - [Head of Line Blocking](#head-of-line-blocking)
      - [How HTTP2.0 Solves this problem?](#how-http20-solves-this-problem)
      - [Further Enhancement](#further-enhancement)
    - [HLS - HTTP Live Streaming](#hls---http-live-streaming)


## Introduction 

Network refers to how components of a system communicate with each other—whether they are on the same machine, on different servers, or even across the globe.

It involves:
- Data transmission over the internet or intranet
- Protocols (e.g., HTTP, TCP, gRPC)
- Latency, throughput, bandwidth
- Load balancing, failover, timeouts, retries
- Security (encryption, firewalls, authentication)



## Where Network is used?

| Layer               | Example                                   |
|---------------------|-------------------------------------------|
| Client ↔ Backend    | HTTP API calls from browser/mobile app    |
| Service ↔ Service   | Microservice-to-microservice communication |
| App ↔ DB/Cache      | TCP connections to PostgreSQL/Redis       |
| App ↔ Message Queue | Kafka, RabbitMQ messaging                 |
| CDN ↔ Client        | Static file delivery via Cloudflare, etc. |

## OSI Model

<details>
<summary>Model Image</summary>
    <p align="center">
    <img src="../images/osi-model.png" alt="osi-model">
    <br/>
    <i>OSI Model</i>
</details>

### Physical Layer
- The physical layer consists of the basic networking hardware transmission technologies of a network. 
- It is a fundamental layer underlying the logical data structures of the higher level functions in a network.


### Data Link Layer

- This layer is the protocol layer that transfers data between adjacent network nodes in a wide area network (WAN) or between nodes on the same local area network (LAN) segment.
- Examples of data link protocols are Ethernet for local area networks (multi-node), the Point-to-Point Protocol (PPP), HDLC and ADCCP for point-to-point (dual-node) connections. 
- In the Internet Protocol Suite (TCP/IP), the data link layer functionality is contained within the link layer, the lowest layer of the descriptive model.
- MAC addresses
  
### Network Layer

- The network layer is responsible for packet forwarding including routing through intermediate routers, since it knows the address of neighboring network nodes, and it also manages quality of service (QoS), and recognizes and forwards local host domain messages to the Transport layer (layer 4)
- IP Addresses


### Transport Layer

- The protocols of the layer provide host-to-host communication services for applications.
- It provides services such as connection-oriented data stream support, reliability, flow control, and multiplexing.
- The best-known transport protocol of TCP/IP is the Transmission Control Protocol (TCP), and lent its name to the title of the entire suite. It is used for connection-oriented transmissions, whereas the connectionless User Datagram Protocol (UDP) is used for simpler messaging transmissions. 

### Session/Behavioral layer

- The session layer provides the mechanism for opening, closing and managing a session between end-user application processes, i.e., a semi-permanent dialogue.
- Communication sessions consist of requests and responses that occur between applications. 

### Presentation Layer

- The presentation layer is responsible for the delivery and formatting of information to the application layer for further processing or display. 
- It relieves the application layer of concern regarding syntactical differences in data representation within the end-user systems.

### Application Layer

- An application layer is an abstraction layer that specifies the shared protocols and interface methods used by hosts in a communications network. 
- The application layer abstraction is used in both of the standard models of computer networking; the Internet Protocol Suite (TCP/IP) and the Open Systems Interconnection model (OSI model).


### Layer Breakdown

| Layer                | Description |
|----------------------|-------------|
| **Physical Layer**   | Represents the **hardware and physical infrastructure**—servers, routers, switches, data centers, cables, etc.<br>Analogous to Layer 1 (Physical Layer) in the OSI model. |
| **Routing Layer**    | Deals with **communication, routing, and network path selection**—how data moves between components and services.<br>Roughly maps to Layer 2-4 (Data Link, Network, Transport Layer) of OSI. |
| **Behavioural Layer**| Represents the **application logic, system behavior, user interaction**, and business flows.<br>This is the highest level of abstraction and sits above traditional networking models. Essentialy Session Layer of OSI Model|

---

## IP Address

- An IP address is a unique address that identifies a device on the internet or a local network. 
  
- IP stands for "Internet Protocol," which is the set of rules governing the format of data sent via the internet or local network.
  
- In essence, IP addresses are the identifier that allows information to be sent between devices on a network: they contain location information and make devices accessible for communication.
  
- IP addresses are expressed as a set of four numbers — an example address might be 192.158.1.38. Each number in the set can range from 0 to 255. So, the full IP addressing range goes from 0.0.0.0 to 255.255.255.255.

## MAC address

- A MAC address, which stands for Media Access Control Address, is a physical address that works at the Data Link Layer.

- MAC Addresses are unique 48-bit hardware numbers of a computer that are embedded into a network card (known as a Network Interface Card) during manufacturing.

- The data link layer is divided into two sublayers:
    - Logical Link Control (LLC) Sublayer
    - Media Access Control (MAC) Sublayer

## ISP - Internet Service Provider

- An Internet Service Provider (ISP) is an organization that provides internet access to individuals, businesses and other organizations. 

- They connect us to the internet, either through wired connections (like fiber or cable) or wireless methods (like Wi-Fi or mobile data).

## DNS - Domain Name System

### What is DNS?

> DNS, or the Domain Name System, translates human readable domain names (for example, `www.amazon.com`) to machine readable IP addresses (for example, `192.0.2.44`).

<details>
<summary>Basics of DNS</summary>

- All computers on the Internet, from your smart phone or laptop to the servers that serve content for massive retail websites, find and communicate with one another by using numbers. 
- These numbers are known as `IP addresses`. When you open a web browser and go to a website, you don't have to remember and enter a long number. Instead, you can enter a domain name like `example.com` and still end up in the right place.
- The Internet’s DNS system works much like a `phone book `by managing the mapping between names and numbers. 
</details>



### Working of DNS
<details>
<summary>Detailed Working of DNS</summary>

1. A user enters a domain name (e.g., `www.example.com`) in their browser.
2. The browser checks its `DNS cache` to see if it already knows the IP address for that domain. If found, it uses that and skips the next steps.
3. If not cached, the request goes to a recursive D`NS resolver` (usually provided by the user’s ISP or a public resolver like Google DNS 8.8.8.8).
4. The resolver contacts a root DNS server, which doesn’t know the exact IP but points to the `TLD (Top Level Domain)` DNS server (like .com or .org).
5. The `resolver` asks the` TLD server` (e.g., for .com) for the DNS server responsible for `example.com`.
6. The resolver then queries the `authoritative DNS server `for example.com, which contains the actual` DNS records` (like A, AAAA, CNAME, etc.).
7. The authoritative server returns the IP address of the web server hosting your app (usually via an `A record` for IPv4 or `AAAA record` for IPv6)
8. Now that the browser has the IP address, it sends an `HTTP/HTTPS request` to your web application server.
9. Your web server (like one hosted on AWS, Heroku, or your own VPS) processes the request and sends back the website or web app content.
    

    <p align="center">
        <img src="../diagrams/dns-working.png" alt="CDN">
        <br/>
        <i>DNS working</i>
    </p>

</details>


## CDN - Content Delivery Network

A content delivery network (CDN) is a group of geographically distributed servers that speed up the delivery of web content by bringing it closer to where users are. (cache for static content).

- CDNs rely on a process called “caching” that temporarily stores copies of files in data centers across the globe, allowing you to access internet content from a server near you. 

- Because getting content everytime for multiple users from the destination requires high bandwidth.


### How CDN works? 
A content delivery network relies on three types of servers.

- **Origin servers**:
  -  Origin servers contain the original versions of content and they function as the source of truth. 
  -  Whenever content needs to be updated, changes are made on the origin server. 
  -  An origin server may be `owned and managed by a content provider` or it may be hosted on the infrastructure of a third-party cloud provider like Amazon’s AWS S3 or Google Cloud Storage.
  
- **Edge servers**: 
  - Edge servers are located in multiple geographical locations around the world, also called `points of presence` (PoPs).
  -  The edge servers within these PoPs cache content that is copied from origin servers, and they are responsible for delivering that content to nearby users. 
  -  When a user requests access to content on an origin server, they are redirected to a cached copy of the content on an edge server that’s geographically close to them. When cached content is out of date, the edge server requests updated content from the origin server. 
  -  CDN edge servers are `owned or managed by the CDN hosting provider`.
  
- **DNS servers**: 
  - Domain Name System (DNS) servers keep track of and supply IP addresses for origin and edge servers. 
  - When a client sends a request to an origin server, DNS servers respond with the name of a paired edge server from which the content can be served faster.


<p align="center">
    <img src="../images/cdn-working.png" alt="CDN">
    <br/>
    <i><a href="https://www.akamai.com/glossary/what-is-a-cdn">CDN working</a></i>
</p>

### Functions of CDN

- Reduce latency
  - Larger and more widely distributed CDNs are able to deliver website content more quickly and reliably by putting the content as close to the end user as possible.
- Balance loads.
  - Load balancing enables content providers to handle increases in demand and large traffic spikes while still providing high-quality user experiences and avoiding downtime.

### Benefis of CDN

- `Boost performance`: With CDNs, content providers can deliver fast, quality web experiences to all their end users; no matter what location, browser, device, or network they’re connecting from.
- `Ensure availability`: Availability means that content remains accessible to end users even during periods of excessive user traffic when many people are accessing content at the same time or if there are server outages in some parts of the internet. 
- `Enhance security`: CDNs can also improve website security with increased protection against malicious actors and threats like [distributed denial-of-service (DDoS)]() attacks





## HTTP

- HTTP is a method for encoding and transporting data between a client and a server.
- It is the foundation of any data exchange on the Web and it is a client-server protocol, which means requests are initiated by the recipient, usually the Web browser.
- Clients and servers communicate by exchanging individual messages (as opposed to a stream of data). 
- The messages sent by the client are called requests and the messages sent by the server as an answer are called responses.


  
<p align="center">
    <img src="../images/http.png" alt="HTTP">
    <br/>
    <i><a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Overview">Web Overview</a></i>
</p>


## Internet Protocols
### TCP/IP
- The `Transmission Control Protocol (TCP)` is a transport protocol that is used on top of IP to ensure reliable transmission of packets.
- TCP includes mechanisms to solve many of the problems that arise from packet-based messaging, such as lost packets, out of order packets, duplicate packets, and corrupted packets.
- Since TCP is the protocol used most commonly on top of IP, the Internet protocol stack is sometimes referred to as TCP/IP.
- Used for 



    <p align="center">
        <img src="../images/tcp-ip.png" alt="TCP/IP">
        <br/>
        <i><a href="https://www.khanacademy.org/computing/computers-and-internet/xcae6f4a7ff015e7d:the-internet/xcae6f4a7ff015e7d:transporting-packets/a/transmission-control-protocol--tcp">TCP Model</a></i>
    </p>


### UDP 

- The `User Datagram Protocol (UDP)` is a lightweight data transport protocol that works on top of IP.
  
- UDP provides a mechanism to detect corrupt data in packets, but it does not attempt to solve other problems that arise with packets, such as lost or out of order packets. That's why UDP is sometimes known as the Unreliable Data Protocol.
  
- UDP is simple but fast, at least in comparison to other protocols that work over IP. It's often used for `time-sensitive applications` (such as real-time video streaming) where speed is more important than accuracy.

### TCP vs UDP

| Feature                     | TCP                                                                 | UDP                                                             |
|-----------------------------|----------------------------------------------------------------------|------------------------------------------------------------------|
| Connection Type          | Connection-oriented (establishes a connection before data transfer) | Connectionless (no connection setup)                            |
| Reliability              | Reliable – ensures data is delivered correctly and in order         | Unreliable – no guarantee of delivery, order, or error checking |
| Data Transmission        | Data is sent as a stream of bytes (continuous flow)                 | Data is sent as discrete packets (datagrams)                    |
| Error Checking & Recovery| Error detection, acknowledgment, and retransmission                 | Basic error checking (checksum), no retransmission              |
| Speed                    | Slower due to overhead                                              | Faster – minimal overhead                                       |
| Use Cases                | Web browsing (HTTP/HTTPS), email (SMTP), file transfer (FTP)       | Streaming (video/audio), online gaming, VoIP, DNS               |

<p align="center">
        <img src="../images/tcp-vs-udp.png" alt="TCP vs UDP">
        <br/>
        <i>TCP vs UDP</i>
    </p>


## How do Servers communicate internally?

- Different services can be written in different languages, but we need one common language to define the object for communication. (intermediate language)

## How do we query the microservcies?

### REST

- A REST API is an `application programming interface (API)` that follows the design principles of the REST architectural style.
- REST is short for `representational state transfer`, and is a set of rules and guidelines about how you should build a web API.
- REST is a set of architectural constraints, not a protocol or a standard. 

### GraphQL

- GraphQL is a query language and `server-side` runtime for `application programming interfaces (APIs)` that prioritizes giving clients exactly the data they request and no more. 
  
- GraphQL is designed to make APIs fast, flexible, and developer-friendly. 
  
-  As an alternative to REST, GraphQL lets developers construct requests that pull data from multiple data sources in a single API call. 
-  

### REST vs GraphQL

| Feature/Aspect                 | REST                                                                  | GraphQL                                                               |
|-------------------------------|-----------------------------------------------------------------------|------------------------------------------------------------------------|
| **Architecture Style**        | Resource-based (each resource has its own endpoint)                   | Query-based (single endpoint with flexible queries)                    |
| **Data Fetching**             | May over-fetch or under-fetch data                                    | Client fetches exactly what it needs                                   |
| **Endpoints**                 | Multiple endpoints for different resources                            | Single endpoint for all data                                           |
| **Request Format**            | Fixed structure (usually JSON)                                        | Custom structure defined by the client                                 |
| **Flexibility**               | Less flexible – server defines response structure                     | Highly flexible – client defines response structure                    |
| **Versioning**                | Needs versioning for API updates (e.g., `/v1/`, `/v2/`)               | No versioning needed – evolves via schema changes                      |
| **Use Case Fit**              | Simple, CRUD-based microservices                                      | Complex data relationships or frontend-driven apps                     |
| **Caching**                   | Easy with HTTP caching (GET requests)                                 | More complex; often needs custom caching logic                         |
| **Error Handling**            | Standard HTTP status codes                                            | Errors returned in response body with partial data possible            |


## How do microservices communicate internally?
gRPC - google Remote Procedure Call


## Video Transmission

- Video files are large and cannot be sent in a single response. They have to be broken into packets.

### Why HTTP is not helpful?


- Videos are broken into chunks. Since HTTP is stateless, the client has to specify which chunk it wants because the server does not know about the previous requests.

- НТТР is written over TCP which is not optimal for live streaming. Because in live streaming, if the video packet does not reach the client then there is no point in retrying because the data is old.
  
- So UDP is better for live-streaming. However, in some cases where we need, guaranteed delivery TCP is preferred.


### HTTP - Dash
- DASH stands for` Dynamic Adaptive Streaming over HTTP`
- This protocol runs over TCP. So there is guaranteed delivery and ordering.


> The basic idea is, **that the client sends a signal to the main server based on the data you can handle.**

For example:
- if the client can handle a video up to 720p resolution then the main server will send the client a video up to 720p resolution. 
- If the client's network is very slow then the main server will send videos in lower resolution to maintain connectivity.

`In Mac devices, HLS is used`. It is very similar to HTTP-DASH.


### Web RTC

For video conferencing, sending video streams to the other client through a server is a bad idea because:
- It will increase the network congestion on the server-side.
- Video streaming will be slow.
- The server acts as a single point of failure.

So to solve these issues we use `WebRTC`.
It is a `peer-to-peer protocol` so no server is required to send data.

#### How does peer-to-peer communication work?
- First, the clients get the addresses of the other clients from the server.
- Then the server sends the information to both the clients.
- Clients use this information to connect.

Since it does not require a server
- It is fast.
- It saves bandwidth.
- It is more robust because even if the server crashes, clients can keep talking to each other.


## Requirements for Network Protocol
To send a message from A to B:
- Choose a language
- Find out location (IP address) of B
- Find an efficient way to connect.

For these 3 requirements, we have to choose the above protocols on their merits.

## Glossary

### DDoS - distributed denial-of-service 

- In a distributed denial-of-service (DDoS) attack, a type of cyberattack, an attacker overwhelms a website, server, or network resource with malicious traffic. As a result, the target crashes or is unable to operate, denying service to legitimate users and preventing legitimate traffic from arriving at its destination.

- The purpose of DDoS attacks is to severely slow down or stop legitimate traffic from reaching its intended destination. For example, this could mean stopping a user from accessing a website, buying a product or service, watching a video, or interacting on social media.



### NIC - Network Interface Card

- Hardware Device which allows the device to connect to the network.

- It enables data transmission and reception between the computer and other network devices.

### NAT - Network Address Translation

- It is a process in which one or more `local IP addresses` are `translated` into one or more `Global IP addresses` and vice versa to provide Internet access to the local hosts.

- Used in routers. Router can have a public address, while all the devices in the same local network can have private IP addresses assigned by router.

- NAT translated IP addresses to and fro requests (private -> public or vice versa) for internet access.

- NAT saves from IP address exhaustion, as limited amount of IPv4 public IP address (2^32) are available

- Future ->  IPv6 (128 bit addresses instead of IPv4 - 32bit addresses)

### Websocket

- The WebSocket API makes it possible to open a two-way interactive communication session between the user's browser and a server. 
- With this API, you can send messages to a server and receive responses without having to poll the server for a reply.

### XMPP

- `Extensible Messaging and Presence Protocol` (XMPP) is an open XML technology for real-time communication, which powers a wide range of applications including instant messaging, presence and collaboration.

### Head of Line Blocking

Head-Of-Line blocking occurs when the message/data packet at the head of the queue cannot move forward due to congestion even if other messages/packets behind this could.

- Example:
  - First A sends a message to B.
  - B fails to process the message (or maybe it fails to send an acknowledgment).
  - Since A did not get back any acknowledgment, A keeps retrying until B sends an acknowledgment.
  - Other messages in A's queue are going to be waiting (because of ordered delivery)

#### How HTTP2.0 Solves this problem?
- HTTP 2.0 solves this problem using Multiplexing. 
- It implements multiplexing by breaking the messages/data packets into frames and are sent in streams. 
- Each stream has a stream ID. So, when you get a message of a stream ID, that stream is going to be blocked until all the messages having the stream IDs are processed.

#### Further Enhancement

- HTTP 2.0 solved Head of Line Blocking, but it is still written over TCP. Even though data packets are broken into logical streams, they are not independent because they are running on the same TCP queue. If there was a drop-in packet in one of the streams, other packets in this stream will still be blocked.
  
- To solve this problem, `HTTP 3.0(QUIC)` uses UDP instead of TCP. Itused UPD because there are no
acknowledgments. It provides streams, ordered delivery and all other features of НТТР 2.0. НТТР 3.0 will be called QUIC.


### HLS - HTTP Live Streaming

It is a media streaming protocol developed by Apple for delivering video and audio content over the internet