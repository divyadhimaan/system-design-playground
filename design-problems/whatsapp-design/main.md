# Whatsapp - Chat Application

> Check the final architecture Diagram [here](./final-architecture.png)


## Step 1: Understand problem statement and requirements

> Candidate: What kind of chat app shall we design? 1 on 1 or group based?
> 
> Interviewer: It should support both 1 on 1 and group chat.

> Candidate: Is this a mobile app? Or a web app? Or both? 
> 
> Interviewer: Both. 

> Candidate: What is the scale of this app? A startup app or massive scale? 
> 
> Interviewer: It should support 50 million daily active users (DAU). 

> Candidate: For group chat, what is the group member limit? 
> 
> Interviewer: A maximum of 100 people 

> Candidate: What features are important for the chat app? Can it support attachment? 
> 
> Interviewer: 1 on 1 chat, group chat, online indicator. The system only supports text messages. 

> Candidate: Is there a message size limit? 
> 
> Interviewer: Yes, text length should be less than 100,000 characters long. 

> Candidate: Is end-to-end encryption required? 
> 
> Interviewer: Not required for now but we will discuss that if time allows. 

> Candidate: How long shall we store the chat history? 
> 
> Interviewer: Forever.


### Requirements
- One to One Chat with low delivery latency
- Group Messaging (max of 100 people)
- Sent + Delivered + Read Receipts
- Multiple Device Support
- Online/Last seen
- Image Sharing
- Chats are temporary/permanent
- Push notifications for new messages


## Step 2: High Level Design

- How clients and servers will communicate?
  - Clients do not communicate directly. All communication is done via our chat servers.
  - Functions supported
    - Receive messages from other clients
    - Find recipient and relay the message
    - if recipient is not online, hold the message and deliver it when the recipient comes online.
    - `SENDER --> CHAT SERVICE (store message / relay message) --> RECIPIENT`
- When client starts a chat, it connects to chat service using a network protocol.

---

---

### Network Protocol
- HTTP Protocol
  - Client sends HTTP request to server to send a message.
  - Server responds with HTTP response.
  - Keep alive allows persistent connections and reduces TCP handshake overhead.
  - Problem: HTTP is stateless and unidirectional. Server cannot send messages to clients unless clients poll the server.

#### How to handle receiver side communication?
- **Option 1: Polling**
  - Client sends HTTP request to server to check for new messages.
  - Server responds with new messages or empty response.
  - Problem: Polling introduces latency, is costly and increases server load.
  ![polling](../../images/whatsapp-design/polling.png)
- **Option 2: Long Polling**
  - Client sends HTTP request to server to check for new messages.
  - Server holds the request until new messages arrive or timeout occurs.
  - Server responds with new messages or empty response.
  - Client immediately sends another request after receiving response.
  - Problem: Still has overhead of HTTP and not truly bidirectional.
  ![long-polling](../../images/whatsapp-design/long-polling.png)
- **Option 3: WebSockets (WSS)**
  - Starts as a HTTP handshake and then upgrades to WebSocket protocol.
  - Both client and server can send messages to each other at any time.
  - works even with firewalls and proxies as it uses standard HTTP ports (80 and 443).
  - Low latency and efficient for real-time communication.
  - Industry standard for chat applications.
  ![websockets](../../images/whatsapp-design/websocket.png)

---

### High Level Architecture
- Clients connect to Chat Service using WebSockets.
- Other features like sign up, login, user profile etc use REST APIs (HTTP) via Load Balancer to Web Servers.

- Chat system has 3 main components
  - **Stateless Services**
    - Public facing request/response services
    - Used to manage login, signup, user profile, etc.
    - Load Balancer + Web Servers
    - Can be monolithic or microservices based
    - Can be integrated with 3rd party services like OAuth, SMS gateway, Email service, etc.
    - Example - Service Discovery, API Gateway, Auth Service
  - **Stateful Services**
    - Chat Service is the only stateful service
    - Because each client maintains a persistent WebSocket connection with Chat Service
    - Client normally doesn't switch Chat Service servers during a session
    - Service discovery is needed to find the right Chat Service server
  - **Third Party Integrations**
    - Push Notification Service for offline message alerts
    - Distributed File Storage for media files (images, audio, video)
    - CDN for faster media file delivery



  ![components](../../images/whatsapp-design/components.png)

---

### Architecture Diagram

- Chat servers are distributed behind a Load Balancer.
- Clients connect to Chat Service using WebSockets which facilitate bidirectional communication.
- Presence servers manage online/last seen status.
- API servers handle REST API requests for non-chat features.
- Notification servers send push notifications.
- Key-value store is used to store chat history and user data.


  ![high-level-design](../../images/whatsapp-design/high-level-architecture.png)

---

### Storage Design
> Question: Which database to use? (Relational database / NoSQL database) 

- Two types of data exists in chat system
  - Generic data: user profile, contacts, groups, etc.
    - Structured data with relationships
    - Requires ACID transactions
  > _Relational database_ is suitable
  - Chat data: chat history/messages, media files, etc.
    - Enormous volume of unstructured data
    - Requires high write throughput and low latency reads
    - Only recent chats are accessed frequently
    - Users might use features that need random access of data like search, view mentions, jump to specific message, etc.
  > _Key-value store_ is suitable.

#### Why key-value store for chat data?
- Easy horizontal scaling by sharding data across multiple nodes.
- Very low latency for reads/writes.
- Relational databases dont handle long chat histories well.
- Adopted by facebook messenger (HBase) and discord (Cassandra).


