# OWASP Top 10 Demo - Spring Boot Vulnerabilities Lab

This project demonstrates some of the OWASP Top 10 web application security vulnerabilities using a simple Spring Boot app.  
It’s designed as a learning and testing playground for security flaws such as **XSS**, **SQL Injection**, and more.

---

## Features

- **Vulnerable and Secure Endpoints** showcasing:
  - SQL Injection (unsafe vs parameterized queries)
  - Cross-Site Scripting (XSS) with `th:utext` vs safe `th:text`
  - Basic login endpoint vulnerable to logic flaws
- Dockerized PostgreSQL instance for testing injection attacks
- HTMX integration for interactive demo forms
- Simple UI with examples and explanations

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Docker & Docker Compose

### Running the Project

1. Start the database:

```bash
docker-compose up -d
```

2. Build and run the Spring Boot app:

```bash
./mvnw spring-boot:run
```

3. Open your browser and visit:

- `http://localhost:8080/xss-demo` — test XSS vulnerability
- `http://localhost:8080/sql-injection-demo` — test SQL injection examples
- `http://localhost:8080/login` — test login endpoint

---

## Project Structure

```
src/
├── main/
│   ├── java/           # Java source code
│   ├── resources/
│   │   ├── templates/  # Thymeleaf templates (HTML)
│   │   └── application.properties
│   └── docker/         # Docker configs (Postgres)
docker-compose.yml      # Docker compose file for DB
pom.xml                 # Maven config
README.md               # This file
```

---

## Security Notes

- Vulnerable endpoints **should never be used in production!**  
- This project is for educational purposes only.
- Always use output escaping (`th:text`) and parameterized queries to avoid XSS and SQL injection.
- Implement additional security like CSP headers, input validation, and proper authentication.

---


