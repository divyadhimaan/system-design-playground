# Idempotency in Distributed Systems

> Idempotency means performing the same operation multiple times results in the same final state as performing it once, even under retries, failures, or duplicates.


## Pain Points

- Distributed systems cannot guarantee exactly-once execution
- At-least-once delivery is common (MQs, Kafka consumers)
- Failures occur between processing and acknowledgment
- Clients often retry blindly on timeout
  
Without idempotency → correctness breaks

## Problems Solved by Idempotency
- Duplicate requests due to:
  - Network retries
  - Client timeouts
  - Message queue redelivery
- Prevents double side effects:
  - Double payments
  - Duplicate orders
  - Repeated emails/notifications