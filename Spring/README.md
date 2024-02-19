# Spring Boot Authentication and Authorization

This project implements a secure backend for user authentication and authorization using Spring Boot. It includes endpoints for user login and signup, utilizing JSON Web Tokens (JWT) for secure authentication.

## Features

- **User Authentication and Authorization:** Implement secure user authentication with JWT and ensure that only authenticated users can access protected resources.

- **REST API Endpoints:**
  - `/api/auth/register`: Allows users to create new accounts and securely stores their information in the H2 database.
  - `/api/auth/login`: Verifies user credentials and generates a JWT upon successful authentication.
  - `/hello`: Returns "Hello from GreenStitch" only for authenticated users.

## Technologies Used

- **Spring Boot:** Framework for building robust and scalable Java applications.
- **Spring Security:** Provides comprehensive security services for Java EE-based enterprise software applications.
- **H2 Database:** In-memory database for storing user account information securely.
  ![image](https://github.com/sarankumar007/springboot-JWT-security/assets/93342856/1bea1b11-1119-4ca6-96b4-bf142447b1ef)

- **JSON Web Tokens (JWT):** Used for secure user authentication and authorization.

## Project Setup

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/sarankumar007/springboot-JWT-security.git
   cd springboot-JWT-security

2. **Build and Run:**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run

## Sample json for postman (register / login) 
```
{
   "username":"sk",
   "password":"password"
}
```

## Access Endpoints:

- Signup: `http://localhost:8000/api/auth/register` (POST request with user details)
  ![image](https://github.com/sarankumar007/springboot-JWT-security/assets/93342856/e8a6d3a5-2551-47cb-bc56-2b8d2dae6d5e)

- Login: `http://localhost:8000/api/auth/login` (POST request with user credentials)
  ![image](https://github.com/sarankumar007/springboot-JWT-security/assets/93342856/3b2f9bf2-bece-4b3a-8ff7-b119b478adb2)

- Hello: `http://localhost:8000/hello` (GET request with JWT token in Authorization header)
  ![image](https://github.com/sarankumar007/springboot-JWT-security/assets/93342856/01f5b9f1-12fd-4ebe-b3ab-8433f830298f)


## Dependencies:

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [H2 Database](https://www.h2database.com/)
- [JSON Web Tokens (JWT)](https://jwt.io/)


