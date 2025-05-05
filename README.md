📝 Simple Blog System

A full-featured blog platform built with Spring Boot that supports user registration,
authentication (JWT & social login), and allows users to create, like, and comment on posts.

 🚀 Features

- ✅ **User Registration & Login**
  - Register new users with email & password
  - Secure login with JWT-based authentication
  - Refresh token support
  - Social login via **Google** and **GitHub**

- ✍️ **Post Management**
  - Create, read, update, and delete blog posts
  - List all posts or filter by user

- 💬 **Comment System**
  - Add comments to posts
  - List comments by post or user
  - Delete own comments

- ❤️ **Like System**
  - Like/unlike posts
  - Check like status
  - Count likes per post

- 🧑‍💼 **User Roles**
  - Role-based access (e.g., USER, ADMIN — can be extended)

- 🔐 **Security**
  - JWT authentication for REST APIs
  - OAuth2 login with custom user service
  - Secure endpoints using Spring Security
  - CORS configuration for frontend integration

- 📜 **API Documentation**
  - Swagger UI enabled for testing and exploring endpoints

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security**
- **JWT (jjwt)**
- **Spring OAuth2 Client**
- **MapStruct**
- **Lombok**
- **H2 (dev) / PostgreSQL (prod)**
- **Swagger (springdoc-openapi-ui)**
- **Maven**

---

## 📦 Project Structure

```bash
├── bootstrap/
├── config/
├── controller/
├── dto/
├── entity/
├── exception/
├── mapper/
├── repository/
├── security/
├── service/
└── resources/
