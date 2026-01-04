# Google Drive

Google Drive is a file storage and synchronization service developed by Google. It allows users to store files, photos and videos in the cloud, synchronize files across devices, and share files with others.

- Similar Services
    - Dropbox
    - OneDrive
    - iCloud Drive

## Step 1: Requirements

> Candidate: What are the most important features?
> Interviewer: Upload and download files, file sync, and notifications.

> Candidate: Is this a mobile app, a web app, or both? 
> Interviewer: Both. 

> Candidate: What are the supported file formats? 
> Interviewer: Any file type. 

> Candidate: Do files need to be encrypted? 
> Interview: Yes, files in the storage must be encrypted.

> Candidate: Is there a file size limit? 
> Interview: Yes, files must be 10 GB or smaller. 

> Candidate: How many users does the product have? 
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

### Es
