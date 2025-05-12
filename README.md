---
# 🏆 **BlogSport - Java Spring Boot Project**

A web application for creating, managing, and publishing sports-related blog content.

---

## 📋 About the Project

Developed using **Java Spring Boot**, **Maven**, and **Thymeleaf** for server-side rendering, this project features user authentication, post management, and a responsive interface for sports content publishing.

This project was developed as part of the *Web Application Development* course and serves as a practical experience in full-stack Java development and cloud deployment.

### 👥 Project Team

| Name                        | Function                                 |
| --------------------------- | ---------------------------------------- |
| Luiz Alberto Cury Andalécio | Programmer                               |
| Gabriel Lucas Silva Seabra  | Programmer                               |
| Rafael Godoi Orbolato       | Guiding Professor                        |

### 🎯 Main Features

### 🛠️ Technologies Used

* **Frontend**:
  * Thymeleaf templates for dynamic HTML pages

* **Backend**:
  * Java 21 + Spring Boot 3.x
  * Maven for project build and dependency management
  * PostgreSQL as the relational database
  * JPA/Hibernate for ORM and data persistence
  * Spring Security for login and access control

* **Others**:
  * Deployed on AWS (EC2 / Elastic Beanstalk / RDS)
  * Docker

### 📁 Project Structure

---

## 📈 Project Status

---

## ⚙️ How to Run Locally

---

# 📓 Commit Convention

This repository follows the [Conventional Commits](https://www.conventionalcommits.org/) specification to maintain a structured, readable, and automation-friendly commit history.

### ✔️ Format

```bash
<type>(scope):<ENTER>
<short message explaining the commit>
```

### 🔧 Common Types

* `feat`: New feature
* `fix`: Bug fix
* `docs`: Documentation updates
* `style`: Styling changes (CSS, layout, images, etc.)
* `refactor`: Code refactoring without functional changes
* `perf`: Performance improvements
* `test`: Adding or updating tests
* `build`: Build-related changes (dependencies, scripts)
* `ci`: Continuous integration configuration

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

