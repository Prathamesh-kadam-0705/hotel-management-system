<div align="center">

# 🏨 Hotel Management System

### A scalable, event-driven **microservices** platform for modern hotel operations

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)

[Features](#-features) •
[Architecture](#-architecture) •
[Tech Stack](#-tech-stack) •
[Getting Started](#-getting-started) •
[Services & Ports](#-services--ports) •
[Contributing](#-contributing)

</div>

---

## 📖 About The Project

The **Hotel Management System** is a backend platform built with a **microservices architecture**. Each business capability (customers, orders, notifications) runs as an independent service, discovered through a central **Eureka Service Registry** and exposed to clients through a single **API Gateway**.

Services communicate **asynchronously through Apache Kafka**, so when an order is placed, the customer gets notified by email without the services being tightly coupled.

---

## ✨ Features

- 👤 **Customer Management**: register and manage guest/customer records
- 🛎️ **Order Management**: create and track hotel orders and requests
- 📧 **Email Notifications**: automatic emails triggered by Kafka events
- 🚪 **Single Entry Point**: all traffic routed through the API Gateway
- 🔍 **Service Discovery**: dynamic registration and lookup with Netflix Eureka
- ⚡ **Event-Driven Communication**: loosely coupled services using Kafka
- 🐳 **Fully Dockerized**: whole system starts with one command
- 🔄 **CI/CD Ready**: GitHub Actions workflows included

---

## 🏗️ Architecture

```
                        ┌──────────────────┐
        Client  ───────▶│   API Gateway    │  :8084
                        └────────┬─────────┘
                                 │ routes via Eureka
              ┌──────────────────┼───────────────────┐
              ▼                                      ▼
   ┌──────────────────┐                   ┌──────────────────┐
   │ Customer Service │  :8081            │  Order Service   │  :8082
   └────────┬─────────┘                   └────────┬─────────┘
            │                                      │ publishes events
            ▼                                      ▼
   ┌──────────────────┐                   ┌──────────────────┐
   │   PostgreSQL     │                   │      Kafka       │  :9092
   │ hotel_customer_db│                   └────────┬─────────┘
   └──────────────────┘                            │ consumes events
   ┌──────────────────┐                            ▼
   │   PostgreSQL     │                   ┌──────────────────┐
   │  hotel_order_db  │                   │Notification Svc  │  :8083
   └──────────────────┘                   └────────┬─────────┘
                                                   ▼
                                           📧 SMTP (Gmail)

        ┌──────────────────────────────────────────┐
        │  Service Registry (Eureka)  :8761        │
        │  All services register & discover here   │
        └──────────────────────────────────────────┘
```

---

## 🧰 Tech Stack

| Category | Technology |
|---|---|
| **Language** | Java |
| **Framework** | Spring Boot, Spring Cloud |
| **API Gateway** | Spring Cloud Gateway |
| **Service Discovery** | Netflix Eureka |
| **Messaging** | Apache Kafka (KRaft mode) + Kafka UI |
| **Database** | PostgreSQL |
| **Email** | Spring Mail (SMTP) |
| **Containerization** | Docker & Docker Compose |
| **CI/CD** | GitHub Actions |

---

## 📂 Project Structure

```
hotel-management-system/
├── .github/workflows/      # CI/CD pipelines
├── api-gateway/            # Single entry point for all requests
├── service-registry/       # Eureka discovery server
├── Customer-Service/       # Customer management microservice
├── order-service/          # Order management microservice
├── notification-service/   # Kafka consumer + email sender
├── docker-compose.yml      # Orchestrates the entire system
└── .gitignore
```

---

## 🌐 Services & Ports

| Service | Port | Description |
|---|:---:|---|
| 🚪 API Gateway | `8084` | Entry point for all client requests |
| 🔍 Service Registry (Eureka) | `8761` | Eureka dashboard |
| 👤 Customer Service | `8081` | Customer APIs |
| 🛎️ Order Service | `8082` | Order APIs |
| 📧 Notification Service | `8083` | Sends emails from Kafka events |
| 📨 Kafka | `9092` | Message broker |
| 🖥️ Kafka UI | `8080` | Browse topics and messages |

---

## 🚀 Getting Started

### Prerequisites

Make sure you have installed:

- [Docker](https://www.docker.com/get-started) & Docker Compose
- [PostgreSQL](https://www.postgresql.org/download/) running locally on port `5432`
- [Git](https://git-scm.com/)
- A Gmail account with an [App Password](https://support.google.com/accounts/answer/185833) (for email notifications)

### 1️⃣ Clone the repository

```bash
git clone https://github.com/Prathamesh-kadam-0705/hotel-management-system.git
cd hotel-management-system
```

### 2️⃣ Create the databases

Open `psql` (or pgAdmin) and run:

```sql
CREATE DATABASE hotel_customer_db;
CREATE DATABASE hotel_order_db;
```

### 3️⃣ Configure environment variables

Create a `.env` file in the project root:

```env
# PostgreSQL credentials
DB_USERNAME=your_postgres_username
DB_PASSWORD=your_postgres_password

# Gmail SMTP (use an App Password, not your real password)
MAIL_USERNAME=your_email@gmail.com
MAIL_APP_PASSWORD=your_gmail_app_password
```

> ⚠️ **Never commit your `.env` file.** Make sure it is listed in `.gitignore`.

### 4️⃣ Start everything

```bash
docker-compose up -d
```

### 5️⃣ Verify it's running

| What | URL |
|---|---|
| Eureka Dashboard | http://localhost:8761 |
| Kafka UI | http://localhost:8080 |
| API Gateway | http://localhost:8084 |

All services should appear as **UP** in the Eureka dashboard.

### 🛑 Stop the system

```bash
docker-compose down
```

---

## 🔌 API Usage

All requests go through the **API Gateway** at `http://localhost:8084`.

```bash
# Example (update the paths to match your controllers)
curl http://localhost:8084/customers
curl http://localhost:8084/orders
```

> 📝 *Add your actual endpoints here, for example:*
>
> | Method | Endpoint | Description |
> |---|---|---|
> | `POST` | `/customers` | Create a customer |
> | `GET` | `/customers/{id}` | Get a customer by ID |
> | `POST` | `/orders` | Place a new order |
> | `GET` | `/orders/{id}` | Get an order by ID |

---

## 🔄 How It Works

1. A client sends a request to the **API Gateway**.
2. The gateway looks up the target service in **Eureka** and forwards the request.
3. The **Order Service** saves the order to PostgreSQL and publishes an event to **Kafka**.
4. The **Notification Service** consumes the event and sends an **email** to the customer.

---

## 🗺️ Roadmap

- [ ] Authentication & authorization (JWT / Spring Security)
- [ ] Room & booking management service
- [ ] Payment / billing service
- [ ] Centralized configuration (Spring Cloud Config)
- [ ] Distributed tracing & monitoring
- [ ] Swagger / OpenAPI documentation
- [ ] Frontend dashboard

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the project
2. Create your feature branch: `git checkout -b feature/AmazingFeature`
3. Commit your changes: `git commit -m "Add some AmazingFeature"`
4. Push to the branch: `git push origin feature/AmazingFeature`
5. Open a Pull Request

---

## 👨‍💻 Author

**Prathamesh Kadam**

[![GitHub](https://img.shields.io/badge/GitHub-Prathamesh--kadam--0705-181717?style=for-the-badge&logo=github)](https://github.com/Prathamesh-kadam-0705)

---

<div align="center">

⭐ If you found this project useful, please give it a star! ⭐

</div>
