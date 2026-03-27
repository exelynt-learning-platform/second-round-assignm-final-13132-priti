# 🛒 E-Commerce Backend — Production-Ready Spring Boot REST API

A professional, secure, and scalable e-commerce backend built with **Spring Boot 3**, **Spring Security (JWT)**, **Spring Data JPA (MySQL)**, and **Stripe Payment Integration**.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 **JWT Authentication** | Register / Login with BCrypt-encrypted passwords and stateless JWT tokens |
| 👥 **Role-Based Access** | `ROLE_USER` and `ROLE_ADMIN` authorization on every endpoint |
| 📦 **Product Management** | Full CRUD for products (public read, admin-only write) |
| 🛒 **Cart Management** | Personal cart per user — add, update, remove items |
| 📋 **Order Management** | Checkout from cart, view order history, update order status |
| 💳 **Stripe Payments** | Create Stripe PaymentIntents, get `clientSecret` for frontend integration |
| 📄 **Swagger UI** | Interactive API docs at `/swagger-ui/index.html` |
| 🧪 **Unit Tests** | JUnit 5 & Mockito tests for Auth, Product, Cart, and Order services |
| 🔄 **Auto Data Seeding** | Sample products and admin/user accounts loaded on startup |

---

## 🛠 Tech Stack

- **Java 17** · **Spring Boot 3.2.x** · **Spring Security** · **Spring Data JPA**
- **MySQL** · **Lombok** · **MapStruct** · **JJWT**
- **Stripe Java SDK** · **SpringDoc OpenAPI** · **JUnit 5 & Mockito**

---

## 🏗 Project Structure

```text
com.ecommerce
├── config         → SecurityConfig, Swagger config
├── security       → JwtUtils, AuthTokenFilter, AuthEntryPointJwt
├── controller     → AuthController, ProductController, CartController, OrderController
├── service
│   └── impl       → AuthServiceImpl, ProductServiceImpl, CartServiceImpl, OrderServiceImpl, PaymentServiceImpl
├── repository     → UserRepository, ProductRepository, CartItemRepository, OrderRepository
├── entity         → User, Product, CartItem, Order (with OrderStatus enum)
├── dto            → LoginRequest, SignupRequest, JwtResponse, ProductDTO, CartDTO, OrderDTO
├── mapper         → ProductMapper, CartItemMapper, OrderMapper (MapStruct)
├── exception      → GlobalExceptionHandler, ResourceNotFoundException, BadRequestException
└── util           → DataLoader
```

---

## 🚦 Getting Started

### Prerequisites
- Java 17+
- MySQL Server
- Maven

### Setup

**1. Clone the repository**
```bash
git clone https://github.com/exelynt-learning-platform/second-round-assignm-final-13132-priti.git
cd second-round-assignm-final-13132-priti
git checkout develop-the-backend-for-an-e-commerce-system-28151
```

**2. Create the database**
```sql
CREATE DATABASE ecommerce_db;
```

**3. Configure `src/main/resources/application.properties`**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password

app.jwt.secret=your_256_bit_secret_key_at_least_32_chars
app.jwt.expiration-ms=86400000

stripe.api.key=sk_test_your_stripe_secret_key
```

**4. Build and Run**
```bash
mvn clean spring-boot:run
```

> ✅ The app automatically creates all tables and seeds sample data on first startup.

---

## 🔑 Default Seeded Accounts

| Role | Username | Password |
|---|---|---|
| Admin | `admin` | `admin123` |
| User | `user` | `user123` |

---

## 📖 API Endpoints

### 🔓 Authentication (Public)

| Method | Endpoint | Body | Description |
|---|---|---|---|
| `POST` | `/auth/register` | `{ "username", "password", "role": "user"/"admin" }` | Register a new user |
| `POST` | `/auth/login` | `{ "username", "password" }` | Login and receive JWT token |

### 📦 Products

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/products` | Public | Get all products |
| `GET` | `/api/products/{id}` | Public | Get product by ID |
| `POST` | `/api/products` | Admin | Create a product |
| `PUT` | `/api/products/{id}` | Admin | Update a product |
| `DELETE` | `/api/products/{id}` | Admin | Delete a product |

### 🛒 Cart (User Auth Required)

| Method | Endpoint | Params | Description |
|---|---|---|---|
| `GET` | `/api/cart` | — | View current user's cart |
| `POST` | `/api/cart/add` | `?productId=1&quantity=2` | Add item to cart |
| `PUT` | `/api/cart/update` | `?cartItemId=1&quantity=3` | Update item quantity |
| `DELETE` | `/api/cart/remove/{cartItemId}` | — | Remove item from cart |

### 📋 Orders & Payments (User Auth Required)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/orders/checkout` | Create order from current cart |
| `GET` | `/api/orders` | Get all orders of logged-in user |
| `POST` | `/api/orders/{orderId}/pay` | Create Stripe PaymentIntent & mark order PAID |

---

## 💳 Stripe Payment Flow

1. Checkout → `POST /api/orders/checkout` → Order created (`CREATED`)
2. Pay → `POST /api/orders/{orderId}/pay` → Returns Stripe `clientSecret`
3. Frontend confirms payment using `clientSecret` + Stripe.js
4. Order status → `PAID`

---

## 📄 Swagger / API Docs

| URL | Description |
|---|---|
| http://localhost:8080/swagger-ui/index.html | Interactive Swagger UI |
| http://localhost:8080/api-docs | Raw OpenAPI JSON |

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 🔒 Security Summary

| Endpoint Pattern | Access |
|---|---|
| `/auth/**` | Public |
| `GET /api/products/**` | Public |
| `/swagger-ui/**`, `/api-docs/**` | Public |
| All other endpoints | Requires valid JWT Bearer token |
| `POST/PUT/DELETE /api/products/**` | Requires `ROLE_ADMIN` |

---

## 📄 License

Distributed under the **MIT License**.
