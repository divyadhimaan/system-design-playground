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
  

  ![Alt text](./../../diagrams/hotstar-1.png)