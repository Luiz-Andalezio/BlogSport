---

# 🏆 **BlogSport - Java Spring Boot Project**

A complete web application for creating, managing, and publishing sports-related blog content.

Built with **Java Spring Boot**, **Maven**, **Thymeleaf**, and modern web tools like **Tailwind CSS** and **htmx**, this project features secure authentication, category/post/comment/like management, and an interactive UI for engaging sports fans.

This system was developed as part of the *Web Application Development* course and is designed to offer practical experience in **full-stack Java development**, **modern UI**, and **containerized deployment** with Docker.

---

## 👥 Project Team

| Name                        | Function          |
| --------------------------- | ----------------- |
| Luiz Alberto Cury Andalécio | Programmer        |
| Gabriel Lucas Silva Seabra  | Programmer        |
| Rafael Godoi Orbolato       | Guiding Professor |

---

## 🎯 Main Features

### 🔐 **Authentication and User Management**

1. **Pre-Registered Admin (Blogger)**

   * The blogger has exclusive access to content administration via a pre-created account.

2. **User Registration & Login (Content Consumers)**

   * Secure registration form with validation for:

     * Full name
     * Email (unique)
     * Date of birth
     * Password + confirmation

3. **User Account Management**

   * Edit profile (name, email, password, profile picture)
   * View own comments, liked posts, and liked comments in dedicated areas
   * Logout from the system securely

---

### 📝 **Content and Interaction Features**

4. **Category and Post Management (Admin Only)**

   * Create, update, delete categories
   * Create and edit posts using a rich text editor (**Summernote**) with image support
   * Posts are linked to categories and author metadata

5. **User Interaction on Posts**

   * Like and unlike posts and comments
   * Write comments and replies using **@mention**
   * Edit or delete own comments only
   * Nested comment structure (reply to reply)

---

### 🔎 **Additional Functionalities**

6. **Search System**

   * Keyword-based search for posts

7. **Reports**

   * Admin can generate downloadable PDF reports with post/comment statistics using **iTextPDF**

8. **Security & Access Control**

   * Role-based access (admin vs. regular users)
   * Protected routes via **Spring Security**
   * BCrypt password encryption

9. **UX/UI Enhancements**

   * Dynamic page updates via **htmx.js**
   * Responsive layout with **Tailwind CSS**

---

## 🛠️ Technologies Used

* **Frontend**:

  * Thymeleaf for HTML templates
  * Tailwind CSS for responsive design
  * htmx.js for client-server interactions (likes, comments, filters, etc.)

* **Backend**:

  * Java 21 with Spring Boot 3.x
  * Maven for build automation
  * JPA/Hibernate for ORM
  * PostgreSQL as database
  * Spring Security for authentication and access control
  * Flyway for version-controlled database migrations
  * iTextPDF for PDF generation

* **DevOps**:

  * Docker for database containerization
  * Custom PostgreSQL Dockerfile with timezone, locale, and health checks

---

## 📁 Project Structure

* `src/main/java/` – Organized by MVC pattern:
  * `controller/` – Handles HTTP routes and UI interactions
  * `service/` – Business logic
  * `model/` – Entity classes and DTOs
  * `repository/` – Spring Data interfaces for DB access
  * `security/` – JWT auth, BCrypt, access filters

* `src/main/resources/`:
  * `application.yml` – Central configuration
  * `templates/` – Thymeleaf HTML pages
  * `static/` – Images, Tailwind, scripts
  * `db/migration/` – Flyway migration scripts

* `src/test/java/` – JUnit tests

* `docs/` – Diagrams, markdowns, generated reports

* `programs/` – Tools & Docker setups:
  * `postgresql/` – PostgreSQL container image
  * `pgadmin/` – PgAdmin4 setup instructions

---

## 📈 Project Status

* **Current progress**: \~10% completed

* **Latest**:
  * Posting tests
  * Security Spring Auth
 
* **Next steps**:
  1. Classes and tables for admin, posts, comments, likes implemented
  2. Admin panel functional
  3. Authentication & profile management active
  4. Enhance validations and security filters
  5. PDF report integration
  6. Final UI polishing and responsive layout testing
  7. Deployment phase

---

## ⚙️ How to Run Locally (Setup Guide for Windows & Linux Mint)

### ✅ Prerequisites

Install the following tools:

* Java 21 JDK
* Maven
* Docker Engine
* PgAdmin (optional, DB visualization)
* IntelliJ IDEA / Eclipse / VS Code

---

### 🐳 Step 1: Set Up PostgreSQL with Docker

```bash
# Navigate to the container setup folder
cd programs/postgresql

# Build the image
sudo docker build -t="web/postgresql" .

# Create persistent volume
sudo docker volume create --name postgresql_data

# Run the container
sudo docker run -i -t -d --name postgresql -p 5432:5432 \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=pgdb123 \
  --volume postgresql_data:/var/lib/postgresql/data \
  web/postgresql
```

---

### 🔧 Step 2: Configure Spring Boot App

Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: pgdb123
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  thymeleaf:
    cache: false
```

---

### 🚀 Step 3: Run the Application

```bash
# Build and run (Linux/Mac)
./mvnw spring-boot:run

# Or on Windows
mvnw spring-boot:run
```

Alternatively:

```bash
mvn clean install
java -jar target/blogsport-1.0.0.jar
```

Then open your browser:

```
http://localhost:8080
```

---

### 🧠 Optional: Connect PgAdmin

* Host: `localhost`
* Port: `5432`
* Username: `postgres`
* Password: `pgdb123`

---

## 🧼 Troubleshooting

* ❌ **Port Conflict**: Stop other PostgreSQL services.
* 🔑 **Login Error**: Ensure Spring Security form and credentials exist.
* 🗃️ **Tables Not Created?** Set `ddl-auto=create` once, then revert to `update`.

---

## 📓 Commit Convention

This repo uses [Conventional Commits](https://www.conventionalcommits.org/) for clear and semantic history.

### ✔️ Format

```bash
<type>(scope): short description
```

### 🔧 Types

* `feat`: New feature
* `fix`: Bug fix
* `docs`: Documentation changes
* `style`: CSS/layout updates
* `refactor`: Internal code improvements
* `test`: New tests or updates
* `build`: Build tools/config changes
* `ci`: CI/CD pipeline changes

### 📝 Example

```bash
git commit -m "feat(comment):
> Add reply-to-reply support"

git commit -m "fix(auth):
> Fix password validation bug"

git commit -m "docs(readme):
> Update local run instructions"
```

---