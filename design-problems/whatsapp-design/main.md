# Whatsapp - Chat Application


## Requirements
- One to One Chat
- Group Messaging
- Sent + Delivered + Read Receipts
- Online/Last seen
- Image Sharing
- Chats are temporary/permanent


## One to One Chat

- Whenever users want to send a message they send a request to our server. This request is received by the gateway service. Then the client applications maintain a TCP Connection with the gateway service to send messages.
- Once the server sends the message to the recipient, our system must also notify the sender that the message has been delivered. So we also send a parallel response to the sender that the message has been delivered. 
- (Note: To ensure that message will be delivered we store the message in the database and keep retrying till the recipient gets the message.) This takes care of `Sent receipts`.
- When the recipient receives the message it sends a response (or acknowledgment) to our system.
- This response is then routed to session service. It finds the sender from the mapping and sends the `Delivery receipts`.
- The process to send the `Read receipts` is also the same. As soon as the user reads the message we perform the above process.

### Components

- Gateway Service
  - Multiple Servers
  - Receives all requests from users
  - Maintain TCP connection with users
- Session Service
  - Gateway service is also distributed. So if we want to send messages from one user to another we must know which user is connected to which gateway server. This is handled by session service.
It maps each user (userlD) to the particular gateway server.
- Database
  - For storing messages



### Tradeoffs

#### Storing the mapping in gateway service v/s Storing it in session service
  - If we store the mapping in the gateway service then we can access it faster. To get the mappings from the session service we have to make a network call.
  - Gateway services have limited memory. If we store the mapping of the gateway we have to reduce the number of TCP connections.
  - Gateway service is distributed. So there are multiple servers. In that case, there will be a lot of duplication. Also every time there is an update we have to update the data on every server.
  - So we can conclude that storing the mapping in the session service is a better idea.
  
#### Using HTTP for messaging v/s Websockets (WSS)
  - HTTP can only send messages from client to server. The only way we can allow messaging is by constantly sending a request to the server to check if there is any new message (Long Polling).
  - WSS is a peer-to-peer protocol that allows clients and servers to send messages to each other.

#### TCP/WebSocket v/s P2P connection
  - For general chat applications, WebSocket (TCP) via a gateway/server is the preferred and industry-standard choice.
  - P2P is better suited for specialized cases like video/audio streaming using WebRTC where lower latency is critical and the connection is temporary.

### Diagram
![Alt text](image.png)