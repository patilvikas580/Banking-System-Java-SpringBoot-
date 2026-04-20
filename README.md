# 🏦 Banking System — Java Spring Boot

A full-featured **RESTful Banking System** built with **Java 17** and **Spring Boot 3**, supporting secure user authentication, account management, fund transfers, transaction history, and PDF bank statement generation.

---

## 📸 Screenshots

**Create Account** — Register a new bank account with user details
![Create](https://github.com/user-attachments/assets/a0a7a390-2743-43d0-89d2-2787001e5666)

**Login** — Authenticate with credentials to receive a JWT token
![Login Cred](https://github.com/user-attachments/assets/c9f9c3f0-6087-4b13-84b9-01f7ddbe9355)
**Name Enquiry** — Look up the account holder's name by account number
![Name Enquiry](https://github.com/user-attachments/assets/d2bfe1fa-fe02-4ab3-92af-0a3037d94495)
**Balance Enquiry** — Check the current available balance of an account
![Balance Enquiry](https://github.com/user-attachments/assets/1a42894a-8bc7-4cbf-b823-4bc1c51be9f7)
**Credit Account** — Deposit funds into a specified bank account
![Credit account](https://github.com/user-attachments/assets/b4178407-579a-417f-ae62-bddfb82f3c43)
**Transfer Money** — Transfer funds securely between two accounts
![Transfer Money](https://github.com/user-attachments/assets/4b920aa5-9e8d-4273-92ce-cb89f7502415)
**Statement Email Attachment** — Bank statement automatically sent as an email attachment
![Statetement Attachment](https://github.com/user-attachments/assets/977d4856-4b95-40d6-a324-d3de10d1a818)
**PDF Bank Statement** — Downloadable PDF showing full transaction history
![Statements PDF](https://github.com/user-attachments/assets/3224f835-207d-4979-9d3a-64b1d3c958c4)


---

## ✨ Features

### 🔐 Authentication & Security
- User **Registration** and **Login** with JWT-based authentication
- Stateless session management using **JSON Web Tokens (JWT)**
- Role-based access control via **Spring Security**
- Password encryption using **BCrypt**

### 👤 Account Management
- Create new bank accounts linked to registered users
- View account details including balance and account number
- Account number auto-generation
- Fetch account info by account ID or user

### 💸 Transaction Operations
- **Credit** — Deposit funds into an account
- **Debit** — Withdraw funds from an account
- **Fund Transfer** — Transfer money between two accounts
- Real-time balance updates after every transaction
- Transaction validation (e.g., insufficient balance checks)

### 📋 Transaction History
- View complete transaction history per account
- Transactions stored with timestamp, type, and amount
- Filter-ready structured data via REST API

### 📄 PDF Bank Statement Generation
- Generate and download a **PDF bank statement** for any account
- Statement includes transaction history with dates, types, and amounts
- Powered by **iTextPDF**

### 📧 Email Notifications
- Email alerts sent on account creation
- Notifications for credit and debit transactions
- Powered by **Spring Boot Mail**

---

## 🛠️ Technologies Used

| Technology | Description |
|---|---|
| **Java 17** | Core programming language |
| **Spring Boot 3.5** | Application framework |
| **Spring Data JPA** | ORM and database access layer |
| **Spring Security** | Authentication and authorization |
| **JWT (jjwt 0.12.6)** | Stateless token-based auth |
| **MySQL** | Relational database |
| **iTextPDF 5.5.13** | PDF generation for bank statements |
| **Spring Boot Mail** | Email notification service |
| **Lombok** | Boilerplate code reduction |
| **Maven** | Build and dependency management |

---

## 📁 Project Structure

```
Banking_System/
├── src/
│   └── main/
│       ├── java/com/proj/Banking_System/
│       │   ├── controller/       # REST API Controllers
│       │   ├── service/          # Business Logic Layer
│       │   ├── repository/       # JPA Repositories
│       │   ├── entity/           # JPA Entity Classes
│       │   ├── dto/              # Data Transfer Objects
│       │   ├── config/           # Security & App Configuration
│       │   └── utils/            # Helper / Utility Classes
│       └── resources/
│           └── application.properties
├── pom.xml
└── README.md
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8+

### 1. Clone the Repository

```bash
git clone https://github.com/patilvikas580/Banking-System-Java-SpringBoot-.git
cd Banking-System-Java-SpringBoot-
```

### 2. Configure the Database

Create a MySQL database:

```sql
CREATE DATABASE banking_system;
```

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banking_system
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Mail Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_APP_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# JWT Secret
jwt.secret=YOUR_JWT_SECRET_KEY
```

### 3. Build and Run

```bash
./mvnw clean install
./mvnw spring-boot:run
```

The server starts at: `http://localhost:8080`

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/register` | Register a new user |
| `POST` | `/api/auth/login` | Login and receive JWT token |

### Account
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/user/account` | Create a new account |
| `GET` | `/api/user/balanceEnquiry` | Get account balance |
| `GET` | `/api/user/nameEnquiry` | Get account holder name |

### Transactions
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/user/credit` | Credit an account |
| `POST` | `/api/user/debit` | Debit an account |
| `POST` | `/api/user/transfer` | Transfer funds between accounts |

### Statement
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/statement` | Get transaction history |
| `GET` | `/api/statement/pdf` | Download PDF bank statement |

> 💡 All secured endpoints require the `Authorization: Bearer <token>` header.

---

## 🔒 Security

- All sensitive endpoints are protected by JWT authentication
- Passwords are hashed using BCrypt before storage
- Tokens are stateless and expire after a configurable duration
- Spring Security filter chain handles all auth logic

---

## 🤝 Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add YourFeature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

---

## 👨‍💻 Author

**Vikas Patil**
- GitHub: [@patilvikas580](https://github.com/patilvikas580)

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).

---

> ⭐ If you found this project helpful, please consider giving it a star!
