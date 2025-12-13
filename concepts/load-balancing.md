# Load Balancing

> Load balancing is handled by a tool or application called a load balancer. A load balancer can be either hardware-based or software-based.Load balancers distribute incoming client requests to computing resources such as application servers and databases.


## Benefits of Load Balancing
1. **Scalability**: As traffic grows, you can add more servers behind the load balancer without redesigning your entire architecture.
2. **High Availability**: If one server goes offline or crashes, the load balancer automatically reroutes traffic to other healthy servers.
3. **Performance Optimization**: Balancing load prevents certain servers from overworking while others remain underutilized.
4. **Maintainability**: You can perform maintenance on individual servers without taking your entire application down.


## Types of Load Balancers

### Hardware vs. Software

- **Hardware Load Balancer**: Specialized physical devices often used in data centers (e.g., F5, Citrix ADC). They tend to be very powerful but can be expensive and less flexible.
- **Software Load Balancer**: Runs on standard servers or virtual machines (e.g., Nginx, HAProxy, Envoy). These are often open-source or lower-cost solutions, highly configurable, and simpler to integrate with cloud providers.

### Layer 4 vs. Layer 7

- **Layer 4 (Transport Layer)**: Distributes traffic based on network information like IP address and port. It doesn’t inspect the application-layer data (HTTP, HTTPS headers, etc.).
- **Layer 7 (Application Layer)**: Can make distribution decisions based on HTTP headers, cookies, URL path, etc. This is useful for advanced routing and application-aware features.

## Routing Algorithm
- `Round Robin`: Requests are distributed sequentially to each server in a loop.
- `Weighted Round Robin`: Each server is assigned a weight (priority). Servers with higher weights receive proportionally more requests.
- `Least Connections`: The request goes to the server with the fewest active connections.
- `IP Hash`: The load balancer uses a hash of the client’s IP to always route them to the same server (useful for sticky sessions).
- `Random`: Select a server randomly (sometimes used for quick prototypes or specialized cases).

## How load balancers work
### Step 1: Traffic Reception
All incoming requests arrive at the load balancer’s public IP or domain (e.g., www.myapp.com).

### Step 2: Decision Logic (Routing Algorithm)

The load balancer decides which server should get the request. This decision is based on the chosen [routing algorithm](#routing-algorithm) (e.g., Round Robin, Least Connections).

### Step 3: Server Health Checks

Load balancers usually have an internal mechanism to periodically check if servers are alive (e.g., by sending a heartbeat request like an HTTP GET /health).

- If a server doesn’t respond within a certain threshold, it’s marked as unhealthy and no longer receives traffic.
- When it recovers, the load balancer can automatically reintroduce it into the rotation.

### Step 4: Response Handling

Once a request is forwarded to a healthy server, the server processes it and returns the response to the load balancer, which then returns it to the client.