# SkillSwap - Community Skill Exchange Platform

A complete REST API backend for a community skill exchange platform built with Spring Boot 3.2.0 and PostgreSQL.

## Features

- **User Authentication**: JWT-based authentication with register/login endpoints
- **Skill Management**: Master list of skills by category (MUSIC, TECH, COOKING, LANGUAGE, FITNESS, ART, OTHER)
- **User Skills**: Users can add skills they want to teach or learn with proficiency levels
- **Skill Matching**: Intelligent matching system to find users with complementary skills
- **Skill Exchanges**: Request/accept skill exchanges between users
- **Session Management**: Schedule and manage teaching sessions for skill exchanges
- **Rating System**: Rate users after completing sessions with 1-5 star ratings and reviews

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security 6.x** (JWT Authentication)
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Lombok**
- **jjwt 0.11.5** (JWT Token Provider)

## Database Configuration

```properties
Database: skillswap_db
Username: postgres
Password: postgres
Host: localhost
Port: 5432
```

## Project Structure

```
com.sashank.skillswap
├── config/                 # Spring configuration classes
├── controller/            # REST API endpoints
├── dto/
│   ├── request/          # Request DTOs
│   └── response/         # Response DTOs
├── entity/               # JPA entities
├── enums/                # Enumerations
├── exception/            # Custom exceptions & global handler
├── repository/           # Data access layer
├── security/             # JWT & Security related
├── service/              # Business logic (interfaces)
│   └── impl/             # Service implementations
└── util/                 # Utility classes (DTOMapper)
```

## Key Entities

### User
- Basic user information (name, email, password)
- Profile data (bio, city)
- Role (USER, ADMIN)

### Skill
- Master list of all available skills
- Category, name, description

### UserSkill
- Associates users with skills (TEACH or LEARN)
- Proficiency level (BEGINNER, INTERMEDIATE, ADVANCED)

### SkillExchange
- Exchange request between two users
- Offered skill (what requester teaches)
- Wanted skill (what requester wants to learn)
- Status (PENDING, ACCEPTED, REJECTED, COMPLETED)

### Session
- Individual teaching session within an exchange
- DateTime, duration, mode (ONLINE/OFFLINE)
- Meeting link or location

### Rating
- User rating after session completion
- 1-5 star rating with optional review

## Enums

- **SkillLevel**: BEGINNER, INTERMEDIATE, ADVANCED
- **SkillCategory**: MUSIC, TECH, COOKING, LANGUAGE, FITNESS, ART, OTHER
- **SkillType**: TEACH, LEARN
- **ExchangeStatus**: PENDING, ACCEPTED, REJECTED, COMPLETED
- **SessionStatus**: SCHEDULED, COMPLETED, CANCELLED
- **SessionMode**: ONLINE, OFFLINE
- **UserRole**: USER, ADMIN

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login

### Profile
- `GET /api/profile/me` - Get current user profile
- `PUT /api/profile/me` - Update profile
- `POST /api/profile/skills` - Add a skill
- `GET /api/profile/skills` - Get user skills
- `DELETE /api/profile/skills/{userSkillId}` - Remove a skill

### Skills
- `GET /api/skills` - Get all skills
- `GET /api/skills?category=MUSIC` - Filter by category
- `GET /api/skills/categories` - Get all categories
- `POST /api/skills` - Create a new skill

### Matching
- `GET /api/matches` - Get all matching users
- `GET /api/matches/mutual` - Get mutual matches only
- `GET /api/matches/{userId}` - Check if specific user matches

### Skill Exchanges
- `POST /api/exchanges` - Create exchange request
- `GET /api/exchanges` - Get all exchanges (sent & received)
- `GET /api/exchanges/sent` - Get sent exchange requests
- `GET /api/exchanges/received` - Get received exchange requests
- `GET /api/exchanges/{id}` - Get exchange details
- `PUT /api/exchanges/{id}/accept` - Accept exchange
- `PUT /api/exchanges/{id}/reject` - Reject exchange
- `PUT /api/exchanges/{id}/complete` - Mark as completed

### Sessions
- `POST /api/exchanges/{exchangeId}/sessions` - Schedule a session
- `GET /api/exchanges/{exchangeId}/sessions` - Get sessions in exchange
- `GET /api/sessions/my-sessions` - Get upcoming sessions
- `GET /api/sessions/{id}` - Get session details
- `PUT /api/sessions/{id}/complete` - Complete session
- `PUT /api/sessions/{id}/cancel` - Cancel session

### Ratings
- `POST /api/sessions/{sessionId}/ratings` - Rate a session
- `GET /api/users/{userId}/ratings` - Get user's ratings
- `GET /api/users/{userId}/rating-summary` - Get rating summary
- `GET /api/profile/me/ratings` - Get my received ratings

## Security

- JWT token-based authentication
- All endpoints except `/api/auth/**` are protected
- Token format: `Authorization: Bearer <token>`
- Token expiration: 24 hours (configurable)

## Setup Instructions

### 1. Create PostgreSQL Database

```sql
CREATE DATABASE skillswap_db;
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Refresh IDE Dependencies

If using IntelliJ IDEA:
- Right-click on `pom.xml` → Maven → Reload Project

## Configuration

Edit `src/main/resources/application.properties` to customize:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/skillswap_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JWT
jwt.secret=your-secret-key-change-this-in-production
jwt.expiration=86400000

# Server
server.port=8080
```

## Sample Usage

### 1. Register User
```bash
POST /api/auth/register
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

### 2. Login
```bash
POST /api/auth/login
{
  "email": "john@example.com",
  "password": "password123"
}
```

### 3. Add Skills to Profile
```bash
POST /api/profile/skills
Authorization: Bearer <token>
{
  "skillId": 1,
  "type": "TEACH",
  "level": "ADVANCED"
}
```

### 4. Create Skill Exchange
```bash
POST /api/exchanges
Authorization: Bearer <token>
{
  "receiverId": 2,
  "offeredSkillId": 1,
  "wantedSkillId": 3,
  "message": "I'd like to trade"
}
```

## Error Handling

All errors follow a consistent format:

```json
{
  "timestamp": "2024-04-05T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Error details",
  "path": "/api/endpoint"
}
```

HTTP Status Codes:
- `200` - OK
- `201` - Created
- `204` - No Content
- `400` - Bad Request
- `401` - Unauthorized
- `403` - Forbidden
- `404` - Not Found
- `409` - Conflict

## Validation Rules

- Email must be unique and valid format
- Cannot send exchange to yourself
- Can only rate completed sessions
- Cannot rate same session twice
- Cannot accept/reject non-pending exchanges
- Only exchange participants can schedule sessions

## Development Notes

- JPA entities use Lombok for getters/setters
- DTOs use builder pattern for object creation
- Services use dependency injection via @Autowired
- JWT tokens extracted from Authorization header
- User context available via @RequestAttribute("userId")

## Future Enhancements

- Email notifications
- Skill reviews/testimonials
- Session rescheduling
- Skills endorsements
- User search/filtering
- Advanced matching algorithm
- Real-time chat during sessions
- Payment integration

## License

This project is for educational purposes.

# skillswap
