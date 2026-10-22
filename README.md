# 🎓 Core Java Console Quiz & Assessment Portal

A feature-rich, enterprise-grade command-line quiz application built using **Core Java** and **MySQL (JDBC)**. Designed as a college mini-project to demonstrate Object-Oriented Programming (OOP) principles, Database Connectivity, Collections, and Exception Handling.

---

## 🚀 Features
* **MySQL Database Integration:** Fetches questions dynamically from a MySQL database and stores candidate scores securely using JDBC (`PreparedStatement`, `ResultSet`).
* **Random Question Shuffling:** Utilizes Java Collections (`Collections.shuffle`) to randomize question order on every new session.
* **Smart Exception Handling:** Prevents application crashes and handles invalid inputs gracefully using `try-catch` blocks and input validation loops.
* **Interactive Leaderboard:** Displays past student rankings sorted by performance directly from the database.
* **Performance Analytics:** Computes exact score percentages, time taken, and assigns automated letter grades (A+, A, B, C, F).

---

## 🛠️ Project Architecture (MVC / DAO Pattern)
* `DatabaseConnection.java` - Manages secure database connection credentials via JDBC.
* `Question.java` - Object-oriented model class representing individual quiz questions.
* `QuizDao.java` - Data Access Object layer handling all SQL queries (fetching questions & recording scores).
* `QuizPortal.java` - Main controller handling the command-line user interface, menus, and game loop.

---

## ⚙️ Prerequisites & Setup

1. **Java Development Kit (JDK 8 or higher)**
2. **MySQL Server**
3. **MySQL Connector/J JAR** (Added to your Eclipse Build Path)

### Database Schema Setup
Run the following SQL script in your MySQL Workbench or Command Line:
```sql
CREATE DATABASE quiz_portal_db;
USE quiz_portal_db;

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prompt TEXT NOT NULL,
    option1 VARCHAR(255) NOT NULL,
    option2 VARCHAR(255) NOT NULL,
    option3 VARCHAR(255) NOT NULL,
    option4 VARCHAR(255) NOT NULL,
    correct_answer INT NOT NULL
);

CREATE TABLE scores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    score INT NOT NULL,
    total_questions INT NOT NULL,
    percentage DECIMAL(5,2) NOT NULL,
    grade VARCHAR(20) NOT NULL,
    attempt_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
