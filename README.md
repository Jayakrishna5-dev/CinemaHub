# 🎬 CinemaHub – IMDb Clone Backend API

A production-inspired IMDb Clone backend built using **Spring Boot**.

CinemaHub is a RESTful backend application that allows users to browse movies, rate and review movies, manage personal watchlists, favorite movies & personal information, and enables an internal content management team to manage movie information securely using Role-Based Access Control (RBAC).

---

# ✨ Project Overview

CinemaHub replicates the core functionality of IMDb with a focus on backend architecture, clean API design, and production-ready coding practices.

The project demonstrates:

- JWT Authentication
- Role-Based Authorization
- RESTful API Design
- Layered Architecture
- DTO Pattern
- Global Exception Handling
- Database Relationship Design
- Clean Package Structure
- Validation
- Pagination

---

# 🚀 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- JWT Authentication
- Maven
- Swagger / OpenAPI
- Lombok
- Validation

---

# 👥 User Roles

## 🌍 Public Visitor

- Browse Movies
- View Movie Details
- Search Movies
- View Genres
- View Cast & Crew
- View Reviews

---

## 👤 Registered User

- Login
- Rate Movies
- Write Reviews
- Manage Watchlists
- Add Favourite Movies
- Update Profile

---

## 🎬 Content Manager

- Manage Movies
- Manage Genres
- Manage Movie Roles
- Manage Movie Person details

---

## 🛡 Super Admin

- Generate Employee Invitations
- Update Employees information

---

# ✨ Features

## Authentication

- JWT Login
- User Registration
- Employee Registration using Invitation Code
- Role Based Authorization

## Movie Management

- Movie CRUD
- Genre Management
- Cast&Crew Management
- Pagination

## Review System

- Add Review
- Update Review
- Delete Review
- View Reviews
- Pagination

## Rating System

- Rate Movies
- Update Ratings
- Delete Ratings

## Watchlist

- Create Watchlist
- Add Movies
- Remove Movies
- Delete Watchlist

## Favorites

- Add Favourite Movies
- Remove Favourite Movies

## Exception Handling

- Global Exception Handler
- Custom Exceptions
- Validation Errors

---

# 🗄 Database Design

## Entities

- EmpInvitationEntity
- FavoriteEntity
- GenreEntity
- MovieEntity
- MovieGenreEntity
- MoviePersonEntity
- PersonEntity
- PersonRoleEntity
- RatingEntity
- ReviewEntity
- RoleEntity
- UserEntity
- WatchlistEntity
- WatchlistMoviesEntity

---

## Entity Relationship Diagram

<img width="1536" height="1024" alt="Entity Relationship ER diagram" src="https://github.com/user-attachments/assets/91951fe8-a142-4596-8ab6-0ac183c71816" />


---

# 📂 Project Structure

```
src/main
 └── cinema
      ├── auth
	  |		├── config
	  |		├── service
      ├── common
	  |		├── auditVariables
	  |		├── bootstrap
      ├── empInvitation
	  |		├── controller
	  |		├── empInvitationDto
	  |		├── entity
	  |		├── mapper
	  |		├── repository
	  |		├── service	  
      ├── exception
	  |		├── customException
	  |		├── ErrorResponse.java
	  |		├── GlobalExceptionHandler.java
      ├── favorite
	  |		├── controller
	  |		├── dto
	  |		├── entity
	  |		├── repository
	  |		├── service	
      ├── genre
	  |		├── controller
	  |		├── dto
	  |		├── entity
	  |		├── repository
	  |		├── service	
      ├── movie
	  |		├── controller
	  |		├── dto
	  |		├── entity
	  |		├── repository
	  |		├── service	
	  |		├── mapper	
      ├── movieGenre
	  |		├── controller
	  |		├── dto
	  |		├── entity
	  |		├── repository
	  |		├── service	
      ├── moviePerson
	  |		├── controller
	  |		├── dto
	  |		├── entity
	  |		├── repository
	  |		├── service	
      ├── person
	  |		├── controller
	  |		├── dto
	  |		├── entity
	  |		├── repository
	  |		├── service	
      ├── personRole
	  |		├── controller
	  |		├── dto
	  |		├── entity
	  |		├── repository
	  |		├── service	
      ├── rating
	  |		├── controller
	  |		├── dto
	  |		├── entity
	  |		├── repository
	  |		├── service	
      ├── review
	  |		├── controller
	  |		├── dto
	  |		├── entity
	  |		├── repository
	  |		├── service	
      ├── role
	  |		├── controller
	  |		├── roleEntity
	  |		├── repository
	  |		├── service	
      ├── user
	  |		├── controller
	  |		├── dto
	  |		├── userEntity
	  |		├── repository
	  |		├── service	
      ├── Watchlist
	  |		├── controller
	  |		├── dto
	  |		├── entity
	  |		├── repository
	  |		├── service	
      ├── watchlistMovies
	  |		├── controller
	  |		├── dto
	  |		├── entity
	  |		├── repository
	  |		├── service	
	  
```
---

