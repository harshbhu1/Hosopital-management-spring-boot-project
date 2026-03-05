
# Hospital Patient Management Microservices System

A production‑style **Java Spring Boot Microservices Architecture** for managing hospital patients.
The system demonstrates modern backend architecture using:

- **Spring Boot**
- **Spring Cloud API Gateway**
- **Apache Kafka (Asynchronous communication)**
- **gRPC (Synchronous communication)**
- **Docker (Containerization)**
- **PostgreSQL**
- **REST APIs**
- **Microservice Internal Networking**

This project simulates how large-scale healthcare platforms manage patient workflows using distributed systems.

---

# Architecture Overview

The system consists of multiple independent microservices that communicate through different protocols depending on the use case.

Client → API Gateway → Microservices

Communication Types:

1. **REST via API Gateway**
2. **gRPC for synchronous service‑to‑service communication**
3. **Kafka for asynchronous event-driven communication**

---

# Microservices in the System

## 1️⃣ API Gateway

Central entry point for all client requests.

Responsibilities:

- Route incoming HTTP requests to microservices
- Apply filters (JWT validation, logging etc.)
- Hide internal microservice ports
- Provide a single access point for the frontend

Example Request Flow:

```
Client Request
      |
      v
http://localhost:4004/patients/register-patient
      |
      v
API Gateway Route
      |
      v
Patient Service
```

Technology:

- Spring Cloud Gateway
- WebFlux (Reactive routing)
- Spring Boot

---

## 2️⃣ Patient Service

Handles all **patient management operations**.

Features:

- Register patient
- Update patient
- Delete patient
- Fetch patient records

Technologies:

- Spring Boot
- Spring Data JPA
- PostgreSQL
- REST API
- gRPC client (for billing communication)

Example Endpoint:

```
POST /register-patient
```

---

## 3️⃣ Billing Service

Handles **billing related operations** for patients.

Responsibilities:

- Generate billing records when a patient is created
- Provide billing information
- Communicate with Patient Service using **gRPC**

Technologies:

- Spring Boot
- gRPC
- PostgreSQL

Communication:

```
Patient Service
      |
      | gRPC call
      v
Billing Service
```

---

## 4️⃣ Analytics Service

The **Analytics Microservice** listens for events from Kafka and stores analytics data.

Responsibilities:

- Consume Kafka events when a **new patient is created**
- Store analytics data for reporting and monitoring
- Track system activity

Example Event:

```
PATIENT_CREATED
```

Workflow:

```
Patient Service
      |
      | Kafka Event (PATIENT_CREATED)
      v
Kafka Broker
      |
      v
Analytics Service (Consumer)
      |
      v
Store Analytics Data
```

Technologies:

- Spring Boot
- Kafka Consumer
- PostgreSQL / Event Storage

---

# Communication Patterns

## Synchronous Communication (gRPC)

Used when a **service needs an immediate response**.

Example:

Patient Service → Billing Service

Advantages:

- High performance
- Strong contract via `.proto` files
- Faster than REST between services

---

## Asynchronous Communication (Kafka)

Used for **event driven workflows**.

Example:

When a patient is created:

```
Patient Service
      |
      | Kafka Event
      v
Kafka Broker
      |
      v
Analytics Service
```

Advantages:

- Decouples services
- Improves scalability
- Enables event driven architecture

---

# Containerization

All microservices are **dockerized**.

Each service runs inside its own container:

```
api-gateway
patient-service
billing-service
analytics-service
postgres-databases
kafka
zookeeper (or kraft mode)
```

Docker ensures:

- Environment consistency
- Easy deployment
- Isolation of services

---

# Running the Project

Currently **each service is started manually via command line**, not with docker-compose.

Typical workflow:

## 1 Start Infrastructure

Start Kafka container

```
docker run ...
```

Start PostgreSQL containers

```
docker run ...
```

---

## 2 Start Microservices

Run each microservice separately.

Example:

### Patient Service

```
./gradlew bootRun
```

or

```
docker run patient-service-image
```

---

### Billing Service

```
./gradlew bootRun
```

---

### Analytics Service

```
./gradlew bootRun
```

---

### API Gateway

```
./gradlew bootRun
```

---

# Example API Request

Register a patient through API Gateway:

```
POST http://localhost:4004/patients/register-patient
```

Request Body:

```json
{
  "name": "John Doe",
  "age": 35,
  "disease": "Fever"
}
```

Flow:

```
Client
   |
   v
API Gateway
   |
   v
Patient Service
   |
   | gRPC
   v
Billing Service

AND

Patient Service
   |
   | Kafka Event
   v
Analytics Service
```

---

# Technologies Used

Backend:

- Java 21
- Spring Boot
- Spring Cloud Gateway
- Spring Data JPA
- WebFlux

Communication:

- Apache Kafka
- gRPC
- REST

Database:

- PostgreSQL

Containerization:

- Docker

---

# Learning Objectives

This project demonstrates:

- Microservice architecture design
- Event-driven systems using Kafka
- High-performance service communication with gRPC
- API Gateway routing
- Docker containerization
- Distributed system communication

---

# Future Improvements

Possible enhancements:

- Service discovery (Eureka / Consul)
- Docker Compose for easier orchestration
- Kubernetes deployment
- Distributed tracing (Zipkin / OpenTelemetry)
- Centralized logging (ELK stack)
- Authentication service (JWT)

---

# Author

Harsh  
Java Backend Developer

This project was built to demonstrate **real-world microservices architecture using Spring Boot**.
