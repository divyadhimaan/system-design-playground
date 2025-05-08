# Hotstar - Live Streaming

For large users, these are the requirements
- Ingest live HD video.
- Transform video for different users.
- Transport videos to end user.

## Assumption and Requirements

1 minute is the maximum delay between live event and its streaming.
- Transform original video
- heterogenous delivery
- caching
- Fault tolerance

## Available in diff resolutions

- Maintain a 2D matrix for `resolutions`(8K, 4k, 1080p, 720p, ..) and `codecs`(HLS, MP4, DASH).
- Before transporting, the raw videos will be transformed into every supported resolution and codec.
- This will be transformation service
- The service will have a job scheduler. Tasks will be assigned to worker nodes.
- From workers videos can be posted into a DFS. and a completion event can be sent to the message queue. 
- Subscribers can pull these videos from the message queue.

- This needs `compute`

## Transport Videos

- We will be using RMTP (No loss) for video ingestion
- Video replicas must be stored for fault tolerance (DB)

### RMTP
- RTMP, or Real-Time Messaging Protocol, is a streaming protocol initially developed by Macromedia (later acquired by Adobe) to deliver live audio, video, and data over the internet as both an ingest and playback protocol.
-  By establishing a persistent connection between the source (like a camera or encoder) and the streaming server, it ensures smooth and reliable data flow. 
-  It became widely recognized for its low latency, making it ideal for interactive applications and live streaming events


- RTMP is most commonly used for: 

  - Live Broadcasting: Delivering real-time video feeds to audiences during sports events, concerts, and other live events. 
  - Ingesting Streams: Sending video data from an encoder to a streaming server.
  
- Usage today: OBS → RTMP → Media server → viewers get HLS/DASH.
  - OBS streams live video using RTMP to a media server.
  - The media server converts RTMP into HLS or DASH formats.
  - Viewers watch the stream using HLS/DASH, which works in browsers and apps.




### Tradeoffs
| Protocol | Type                 | Latency         | Browser Support      |                                         Feature    | Use Case                                   |
| -------- | -------------------- | --------------- | ------------------------------------------------------------------ | ----- | ------------------------------------------ |
| **RTMP** | Push streaming (old) | Very Low (2-5s) | Needs Flash (now deprecated)   |                                     | Live streaming (OBS → Server), low latency |
| **HLS**  | HTTP-based streaming | Higher (6-30s)  | Native on Safari, works everywhere via JS players (e.g., Video.js) | Adaptive Bitrate| Live + on-demand, works on all devices     |
| **DASH** | HTTP-based streaming | Medium (3-10s)  | Supported in modern browsers, except iOS Safari (partial)   |   Adaptive Bitrate    | Adaptive streaming, more flexible than HLS |


![Alt text](./../../diagrams/hotstar-1.png)

---

## Transferring processed videos

- architecture
  - layer 1 : processed video
  - layer 2: Geographically distributed servers
  - layer 3: CDNs
  - Layer 4: Users


- We want CDNs to take care of the Caching of videos
- We can cache the user requested endpoints. `(userId, liveStreamId) -> endpoint`.


### Tradeoffs

- CMAF vs WebRTC
  
    | Protocol   | Type                    | Latency         | Transport                    | Use Case                                      |
    | ---------- | ----------------------- | --------------- | ---------------------------- | --------------------------------------------- |
    | **CMAF (HLS/DASH)**   | HTTP-based streaming    | Low (2-5s)      | **TCP** (HTTP-based)         | Scalable live streaming (sports, events)      |
    | **WebRTC** | Real-time communication | Ultra-low (<1s) | **UDP** (uses SRTP over UDP) | Video calls, gaming, interactive live streams |
