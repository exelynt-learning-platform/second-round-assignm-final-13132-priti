# E-Commerce Backend Project

This is my e-commerce backend project made using Spring Boot. It provides APIs for user login, product management, cart handling, order processing, and Stripe payment integration.

The project is built with Spring Boot 3, Spring Security with JWT, MySQL, and Stripe.

---

# Features

* User Registration and Login
* JWT Authentication
* Role-based Authorization (`USER` and `ADMIN`)
* Product CRUD Operations
* Cart Management
* Order Creation and Order History
* Stripe Payment Integration
* Swagger API Documentation
* Unit Testing using JUnit and Mockito
* Sample Data Loaded Automatically

---

# Technologies Used

* Java 17
* Spring Boot 3.2.x
* Spring Security
* Spring Data JPA
* MySQL
* Lombok
* MapStruct
* JWT (JJWT)
* Stripe Java SDK
* Swagger / SpringDoc OpenAPI
* JUnit 5
* Mockito

---

# Project Structure

```text
src/main/java/com/ecommerce
│
├── config
│   ├── SecurityConfig
│   └── SwaggerConfig
│
├── security
│   ├── JwtUtils
│   ├── AuthTokenFilter
│   └── AuthEntryPointJwt
│
├── controller
│   ├── AuthController
│   ├── ProductController
│   ├── CartController
│   └── OrderController
│
├── service
│   └── impl
│       ├── AuthServiceImpl
│       ├── ProductServiceImpl
│       ├── CartServiceImpl
│       ├── OrderServiceImpl
│       └── PaymentServiceImpl
│
├── repository
│   ├── UserRepository
│   ├── ProductRepository
│   ├── CartItemRepository
│   └── OrderRepository
│
├── entity
│   ├── User
│   ├── Product
│   ├── CartItem
│   ├── Order
│   └── OrderStatus
│
├── dto
├── mapper
├── exception
└── util
```

---

# Database Setup

Create a database in MySQL:

```sql
CREATE DATABASE ecommerce_db;
```

---

# application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

app.jwt.secret=your_secret_key
app.jwt.expiration-ms=86400000

stripe.api.key=your_stripe_secret_key
```

---

# Run the Project

```bash
mvn clean install
mvn spring-boot:run
```

After starting the application:

* Swagger UI: `http://localhost:8080/swagger-ui/index.html`
* OpenAPI Docs: `http://localhost:8080/api-docs`

---

# Default Users

These users are added automatically when the project starts for the first time.

| Role  | Username | Password |
| ----- | -------- | -------- |
| ADMIN | admin    | admin123 |
| USER  | user     | user123  |

---

# API Endpoints

## Authentication

| Method | Endpoint       | Description             |
| ------ | -------------- | ----------------------- |
| POST   | /auth/register | Register new user       |
| POST   | /auth/login    | Login and get JWT token |

Example Login Request:

```json
{
  "username": "user",
  "password": "user123"
}
```

---

## Product APIs

| Method | Endpoint           | Access     |
| ------ | ------------------ | ---------- |
| GET    | /api/products      | Public     |
| GET    | /api/products/{id} | Public     |
| POST   | /api/products      | Admin Only |
| PUT    | /api/products/{id} | Admin Only |
| DELETE | /api/products/{id} | Admin Only |

---

## Cart APIs

| Method | Endpoint                                 |
| ------ | ---------------------------------------- |
| GET    | /api/cart                                |
| POST   | /api/cart/add?productId=1&quantity=2     |
| PUT    | /api/cart/update?cartItemId=1&quantity=3 |
| DELETE | /api/cart/remove/{cartItemId}            |

These APIs require JWT token in the Authorization header:

```text
Authorization: Bearer your_token_here
```

---

## Order APIs

| Method | Endpoint                  |
| ------ | ------------------------- |
| POST   | /api/orders/checkout      |
| GET    | /api/orders               |
| POST   | /api/orders/{orderId}/pay |

---

# Stripe Payment Flow

1. Add products to cart
2. Call `/api/orders/checkout`
3. Order is created with status `CREATED`
4. Call `/api/orders/{orderId}/pay`
5. Stripe returns `clientSecret`
6. Frontend uses `clientSecret` to complete payment
7. Order status becomes `PAID`

---

# Running Tests

```bash
mvn test
```

The project contains unit tests for:

* Auth Service
* Product Service
* Cart Service
* Order Service

---

# Security Rules

| Endpoint                     | Access       |
| ---------------------------- | ------------ |
| /auth/**                     | Public       |
| GET /api/products/**         | Public       |
| /swagger-ui/**               | Public       |
| /api-docs/**                 | Public       |
| Other APIs                   | JWT Required |
| POST/PUT/DELETE Product APIs | ADMIN Only   |

---

# Notes

* Passwords are stored using BCrypt encryption.
* JWT is used for stateless authentication.
* Product read APIs are public.
* Product create, update, and delete APIs can only be accessed by admin.
* Cart and order APIs are available only after login.
* Sample products are inserted automatically when the application starts.

---

# Future Improvements

* Add pagination and sorting for products
* Add image upload for products
* Add email notifications after order placed
* Add refresh token support
* Add Docker support
* Deploy project on AWS or Render

---

This project was created for learning Spring Boot, Spring Security, REST APIs, and payment integration.
