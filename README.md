JOB TRACKER DASHBOARD

A full-stack Job Application Tracker built with Spring Boot, Java, and JavaScript that allows users to manage and track job applications through a simple dashboard.
The application provides a REST API backend and a lightweight frontend interface that communicates with the backend using the Fetch API.

FEATURES
. Create new job applications
. View all job applications
. Update job application details
. Delete job applications
. Color-coded job status indicators
. Dashboard UI for managing applications

Supported statuses include:
. Applied
. Interview
. Offer
. Rejected

Tech Stack
. Backend
. Java
. Spring Boot
. Spring Data JPA
. Maven
. H2 Database

Frontend
. HTML
. CSS
. JavaScript
. Fetch API

Tools
. IntelliJ IDEA
. Git & GitHub
. Postman (API testing)

PROJECT STRUCTURE
job-tracker
│
├── src/main/java/com/jobtracker/job_tracker
│   ├── controller
│   │   └── JobApplicationController
│   │
│   ├── service
│   │   └── JobApplicationService
│   │
│   ├── repository
│   │   └── JobApplicationRepository
│   │
│   ├── model
│   │   └── JobApplication
│   │
│   ├── dto
│   │
│   └── config
│
├── static
│   ├── index.html
│   ├── style.css
│   └── script.js
│
└── pom.xml

REST API ENDPOINTS
| Method | Endpoint         | Description       |
| ------ | ---------------- | ----------------- |
| GET    | `/api/jobs`      | Retrieve all jobs |
| POST   | `/api/jobs`      | Create new job    |
| PUT    | `/api/jobs/{id}` | Update job        |
| DELETE | `/api/jobs/{id}` | Delete job        |

Example API Request
Create Job Application
POST /api/jobs
<>JSON
{
  "company": "Google",
  "position": "Backend Developer",
  "status": "Applied"
}

Running the Project
1 Start the Backend
Run the Spring Boot application.

Using Maven:
mvn spring-boot:run

The backend will run at:
http://localhost:8080

2 Open the Frontend
Navigate to the frontend folder and open:
index.html

You can also run it with a local development server.

DASHBOARD
The dashboard allows users to:
. Add new job applications
. Edit existing entries
. Delete job applications
. View job statuses with color indicators

LEARNING GOALS
This project was built to practice:
. REST API development with Spring Boot
. CRUD operations using Spring Data JPA
. Dependency Injection
. Frontend-backend communication using Fetch API
. Building a simple full-stack application

FUTURE IMPROVEMENTS
Potential improvements include:
. User authentication
. Filtering jobs by status
. Pagination
. Deployment to a cloud platform
. React frontend

AUTHOR
Kamogelo Meno
