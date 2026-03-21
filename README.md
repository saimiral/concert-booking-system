# Concert Booking System

A backend system for managing concert ticket reservations, built with Spring Boot. Designed with high-load performance in mind, featuring concurrency control and load testing.

> Undergraduate Thesis — University of Western Attica  
> Department of Computer Science and Computer Engineering

---

## Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA / Hibernate**
- **Spring Security + BCrypt**
- **H2 In-Memory Database**
- **Lombok**
- **React** (Demo UI)
- **Apache JMeter** (Load Testing)

---

## Architecture
```
src/main/java/com/saimiral/concert_booking_system
│
├── entity/          # JPA Entities (User, Concert, Reservation)
├── repository/      # Spring Data JPA Repositories
├── service/         # Business Logic
├── controller/      # REST Controllers
├── dto/             # Request/Response DTOs
├── mapper/          # Entity ↔ DTO Mappers
└── exception/       # Global Exception Handling
```

---

## Getting Started

### Prerequisites
- Java 17+
- Maven

### Run the application
```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`  
H2 Console: `http://localhost:8080/h2-console`

### Run the React UI
```bash
cd concert-booking-ui
npm install
npm start
```

---

## API Endpoints

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users` | Create a user |
| GET | `/users` | Get all users (paginated) |
| GET | `/users/{id}` | Get user by ID |
| PATCH | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Delete user |

### Concerts
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/concerts` | Create a concert |
| GET | `/concerts` | Get all concerts (paginated) |
| GET | `/concerts/{id}` | Get concert by ID |
| PATCH | `/concerts/{id}` | Update concert |
| DELETE | `/concerts/{id}` | Delete concert |

### Reservations
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/reservations` | Create a reservation |
| GET | `/reservations` | Get all reservations (paginated) |
| GET | `/reservations/{id}` | Get reservation by ID |
| DELETE | `/reservations/{id}` | Cancel reservation |

---

## Sample Requests

### Create a User
```json
POST /users
{
  "name": "Saimir Alla",
  "birthDate": "2003-08-24",
  "email": "saimir@gmail.com",
  "password": "123456"
}
```

### Create a Concert
```json
POST /concerts
{
  "name": "Rap Festival",
  "venue": "OAKA",
  "concertDateTime": "2026-06-01T20:00:00",
  "totalSeats": 1000,
  "availableSeats": 1000
}
```

### Create a Reservation
```json
POST /reservations
{
  "userId": 1,
  "concertId": 1,
  "numberOfSeats": 2
}
```

---

## Concurrency Control

The system uses **JPA Optimistic Locking** (`@Version`) on the Concert entity to prevent overbooking under high concurrency. When multiple users attempt to reserve the last available seats simultaneously, only one transaction succeeds and the rest are rejected.

---

## Load Testing

Load testing was performed using Apache JMeter with 4 scenarios.

### Scenario 1 — Concurrency Control
100 concurrent users attempting to reserve seats for the same concert with only 50 available seats.

| Samples | Avg | Min | Max | Error % | Throughput |
|---------|-----|-----|-----|---------|------------|
| 100 | 4ms | 3ms | 43ms | 50% | 10.2/sec |

> 50% error rate is expected — only 50 seats were available. No overbooking occurred thanks to Optimistic Locking.

---

### Scenario 2 — Stress Test
500 concurrent users making POST requests with sufficient seats available.

| Samples | Avg | Min | Max | Error % | Throughput |
|---------|-----|-----|-----|---------|------------|
| 500 | 4ms | 2ms | 70ms | 1.20% | 50.5/sec |

---

### Scenario 3 — GET Performance
100 concurrent users fetching the concerts list.

| Samples | Avg | Min | Max | Error % | Throughput |
|---------|-----|-----|-----|---------|------------|
| 100 | 3ms | 2ms | 36ms | 0% | 10.1/sec |

---

### Scenario 4 — Mixed Load
50 users doing GET and 50 users doing POST simultaneously.

| | Samples | Avg | Min | Max | Error % | Throughput |
|---|---------|-----|-----|-----|---------|------------|
| GET | 50 | 2ms | 2ms | 5ms | 0% | 5.1/sec |
| POST | 50 | 2ms | 1ms | 3ms | 0% | 5.1/sec |
```
