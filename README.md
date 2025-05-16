# Results Management System

This project is a full-stack Results Management System (RMS) designed to streamline academic performance management for educational institutions. It provides a centralized platform for managing student, teacher, course, exam, and result data.

## Project Structure

```
results-management-service/
│
├── frontend/
│   └── results-management-app/
│       ├── public/
│       ├── src/
│       │   ├── components/
│       │   ├── context/
│       │   ├── assets/
│       │   └── index.js
│       ├── package.json
│       └── README.md
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/canvas/rms/
│       │       ├── Controller/
│       │       ├── Service/
│       │       ├── Entity/
│       │       ├── Repository/
│       │       └── RmsApplication.java
│       └── resources/
│           └── application.properties
│
├── pom.xml
└── README.md
```

### Spring Boot (Backend)

- Located in `src/main/java/com/canvas/rms/`
- REST API controllers in `Controller/`
- Business logic in `Service/`
- JPA entities in `Entity/`
- Data access in `Repository/`
- Main entry point: [`RmsApplication.java`](src/main/java/com/canvas/rms/RmsApplication.java)
- Configuration: [`application.properties`](src/main/resources/application.properties)

### React (Frontend)

- Located in `frontend/results-management-app/`
- Components in `src/components/`
- Context for authentication in `src/context/`
- Main entry point: `src/index.js`
- Routing and main app: `src/App.js`

## How to Run the Application

### Prerequisites

- Java 17+
- Maven
- Node.js and npm
- MySQL (or compatible database, configured in `application.properties`)

### 1. Set Up the Database

- Ensure MySQL is running.
- Create a database named `result_management`.
- Update `src/main/resources/application.properties` with your DB username and password if needed.

### 2. Run the Spring Boot Backend

From the project root:

```sh
./mvnw spring-boot:run
```
or
```sh
mvn spring-boot:run
```

The backend will start on [http://localhost:8080](http://localhost:8080).

### 3. Run the React Frontend

Open a new terminal and navigate to the frontend directory:

```sh
cd frontend/results-management-app
npm install
npm start
```

The frontend will start on [http://localhost:3000](http://localhost:3000).

### 4. Access the Application

- Open [http://localhost:3000](http://localhost:3000) in your browser.
- The frontend communicates with the backend via REST APIs.

## Additional Notes

- CORS is configured to allow requests from the frontend to the backend.
- For development, both servers must be running simultaneously.
- See [`frontend/results-management-app/README.md`](frontend/results-management-app/README.md) for more React-specific scripts and info.

---