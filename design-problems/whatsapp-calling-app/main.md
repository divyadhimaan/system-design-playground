# WhatsApp Calling

## Features
The features are listed below:
1. Users can call each other over VOIP or PSTN
2. Call Routing
3. Charge user for making calls
4. Choose a provider for each call


## Components Required

- `Switch` Whenever there is a call we get a receiver address and sender address. Switch bridges the
connection between these two addresses.
- `Message queue`
We need two message queues.
  - First, whenever there is a change in call state (like user has picked up call or a call is requested)
we push it to call state change queue. Call manager subscribes to this message queue and then
processes the events one by one.
  - Secondly, whenever a connection is created or terminated we push it to Connection Event Queue.
- `Call state manager`
This component is the brain of our system. It receives the call event from the switch .It receives the connection ID of both users from the session service and tells the switch which two connections it has to bridge. Apart from that it also checks what is the current state of call. Like, wether the call is terminated or is ongoing. This data is then provided to billing service.
- `Session service` It pulls call events from the connection event queue. It stores the mapping of user to call session. And each session is mapped to a connection.

### VoIP vs PSTN


| Feature                              | VoIP (Voice over IP)                        | PSTN (Public Switched Telephone Network)       |
|--------------------------------------|---------------------------------------------|------------------------------------------------|
| **Connection Requirement**           | Only internet connection is required        | Telephone lines are required                   |
| **Bandwidth Usage**                  | Requires variable bandwidth                 | Reserves fixed bandwidth                       |
| **Cost Dependency**                  | Not dependent on distance and time          | Dependent on distance and time                 |
| **Service Cost**                     | Generally free to use                       | Paid service                                   |


![Alt text](./../../diagrams/wa-calling-1.png)