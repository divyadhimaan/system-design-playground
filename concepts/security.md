# Security in Distributed Systems




In distributed systems, security refers to the measures and mechanisms used to protect data, communication, and resources across multiple interconnected nodes in the system.

- [Security in Distributed Systems](#security-in-distributed-systems)
  - [Verification](#verification)
    - [Captcha](#captcha)
    - [SSL/TLS](#ssltls)
    - [Encryption Algorithms](#encryption-algorithms)
  - [Digital rights Management](#digital-rights-management)
  - [Authentication](#authentication)
    - [Token Based Auth](#token-based-auth)
    - [SSO - Single Sign On](#sso---single-sign-on)
    - [OAuth - Open Authentication](#oauth---open-authentication)
  - [Authorization](#authorization)
    - [ACLs - Access Control lists](#acls---access-control-lists)
    - [Rule Engines](#rule-engines)
    - [Secret Keys](#secret-keys)
  - [Attack Vectors](#attack-vectors)
    - [Hackers (DDoS Attacks)](#hackers-ddos-attacks)
    - [Employees](#employees)
    - [Malicious Code](#malicious-code)
  - [Preventing unauthorized access to videos on a CDN](#preventing-unauthorized-access-to-videos-on-a-cdn)

## Verification

### Captcha

- `CAPTCHA (Completely Automated Public Turing test to tell Computers and Humans Apart)` is a type of security measure known as challenge-response authentication. 

- CAPTCHA helps protect you from spam and password decryption by asking you to complete a simple test that proves you are human and not a computer trying to break into a password protected account.

- `Problem`: With AI and machine learning, bots can now solve most CAPTCHA challenges better than humans.
For example, some AI models can solve Google reCAPTCHA with 95%+ accuracy.

### SSL/TLS

- SSL - Secure Sockets Layer
  - It is an encryption-based Internet security protocol.

  - A website that implements SSL/TLS has "HTTPS" in its URL instead of "HTTP."

  - In order to provide a high degree of privacy, SSL encrypts data that is transmitted across the web. This means that anyone who tries to intercept this data will only see a garbled mix of characters that is nearly impossible to decrypt.

  - SSL is outdated and insecure, no longer used in modern secure communications.

  

    <p align="center">
        <img src="../images/ssl.png" alt="SSL">
        <br/>
        <i><a href="https://www.cloudflare.com/en-in/learning/ssl/what-is-ssl/">SSL/TLS</a></i>
    </p>

- TLS - Transport Layer Security
  
  -  designed to provide secure communication over a computer network, such as HTTPS on the web.
  
  -  TLS is the successor to SSL.
  
  - It provides privacy, integrity, and authentication between communicating applications.
  
  - It's used in HTTPS, email, messaging apps, and more.
  
  - The current version is TLS 1.3 (as of 2025), which is faster and more secure.   

  - TLS is the updated and secure version, and when people say "SSL certificates," they almost always mean TLS certificates nowaday
### Encryption Algorithms
  - ECC
  - RSA
  - Diffie-Hellman

## Digital rights Management

Digital Rights Management (DRM) is a set of technologies and strategies used to control how digital content is used, distributed, and accessed, in order to protect intellectual property rights.

Example - Using `Fairplay Screening (FPS)` content providers, encoding vendors, and delivery networks can encrypt content, securely exchange keys, and protect playback on Apple platforms.

## Authentication

Below are the common authentication mechanisms, 
- these authentication mechanisms involve the generation and use of tokens but differ in terms of where the token generation and authentication process takes place (server-side or external service).

### Token Based Auth
- A common method of authentication.
- The user sends username & password to the server.
- If valid, the server generates a token — a signed piece of text.

<details>
<summary>More Details about Token based Auth</summary>

    > How do Tokens work?

    - The token proves who the user is and what they're allowed to do.
    - Sent with each request — no need to resend username/password.
    - The server uses a private key to sign the token and a public key to verify it.
    - Simple but not extremely secure.

---
    > Concepts

    - `Private Key`: Secret, kept only on the server.
    - `Public Key`: Shared; can be used to verify the token.
    - Tokens are signed → ensures they can't be tampered with.
---
    > Limitations

    - Vulnerable to replay attacks and token theft.
    - If someone steals a token, they can impersonate the user.
    - Storing tokens in insecure places (like localStorage) is risky.
---

    > Best practices for tokens
    - Use timestamps or version numbers in tokens to limit validity.
    - Logging out = invalidate the token.
    - Token permissions can be quickly checked by the server.

</details>

---

### SSO - Single Sign On
- The authentication process is delegated to an `external service`, such as Google or Uber.
-  The user's credentials are checked by the external service, which then sends a token to the server. 
-  The server can decrypt the token and verify the user's permissions. 
-  SSO is useful for companies that want to have control over their users within their system, even if external services handle authentication.
-  Ensure tokens have a set expiration period and support renewal to maintain security.


### OAuth - Open Authentication

> OAuth is an authorization framework that allows users to grant limited access to their resources on one service to another service without sharing their passwords.
> 
- Originally designed for authorization, but commonly used for authentication too.

- Integrates external services like Google or GitHub with your server.
- The user is prompted to grant permissions to the server.
- Permissions allow access to specific user information (e.g., name, profile photo).

- OAuth tokens are generated after permissions are granted.
- Widely adopted

- Plan a fallback scenario - scenarios when the external service is unavailable.


## Authorization

Authorization is the process of defining and enforcing access permissions to resources or data within a system.


### ACLs - Access Control lists

- ACLs are lists that define the `actions allowed on objects.`

- Types of ACLs:
  - `User-Based`: Specific users have certain permissions.
  - `Role-Based`: Users in a particular role have certain permissions.
  - `Group-Based`: Groups of users or resources have certain permissions.

- ACLs combine to form the access control matrix, which outlines the complete permission model of the system.

- Ensure ACLs cover all necessary objects and actions to prevent unauthorized access.

- But it difficult to maintain the ACLs.

- Use cases:
  - Ideal for static, straightforward permission management.
  - Example: File system permissions or access to specific features in an application.

---

### Rule Engines

- Rule engines use a set of rules—often implemented as if statements—to decide if a user or resource has permission to perform an action.

- Key Benefits:
  - `Handles Complexity`: Suitable for managing complex authorization requirements.
  - `Efficiency`: Can process multiple objects and rules effectively.
  - `Centralization`: Allows centralization of common rules. A centralized rule engine simplifies maintenance and troubleshooting.
  - `Ease of Modification`: Rules can be updated without needing to modify ACLs directly.

- Use Cases:
  - Suitable when authorization rules are complex, conditional, or require frequent updates.
  - Example: Dynamic access controls in a multi-tenant system or context-aware permission models.

---

### Secret Keys

- Secret keys, also known as client keys, add an extra layer of security for authentication and authorization.

- Authentication is performed using a key or token rather than a traditional username and password.

- Provides additional protection by requiring the secret key for access.
- Not considered sufficient as the sole method of security; usually used alongside other authentication methods.

- Key Management:
  - Ensure secret keys are securely stored and rotated regularly.
  - Use best practices to avoid exposure in code or configuration files.

- Use Case:
  - Best for secure authentication between systems or clients.  
  - Example: Authenticating microservices or API access.

## Attack Vectors
Types of attackers and how to protect resources from them:
### Hackers (DDoS Attacks)
- Threat:
  - Distributed Denial of Service (DDoS) attacks by hackers flood the system with requests.
- Mitigation Techniques:
  - Distributed Rate Limiting: Controls the rate of incoming requests to prevent overload.
  - Web Application Firewalls (WAF): Verify the authenticity of users and block malicious requests.

### Employees

- Threat:
  - Employees, whether intentionally or accidentally, can pose security risks.
- Mitigation Techniques:
  - Access Control Lists (ACLs): Limit actions employees can take.
  - Resource Restriction:
    - Open only necessary entry points.
    - Limit database access to only relevant servers.
  - Minimizing Attack Surface: Reduce available pathways to prevent unauthorized actions.

### Malicious Code
- Threat:
  - Execution of malicious code can compromise the system.
- Mitigation Techniques:
  - Resource Restrictions: Control and limit access to critical resources.
  - Rule Engines: Enforce rules to prevent illegal code modifications.
  - Integrity Checks: Regularly verify system integrity.
  - Code Reviews: Conduct thorough reviews to identify vulnerabilities.

---



## Preventing unauthorized access to videos on a CDN

| **Aspect**        | **Token-Based Authentication**                                                                                                                                           | **Domain-Based Authentication**                                                                                                                                          | **Server-Side Authentication**                                                                                                                                                                    |
|-------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Process**       | - CDN forwards user requests to the main server for authentication. <br>- Main server verifies credentials and issues a token. <br>- CDN serves the video upon verification. | - CDN examines the request’s origin domain. <br>- Video is served if the domain is allowed; otherwise, access is denied.                                                   | - User’s request is authenticated directly by the main server. <br>- Server generates a token signed with a private key. <br>- CDN verifies the token using the public key. <br>- Includes token refresh intervals. |
| **Advantages**    | - Strong, thorough authentication.                                                                                                                                    | - Simple and fast; decision is based solely on the request’s domain.                                                                                                     | - Balances robust security with efficiency through periodic token validation.                                                                                                                   |
| **Disadvantages** | - Slower performance due to extra round-trip to the main server. <br>- Risk that the CDN could potentially access and misuse the token.                                     | - Vulnerable to domain spoofing. <br>- Does not provide user-specific authorization.                                                                                    | - More complex integration. <br>- Requires additional backend mechanisms for token generation and refresh.                                                                                      |
| **Use Cases**     | - Ideal for secure API communications between services where strong authentication is needed. <br>- Suitable when a slight delay is acceptable in exchange for stronger verification. | - Best for controlled environments with trusted domains. <br>- Works well when only basic access control is required.                                                     | - Suitable for systems needing both robust security and efficiency (e.g., premium content delivery platforms). <br>- Effective when timely token renewals help prevent unauthorized sharing.|
