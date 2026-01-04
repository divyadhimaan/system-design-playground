# Google Drive

Google Drive is a file storage and synchronization service developed by Google. It allows users to store files, photos and videos in the cloud, synchronize files across devices, and share files with others.

- Similar Services
    - Dropbox
    - OneDrive
    - iCloud Drive

## Step 1: Requirements

> Candidate: What are the most important features?
> 
> Interviewer: Upload and download files, file sync, and notifications.

> Candidate: Is this a mobile app, a web app, or both? 
> 
> Interviewer: Both. 

> Candidate: What are the supported file formats? 
> 
> Interviewer: Any file type. 

> Candidate: Do files need to be encrypted? 
> 
> Interview: Yes, files in the storage must be encrypted.

> Candidate: Is there a file size limit? 
> 
> Interview: Yes, files must be 10 GB or smaller. 

> Candidate: How many users does the product have? 
> 
> Interviewer: 10M DAU.

### Design Scope

- Add files
- Download files
- Sync files across multiple devices
- See file revisions.
- Share files with your friends, family, and coworkers
- Send a notification when a file is edited, deleted, or shared with you.

### Non-Functional Requirements

- Reliability: The system should be highly reliable, ensuring that files are not lost or corrupted.
- Scalability: The system should be able to handle a large number of users and files.
- Fast sync speed: Changes made to files should be synced quickly across all devices.
- Bandwidth efficiency: The system should minimize the amount of data transferred during sync operations.
- High availability: The system should be available 24/7 with minimal downtime.

### Estimations
- Assume application has 50 million users.
- There are 10M DAU.
- Users get 10GB free space.
- Assume users upload 2 files per day. Average file size is 500KB.
- 1:1 read to write ratio.

> Total Space Allocated: 50M * 10GB = 500 PB
> QPS for upload: 10M * 2 / 86400 = 232 ~ 240 QPS
> Peak QPS = QPS * 2 = 480 QPS

## Step 2: High-Level Design

- Start with a single server.
- We need 3 main components
  - web server: to upload and download files
  - metadata database: to store file and user metadata
  - file storage: to store the actual files. We allocate 1TB of storage.

- For the start, use Apache web server, MySQL database, and local file system for storage.

### API Design

- We primarily need 3 APIs
  - upload
  - download
  - get revisions

#### API Specifications
- `UploadFile(userID, file, uploadType)`: 
  - Uploads a file for a user.
  - Upload can be of 2 types
    - Simple upload: for files smaller than 5MB
    - Resumable upload: for files larger than 5MB, allows resuming upload if connection is lost.
  - Endpoint:
    - `/files/upload?uploadType={simple|resumable}`
  - Params
    - uploadType: simple or resumable
    - file: local file to be uploaded
  
- `DownloadFile(userID, path)`: 
  - Downloads a file for a user.
  - Endpoint:
    - `/files/download?path={path}`
  - Params:
    - path: path of the file to be downloaded
    
- `getRevisions(userID, path, limit)`:
  - Gets the revisions for a file.
  - Endpoint:
    - `/files/revisions`
  - Params:
    - path: path of the file
    - limit: number of revisions to return

### Scaling from single server to multiple servers

- Our single server can handle 1TB of storage and 480 QPS.
- To handle more traffic and storage, we need to scale our system.
- We can add more web servers behind a load balancer to handle more QPS.
- For metadata database, we can use a distributed database like Cassandra or DynamoDB.

- Storage can be scaled by sharding based on userID.
- But still potential data losses in case of storage server failure.
- So we can use distributed file system like HDFS or cloud storage like AWS S3, GCP Cloud Storage.

> Optimizations
> 
> 1. Load Balancer: Add a load balancer to distribute incoming requests across multiple web servers.
> 2. Web servers: Add multiple web servers to handle increased traffic.
> 3. Metadata Database: Move database out of server to avoid SPOF. Use a distributed database like Cassandra or DynamoDB to handle increased metadata storage and query load.
> 4. File Storage: Amazon S3 is used for file storage to provide scalability, durability, and availability. To ensure durability, files are replicated across multiple availability zones.

![img.png](../../images/googleDrive/high-level-design.png)

### Sync conflicts
- When a file is edited on multiple devices simultaneously, sync conflicts can occur.
- To handle sync conflicts, we follow following strategy
  - the first version that gets processed wins
  - the other versions are saved as separate files with a timestamp appended to the filename.