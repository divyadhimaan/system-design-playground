# Kafka

> A distributed, durable, and scalable event-streaming platform used to publish, store, and consume streams of records in real time.

## Pain Points Addressed by Kafka
- Synchronous APIs cause high latency and cascading failures.
- Direct service-to-service communication leads to tight coupling
- Traditional message queues struggle with scale, durability, replay
- Systems need event replay for debugging, analytics, and recovery

## Problems Kafka Solves

- Reliable **asynchronous** communication between services
- Handling high-throughput data streams **without tight coupling**
- Decoupling **producers** and **consumers** in large systems

## Use cases

- Event-driven microservices
- Activity logs, audit logs
- Data ingestion 

## High Level Architecture

![img.png](../../images/kafka/architecture.png)