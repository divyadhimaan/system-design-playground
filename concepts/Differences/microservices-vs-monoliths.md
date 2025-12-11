### Microservices vs Monolithic Architecture

| Dimension                  | Monolithic Architecture                                                        | Microservices Architecture                                                         |
|----------------------------|--------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| **Definition**             | Single, unified application deployed as one unit                               | Distributed system of small, independent services                                  |
| **Scalability**            | Scale entire app (vertical); inefficient                                       | Scale specific hot services (horizontal); efficient                                |
| **Deployment**             | One deployment for all modules                                                 | Independent deployment per service                                                 |
| **Performance**            | Faster (no network calls internally)                                           | Slower due to network latency between services                                     |
| **Fault Isolation**        | Low — one failure can crash whole app                                          | High — failures isolated to individual services                                    |
| **Tech Stack**             | Single tech stack for all modules                                              | Polyglot; service-specific tech choices                                            |
| **Database Model**         | Typically single shared database                                               | Each service maintains its own database                                            |
| **Development Speed**      | Fast initially; slows as codebase grows                                        | Requires more setup; faster long-term parallel development                         |
| **Operational Complexity** | Low; easy to run and monitor                                                   | High; requires DevOps, CI/CD, monitoring, service discovery                        |
| **Testing**                | Easy (everything local)                                                        | Hard (distributed testing, mocks, contracts)                                       |
| **Best For**               | MVPs, small teams, simple apps                                                 | Large-scale systems, multiple teams, domain-driven boundaries                      |
| **Pros**                   | Simple architecture, easy testing, fast initial dev, low infra overhead        | Independent scaling, resilient, autonomous teams, faster releases at scale         |
| **Cons**                   | Hard to scale parts, tightly coupled, long deploy cycles, poor fault isolation | High complexity, data consistency issues, network overhead, heavy ops requirements |