---
### Data Model
- Message Table for 1:1 chats
  - Primary Key: (message_id)
  - messageId decided message sequence 
  ![data-model-personal-chat](../../images/whatsapp-design/data-model-1-1.png)
- GroupMessage Table for group chats
  - Primary Key: (channel_id, message_id)
  - 
  ![data-model-group-chat](../../images/whatsapp-design/data-model-1-many.png)

> Question: How to generate message_id?
> - message_id ensures ordering of messages. It should satisfy the following properties:
>   - IDs must be unique
>   - IDs must be sortable by creation time
> 
> - Option 1: Use **Auto-incrementing IDs** in mysql
> - Option 2: Use **global 64-bit sequence number generator** like Twitter Snowflake
> - Option 3: Use **Local sequence number generator**. Local Ids are only unique per user or per group. 
> 
> Finally, option 3 is used as maintaining message sequence within one-on-one channel or group channel is sufficient. Combine with userID/groupID to get global uniqueness (if needed).

---

## Last Seen Timestamps

- We want to show other users whether any user is online or when was he/she last seen. 
- To implement this we can store a table that contains the userlD and the LastSeenTimestamps. W
- Whenever any user makes an activity (like sending or reading a message) that request is sent to the server. At the time at which the request is sent we update the key-value pair. 
- We must also consider the requests sent by the application and not by the user (like polling for messages etc.) These requests do not count as user activity so we won't be logging them. 
- We can have an additional flag (something like application _activity) to differentiate the two.

- We also need to define a threshold. If the last seen is below the threshold then instead of showing the exact time difference, we will just show online.
E.g. if the last seen of user X is 3sec and the threshold is 5sec then other users will see X as online.

### Components

- Last Seen Service
  - Every time there is a user activity, it is routed to this service.
- Database

![Alt text](./../../diagrams/wa-2.png)


---

## Group messaging

- Each group will have many users. Whenever a participant in a group sends a message we first find the list of users present in the group. 
- Once the session service has the list of users it finds the gateway services that the users are connected to and then sends the message.
> Note: We should also limit the number of users in a group. If there are a lot of users then it can cause a fanout. We can ask the client applications to pull new messages from our system but our messages won't be real-time in such case.
- We do not want the gateway service to parse messages because we want to minimize the memory usage and maximize the TCP connections. So we will use a `message parser` to convert the unparsed to sensible message.
- We have a mapping of groupID to userID. This is one-many relationships. Group messaging service has multiple servers so there can be data redundancy. To reduce redundancy we use `consistent hashing`. We hash the groupID and send the request to the server according to the result.
- We also need to use a `message queue` in case there are any failures while sending requests.
- Once we give a request to the message queue it ensures that message will be sent. If we reach the maximum number of retries it tells the sender that it failed and we can notify the user.


- While sending messages in a group we must take care of three things
  - `Retries` - The message queue takes care of that.
  - `Idempotency` - It means that each message should be sent only once. We can achieve this by sending messages to queue at least once but each message will have a unique ID. If the service has already seen the ID then it means that message is already sent to the new message is ignored.
  - `Ordering of messages` - Messages must be ordered by their timestamps in a group. To ensure this we always assign the messages of a particular group to a particular thread from the thread pool.

### Components

- Group Messaging service
  - It stores the mapping of groupID to userlD and provides this data to the session service.
- Message parser service
  - It receives the unparsed message from the gateway service and converts it to a sensible format before sending it to other services.
- Message queue

![Alt text](./../../diagrams/wa-3.png)

## Sending Images, Audio, Video Files

- We can use a distributed file service to store the files as they are much more efficient and cost-effective compared to storing images as BLOBs in a database. 
- Every time a user sends an image we can store it in the file service and when we can get the image when we need to send it.


### Features provided by database when store images as BLOB

| Feature | Requirement | 
| ------- | ----------- |
| Mutability |  When we store an image as a BLOB in database we can change its value. However, this is useless as because an update on image is not going to be a few bits. We can simply create a new image file. |
| Transaction Guarantee | We are not going to do an atomic operation on the image. So this feature is useless. | 
| Indexes | We are not going to search image by its content (which is just O's and 1's) so, this is useless. | 
| Access control | Storing images as BLOB's in database provides us access control, but we can achieve the same mechanisms using the file system. |


### Why file is better
- File is cheaper
- faster as store large files seperately
- Use CDNs for faster access, since files are static
- Store File url in DB
> We Use Distributed File System



### Tradeoffs

#### Using HTTP for messaging v/s Websockets (WSS)
- HTTP can only send messages from client to server. The only way we can allow messaging is by constantly sending a request to the server to check if there is any new message (Long Polling).
- WSS is a peer-to-peer protocol that allows clients and servers to send messages to each other.

#### TCP/WebSocket v/s P2P connection
- For general chat applications, WebSocket (TCP) via a gateway/server is the preferred and industry-standard choice.
- P2P is better suited for specialized cases like video/audio streaming using WebRTC where lower latency is critical and the connection is temporary.


## Optimizations
- `Graceful degradations`: On some occasions, our system might get so many messages that systems get overloaded. In such cases, we can temporarily shut down services that are not critical to our service (like sending read receipts or last seen status, etc).
- `Rate Limiting`: In some situations, it might happen that we cannot handle any more requests. In such cases, we can rate-limit the number of requests and drop extra requests. However, this results in a bad user experience.

