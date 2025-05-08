# Google Docs - Collaborative Editor Design


## Requirements
1. Storing, retrieving, and editing documents

2. Sharing permissions: READ, EDIT, LINK-SHARING

3. Notifications on email

4. Version History

5. Spell Checking*

6. Comments Support

7. Offline and Collaborative Editing


## Reading and Writing Documents

Document Schema

We can essentially store document info as below. But the data column will be very big and is not queries as often as the other metadata. 
![Alt text](./../../images/gd-1.png)

### How would we store the metadata of the document?

### 1. Splitting the metadata
A RDBMS should be used to store document metadata - for faster access.
![Alt text](./../../images/gd-2.png)

A SQL database is used for storing content/data of the document.
![Alt text](./../../images/gd-3.png)


> Problems:
> - There are going to be alot of alter queries, since metadata keeps changing.
>  - JOINs are required.
> 
> Thus this way of storing will be expensive and inefficient.

### 2. Storing as json

This way we can store the data and metadata of the document as a json.
![Alt text](./../../images/gd-4.png)