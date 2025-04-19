# Email Service High-Level Design (HLD)

## Requirements

We access the requirements by the following:

1) **Services**
2) **APIs**
3) **Database Schemas**


## All the Features Required by Email Service

The email service will support the following key features:

1. **Registering a user**
2. **Login (2-factor authentication)**
3. **Profile Creation**
4. **Set Preferences**
5. **Sending/Receiving Emails (with attachments)**
6. **Searching Emails**
7. **Tagging Emails**
8. **Spam and Virus Detection**
9. **Contacts and Groups**


## Email Service Documentation

### 1. [Service Registration and Proxies](./service-registration-and-proxies.md)

Describes how services are **registered** within the system, including the use of proxies for service discovery.

### 2. [Authentication and Global Cache](./auth-global-cache.md)

Details the **authentication mechanisms** (including 2-factor authentication) and how **global cache** is utilized for performance optimization.

### 3. [API Contracts and Versioning](./api-contracts-and-versioning.md)

Defines the **API contracts**, methods, and approaches for **versioning** to ensure backward compatibility.

### 4. [Tagging, Sending, and Searching Emails](./tagging-sending-searching.md)

Explains the **tagging** of emails, methods for **sending** and **receiving** emails (including with attachments), and how **email search** functionality works.

### 5. [Contacts and Groups](./contacts-and-groups.md)

Describes how **contacts** and **groups** are managed within the email service, enabling efficient organization and use of the system.

### 6. [Final Architecture](./final-architecture.md)

Presents the **final architecture** of the email service, integrating all components and their interactions.

---