# 📑 API Documentation

Swagger UI

http://localhost:8080/swagger-ui/index.html

The project exposes RESTful APIs documented using Swagger/OpenAPI.

<img width="457" height="133" alt="image" src="https://github.com/user-attachments/assets/6fa6f70c-6fe2-43a7-a707-1af5fea96dbb" />
<img width="495" height="275" alt="image" src="https://github.com/user-attachments/assets/4f45c20e-fb3a-4c62-8ccf-e8e6c4bde988" />
<img width="536" height="202" alt="image" src="https://github.com/user-attachments/assets/ba0c4120-a047-48cc-b1c8-6d5438dec30c" />
<img width="557" height="263" alt="image" src="https://github.com/user-attachments/assets/697521c1-46bc-4ad7-8f46-c33275845813" />
<img width="552" height="347" alt="image" src="https://github.com/user-attachments/assets/085be78d-d2fb-4cf0-afa2-c12ab15e2a3f" />
<img width="415" height="202" alt="image" src="https://github.com/user-attachments/assets/19958407-da9d-4bc4-8e58-1bbe6093afe6" />
<img width="455" height="120" alt="image" src="https://github.com/user-attachments/assets/d23d015a-4fc6-4a3a-901a-64ec95d31b88" />
<img width="503" height="273" alt="image" src="https://github.com/user-attachments/assets/f182a8c9-a426-4f99-a4df-7d9fd146f880" />
<img width="462" height="286" alt="image" src="https://github.com/user-attachments/assets/4a9ad48c-bf37-4dc1-97b2-a62b310f7884" />
<img width="497" height="273" alt="image" src="https://github.com/user-attachments/assets/0fb78a4d-f4c9-444e-91e5-c56015a9d9da" />
<img width="571" height="212" alt="image" src="https://github.com/user-attachments/assets/a00a49c7-4a64-4fad-a37b-cf1e21c21a5d" />
<img width="616" height="215" alt="image" src="https://github.com/user-attachments/assets/b79ae48a-0758-473b-bf31-6250c6cb98e8" />
<img width="535" height="282" alt="image" src="https://github.com/user-attachments/assets/6972f3fa-9e73-4a69-ade4-1bb85f5aec0a" />
<img width="532" height="283" alt="image" src="https://github.com/user-attachments/assets/0a9e7cc0-a3be-49dc-89c7-92235f1de614" />
<img width="570" height="143" alt="image" src="https://github.com/user-attachments/assets/77cb6261-f437-469b-b807-d6bc93055f71" />
<img width="612" height="285" alt="image" src="https://github.com/user-attachments/assets/4ad807cf-34ec-487c-84f9-221ed1fb026f" />
<img width="520" height="353" alt="image" src="https://github.com/user-attachments/assets/0b180569-fa46-45f9-aadf-896176e2873d" />
<img width="635" height="425" alt="image" src="https://github.com/user-attachments/assets/302e6dc9-6421-48ac-a7d7-c929d6bc0d82" />
<img width="888" height="566" alt="image" src="https://github.com/user-attachments/assets/6fed8521-dda1-4c86-8c09-fb2928e0081f" />


---

# 🔐 Security

- JWT Authentication
- Role Based Authorization
- Protected APIs
- Password Encryption using BCrypt

---

# ▶️ Running the Project

## Clone Repository

```bash
git clone https://github.com/Jayakrishna5-dev/CinemaHub
```

## Configure Database

Update the database configuration in:

```properties
src/main/resources/application.properties
```

Configure the following properties:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cinemahub
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Run

```bash
mvn spring-boot:run
```

Swagger

```
http://localhost:8080/swagger-ui/index.html
```

---

# 🧩 Design Patterns Used

- Layered Architecture
- Repository Pattern
- Service Layer Pattern
- DTO Pattern
- Builder Pattern (Lombok)
- Dependency Injection
- Front Controller Pattern (DispatcherServlet)

---

# 🏗 System Design Concepts Implemented

- RESTful API Design
- Role Based Access Control (RBAC)
- JWT Authentication & Authorization
- Database Normalization
- One-to-Many Relationships
- Association Entity instead of Direct Many-to-Many
- Pagination
- Validation
- Global Exception Handling
- Public APIs and CMS APIs separated using Role-Based Access Control
- Layered Modular Architecture
- Request / Response DTO Separation
- Secure Internal Employee Onboarding using Invitation Codes
- Transaction Management
- Clean Package Structure

---

# 👨‍💻 Author

**Jayakrishna**

Spring Boot Backend Developer
