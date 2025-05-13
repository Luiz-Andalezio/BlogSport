---
# 🏆 **BlogSport - Java Spring Boot Project**

A web application for creating, managing, and publishing sports-related blog content.

Developed using **Java Spring Boot**, **Maven**, and **Thymeleaf** for server-side rendering, this project features user authentication, post management, and a responsive interface for sports content publishing.

This project was developed as part of the *Web Application Development* course and serves as a practical experience in full-stack Java development and cloud deployment.

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

1. **Admin (Blogger) Pre-Registered Login**
  - The blogger (admin) account is pre-registered and has access to post and manage content.

2. **User Registration and Login (Content Consumers)**
  - Users can sign up and log in to access and interact with the platform.
  - During registration, the user must provide:
    - Full name
    - Email address
    - Date of birth
    - Password (with confirmation field)

3. **User Account Management**
  - Logged-in users can:
    - Edit their name, email, password, and profile picture
    - Log out of the system
    - View all of their own comments
    - View all liked posts and liked comments in separate sections

### 📝 **Content and Interaction Features**

4. **Category and Post Management (Admin Only)**
  - The blogger can:
    - Create, update, and delete categories (CRUD)
    - Create, edit, and delete blog posts
    - Add images to posts using the **Summernote** rich-text editor

5. **User Engagement on Posts**
  - Registered users can:
    - Like blog posts and comments
    - Write comments on posts
    - Reply to comments using **@mentions**, similar to Instagram
    - Edit or delete *only* their own comments

### 🔎 **Additional Features**

6. **Search Functionality**
  - A search bar allows users to find specific blog posts by keywords.

7. **Data Storage and Security**
  - All data is stored in a secure database.
  - Passwords are encrypted for protection.

---

## 🛠️ Technologies Used

- **Frontend**:
  - Thymeleaf templates for dynamic HTML pages

- **Backend**:
  - Java 21 + Spring Boot 3.x
  - Maven for project build and dependency management
  - PostgreSQL as the relational database
  - JPA/Hibernate for ORM and data persistence
  - Spring Security for login and access control

- **Others**:
  - Deployed on Docker

---

## 📁 Project Structure

- `src/main/java/` – Java source code (organized by packages: `controller`, `service`, `repository`, `model`, etc.).
- `src/main/resources/` – Configuration and resource files:
  - `application.properties` or `application.yml` – Main configuration.
  - `static/` – Static files (images, JS, CSS).
  - `templates/` – HTML/Thymeleaf templates.
  - `db/` – SQL scripts or database migrations.
- `src/test/java/` – Unit and integration tests.
- `target/` – Compiled output and packaged JAR/WAR (auto-generated after build).
- `docs/` – Documentation files (PDFs, diagrams, markdown, etc.).
- `programs/` – Custom folder containing services and tools used in development:
  - `postgresql/` – Custom Docker setup for PostgreSQL database:
   - Contains a `Dockerfile` that sets locale, timezone (`pt_BR.UTF-8`, `America/Sao_Paulo`), and health checks.
   - The container is built and run with a named volume (`postgresql_data`) for data persistence.
   - Includes setup commands to:
    - Build the Docker image: `web/postgresql`
    - Run the container on port `5432` with user `postgres`
  - `pgadmin/` – PgAdmin4 installation and setup:
   - Installed via apt with repository and keyring.
   - Used to manage and inspect the PostgreSQL database locally.
- `pom.xml` or `build.gradle` – Build configuration (Maven or Gradle).
- `.gitignore` – Files/folders ignored by Git.
- `README.md` – Project overview and usage guide.

---

## 📈 Project Status

- **Current progress**: 10% (Prototyped in local environment).
- **Next steps**:
  * I. Implementation of the Category, User, Comment, and Like classes, all with their functionalities.
  * II. Login integration with complete validations.
  * III. Backend and Frontend optimization.
  * IV. Future deployment.

---

## ⚙️ How to Run Locally (Setup Guide for Windows & Linux Mint)

This guide walks you through running the project locally on both systems.

#### ✅ **Prerequisites**

Make sure the following are installed on your system:

