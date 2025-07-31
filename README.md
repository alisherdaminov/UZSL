UZSL - Uzbekistan Super League - Management System
A modern backend system built with Java 17, Spring Boot, and Domain-Driven Design (DDD) for managing Uzbekistan’s national football league — players, matches, clubs, and live statistics.

- Features at a Glance
// Admin & user roles with JWT authentication
⚽ Manage player statistics: goals, assists, penalties, own goals
🏟 Track match results and historical fixtures
📊 Auto-update league standings after each match
🔍 REST API with full Swagger documentation
📈 Extendable for real-time updates & web integrations
   

- Project Structure (Clean Architecture)
uzsl/
├── domain/           → Pure business logic (Entities, Repositories, Events)
├── application/      → Use cases, DTOs, Mappers, Services
├── infrastructure/   → JWT, Swagger, Exception Handling, Security, Adapters
├── presentation/     → REST Controllers (input layer)
└── shared/           → Common enums, error codes, and utils

- Tech Stack: Java 17, Spring Boot 3, Spring Security, Spring Data JPA,DDD, Ports & Adapters, Clean Architecture, PostgreSQL, WT + Role-based access (RBAC), Swagger / OpenAPI
- Key Modules
player – Tracks individual player performance
club – Manages club profiles, squads, logos
match – Stores match details, results, and timestamps
standings – Auto-calculates points, wins/draws/losses
auth – User & admin login, roles, secure endpoints

- Role Access Capabilities
ADMIN - Full CRUD: players, clubs, matches, stats, users
USER - Read-only access: view players, standings, matches

- Getting Started
Swagger UI: http://localhost:8080/swagger-ui/index.html
PostgreSQL default port: 5432
Java 17 required

- Contact
Email: alisherdaminov135@gmail.com
Telegram: @mr_daminov
LinkedIn: https://www.linkedin.com/in/alisher-daminov-997019231/
GitHub: github.com/alisherdaminov/uzsl


![Image](https://github.com/user-attachments/assets/34787765-c4a5-483e-b456-1176e5e6ae7a)
![Image](https://github.com/user-attachments/assets/a66026d9-9a14-4ccb-825b-a2a7d7892ed5)
![Image](https://github.com/user-attachments/assets/03093914-1c41-47ca-86a0-cd0785efcec7)
![Image](https://github.com/user-attachments/assets/e5b2deab-f78b-4d2a-bf2e-a1f62729d4c4)
