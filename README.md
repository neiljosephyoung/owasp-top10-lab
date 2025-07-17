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

# OWASP Top 10 Lab (Spring Boot)

This project demonstrates several common web application security vulnerabilities based on the [OWASP Top 10](https://owasp.org/Top10/). Each subfolder contains a standalone Spring Boot application vulnerable to a specific issue.

---

## 🚀 How to Run Each App

Each app is a self-contained Spring Boot project. To run an app:

```bash
cd [app-folder]
./mvnw spring-boot:run
```

➡️ Requires Java 17+ and Maven (or use the included `mvnw` wrapper).

---

## 🛠️ Vulnerability Guide & Testing

### 1. 🧨 Broken Authentication  
**Folder**: `broken-auth-app`  
**Vulnerability**: Insecure login logic – the `/login` endpoint accepts any credentials without validating them properly. This is **not** an issue of protected routes, but a **flawed authentication mechanism**.

#### 🔍 How to Test:
- Visit: `http://localhost:8080/login?username=admin&password=wrong`
- You’ll receive a successful response even though the credentials are incorrect — **authentication bypass due to logic error**.

---

### 2. 🧨 SQL Injection  
**Folder**: `sql-injection-app`  
**Vulnerability**: Constructs SQL with string concatenation.

#### 🔍 How to Test:
- Visit: `http://localhost:8080/search?name=' OR '1'='1`
- Should return **all records** from the database due to SQL injection.

---

### 3. 🧨 Cross-Site Scripting (XSS)  
**Folder**: `xss-example-app`  
**Vulnerability**: User input is reflected in HTML without escaping.

#### 🔍 How to Test:
- Visit: `http://localhost:8080/greet?name=<script>alert('XSS')</script>`
- You'll see an alert box pop up — XSS successful.

---

### 4. 🧨 XML External Entity (XXE)  
**Folder**: `xxe-example-app`  
**Vulnerability**: XML parser processes external entities.

#### 🔍 How to Test:
1. Run the app.
2. Use a tool like `curl` or Postman:
```bash
curl -X POST http://localhost:8080/xml \
  -H "Content-Type: application/xml" \
  -d '<?xml version="1.0" encoding="UTF-8"?>
  <!DOCTYPE foo [ <!ENTITY xxe SYSTEM "file:///etc/passwd"> ]>
  <user><name>&xxe;</name></user>'
```

- If `/etc/passwd` contents are returned, the XXE attack worked.

---

### 5. 🧨 Server-Side Request Forgery (SSRF)  
**Folder**: `ssrf-example-app`  
**Vulnerability**: Server fetches user-supplied URLs without validation.

#### 🔍 How to Test:
- Visit: `http://localhost:8080/fetch?url=http://example.com`
- Then try accessing internal resources:
```bash
curl "http://localhost:8080/fetch?url=http://localhost:8080/actuator"
```

- If server returns actuator data, SSRF is working.

---

### 6. 🧨 Broken Access Control  
**Folder**: `broken-access-app`  
**Vulnerability**: Exposes an admin dashboard without checking roles.

#### 🔍 How to Test:
- Visit: `http://localhost:8080/broken-access/admin-dashboard`
- You’ll see: `❌ You just accessed an ADMIN dashboard WITHOUT being an admin!`
- No login required — shows missing access control check.

To compare:
- `http://localhost:8080/admin/dashboard` requires valid credentials:
  - `admin` / `adminpass` ✅ allowed  
  - `user` / `userpass` ❌ forbidden

---

