# Tinder

## Requirement

- Storing Profiles
- Recommend Profiles
- Note Matches
- Direct Messaging


## Tinder Feature breakdown

### Image Storing

How images are stored in DB. If we assume there are 5 images uploaded per user. Its a large number.

#### File vs Blob (Binary Large Object)

 > Features provided by database when store images as BLOB

| Feature | Requirement | 
| ------- | ----------- |
| Mutability |  When we store an image as a BLOB in database we can change its value. However, this is useless as because an update on image is not going to be a few bits. We can simply create a new image file. |
| Transaction Guarantee | We are not going to do an atomic operation on the image. So this feature is useless. | 
| Indexes | We are not going to search image by its content (which is just O's and 1's) so, this is useless. | 
| Access control | Storing images as BLOB's in database provides us access control, but we can achieve the same mechanisms using the file system. |


#### Why file is better
- File is cheaper
- faster as store large files seperately
- Use CDNs for faster access, since files are static
- Store File url in DB
> We Use Distributed File System


### Profile Creation, authentication

- First the system should allow a new user to create an account and once the account has been created then it needs to provide the user with an authentication token. 
- This token will be used by the APl gateway to validate the user.
- System needs to store profile name, age, location and description in a relational database. However, there are 2 ways to store images.
  - We can store images as file in File systems
  - We can store images as BLOB in databases.

#### Components Required

- API Gateway Service
- DFS Image Storing
- Relational Database for User details

![Alt text](./../../diagrams/tinder-1.png)