- **For both Windows and Linux Mint**:
  - [Java 21 JDK](https://jdk.java.net/21/)
  - [Maven](https://maven.apache.org/download.cgi)
  - [Docker Engine](https://docs.docker.com/get-docker/)
  - [PgAdmin (optional, for DB inspection)](https://www.pgadmin.org/download/)
  - A modern IDE (e.g., IntelliJ IDEA, Eclipse, VS Code)

### 🧩 Project Dependencies Summary

- **Frontend**: Thymeleaf Templates for HTML rendering
- **Backend**:
  - Java 21 + Spring Boot 3.x
  - Maven (build & dependency management)
  - PostgreSQL (Relational DB)
  - JPA/Hibernate (ORM)
  - Spring Security (Authentication/Authorization)
- **Other**: Docker for containerized PostgreSQL

#### 🐳 Step 1: Set Up the Database with Docker

1. Open a terminal or command prompt.
2. Navigate to the project’s `programs/postgresql/` folder:

  ```bash
  cd ~/Programs/postgresql   # Linux Mint
  cd path\to\Programs\postgresql  # Windows
  ```

3. Build the Docker image:

  ```bash
  sudo docker build -t="web/postgresql" .
  ```

4. Create a Docker volume:

  ```bash
  sudo docker volume create --name postgresql_data
  ```

5. Run the PostgreSQL container:

  ```bash
  sudo docker run -i -t -d --name postgresql -p 5432:5432 \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=pgdb123 \
    --volume postgresql_data:/var/lib/postgresql/data \
    web/postgresql
  ```

#### 🔧 Step 2: Configure the Application

Edit your `src/main/resources/application.properties` (or `.yml`) with the correct database connection:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=pgdb123
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.thymeleaf.cache=false
```

#### 🚀 Step 3: Run the Application

- **On Linux Mint**:
  1. Open the terminal.
  2. Navigate to the project root:

    ```bash
    cd ~/your-project-folder
    ```

  3. Run using Maven:

    ```bash
    ./mvnw spring-boot:run
    ```

    Or:

    ```bash
    mvn clean install
    java -jar target/your-project-name.jar
    ```

    > Replace `your-project-name.jar` with the correct JAR file name.

- **On Windows**:
  1. Open **CMD** or **PowerShell**.
  2. Navigate to your project folder:

    ```cmd
    cd C:\path\to\your-project
    ```

  3. Run with Maven:

    ```cmd
    mvnw spring-boot:run
    ```

    Or manually:

    ```cmd
    mvn clean install
    java -jar target\your-project-name.jar
    ```

#### 🌐 Step 4: Access the App

Open your browser and go to:

```
http://localhost:8080
```

### 🧠 Optional: Use PgAdmin to Monitor the DB

- **Host**: `localhost`
- **Port**: `5432`
- **Username**: `postgres`
- **Password**: `pgdb123`
- Set connection name: `PostgreSQL Container`

### 🧼 Common Troubleshooting

- 🔌 If port `5432` is in use, check for other PostgreSQL services and stop them.
- 🔐 If Spring Security is active, ensure that default credentials or login form is implemented.
- 🗃️ Use `spring.jpa.hibernate.ddl-auto=create` the first time to auto-create tables (then switch back to `update`).

---

## 📓 Commit Convention

This repository follows the [Conventional Commits](https://www.conventionalcommits.org/) specification to maintain a structured, readable, and automation-friendly commit history.

### ✔️ Format

```bash
<type>(scope):<ENTER>
<short message explaining the commit>
```

### 🔧 Common Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation updates
- `style`: Styling changes (CSS, layout, images, etc.)
- `refactor`: Code refactoring without functional changes
- `perf`: Performance improvements
- `test`: Adding or updating tests
- `build`: Build-related changes (dependencies, scripts)
- `ci`: Continuous integration configuration

### 📍 Scope

Describes the specific part of the project affected by the change, such as a module (`cryptography`), a page (`login-page`), or a feature (`carousel`).

### 📝 Example

```bash
git commit -am "refactor(cryptography):
> Improve code indentation."

git commit -am "fix(login-page):
> Fix null login issue."

git commit -am "feat(carousel):
> Add carousel to home page."
```

---
