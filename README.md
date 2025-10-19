<!-- Banner -->
<div align="center" style="margin-bottom: 24px;">
  <img src="https://img.shields.io/badge/SMT%20Server-Spring%20Boot%20%7C%20MongoDB%20%7C%20JWT-000?style=for-the-badge&logo=springboot" alt="SMT Server" width="420" style="border-radius:20px;"/>
  <h1 style="margin-top: 16px;">SMT Server - RESTful API</h1>
  <p style="font-size: 1.2em; max-width: 700px; margin: 0 auto;">
    A RESTful API for managing common entities of a college campus. <br>
    <a href="https://tavinhossaur.github.io/smt-server" style="display:inline-block; padding:6px 15px; background:#6DB33F; color:#fff; border-radius:10px; margin-top: 15px; text-decoration:none; font-size: 14px; font-weight:bold;">API Documentation</a>
  </p>
  <br>
  <b>This project provides a server for the SMT (Sistema de Monitoramento de Tutores) mobile app.</b> <br>
  <a href="https://github.com/StringKaori/SMT-Sistema_de_Monitoramento_de_Tutores" style="display:inline-block; padding:6px 15px; background:#6DB33F; color:#fff; border-radius:10px; margin-top: 15px; text-decoration:none; font-size: 14px; font-weight:bold;">Mobile App Repository by StringKaori</a>
  <br>
</div>

---

## <img src="https://img.icons8.com/ios-filled/32/6DB33F/info.png" width="20"/> About The Project <hr style="visibility: hidden;">

<blockquote style="border-left:4px solid #6DB33F; padding:12px 18px;">
  The SMT Server is designed to support the academic environment by providing a centralized system for managing professors, classrooms, courses, disciplines, and events. It features JWT-based authentication, role-based access control (Admin/User), and a dashboard for real-time academic data. The API is documented with Swagger and follows clean architecture and SOLID principles.
</blockquote>

---

## <img src="https://img.icons8.com/ios-filled/32/6DB33F/settings.png" width="20"/> Built With <hr style="visibility: hidden;">

<table>
  <tr>
    <td><img src="https://img.shields.io/badge/Java-21-000?&logo=java"/></td>
    <td>Java 21</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/Spring%20Boot-3.5.3-000?&logo=springboot"/></td>
    <td>Spring Boot 3.5.3</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/Spring%20Security-000?&logo=springsecurity"/></td>
    <td>Spring Security</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/MongoDB-000?&logo=mongodb"/></td>
    <td>MongoDB</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/JWT-000?&logo=jwt"/></td>
    <td>JWT Authentication</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/Maven-3.9.6-000?&logo=apachemaven"/></td>
    <td>Maven</td>
  </tr>
</table>

---

## <img src="https://img.icons8.com/ios-filled/32/6DB33F/rocket.png" width="20"/> Getting Started <hr style="visibility: hidden;">

### Prerequisites

<ul>
  <li>Java 21+</li>
  <li>MongoDB</li>
  <li>Maven</li>
</ul>

### Installation

<ol>
  <li><b>Clone the repository:</b>
    <pre><code>git clone https://github.com/tavinhossaur/smt-server.git
cd smt-server</code></pre>
  </li>
  <li><b>Install dependencies:</b>
    <pre><code>./mvnw clean install</code></pre>
  </li>
</ol>

### Configuration

Note that there are different application properties profiles examples in <code>src/main/resources</code>.

<ol>
  <li><b>Copy the example properties file:</b>
    <pre><code>cp src/main/resources/application.properties.example src/main/resources/application.properties</code></pre>
  </li>
  <li><b>Edit <code>src/main/resources/application.properties</code> with your environment values:</b>
    <ul>
      <li><code>spring.data.mongodb.uri</code>: MongoDB connection string</li>
      <li><code>security.jwt.secret-key</code>: Secure random string for JWT</li>
      <li><code>security.jwt.expiration-time</code>: JWT expiration in ms</li>
      <li><code>system.user.default-password</code>: Default password for new users</li>
      <li><code>system.database.seeder.enabled</code>: Run the database seeder (this property is only on application-dev.properties)</li>
    </ul>
  </li>
</ol>

## 🚀 Running the Application

This project can be executed in two main environments: **development (Swarm)** and **production (Compose)**.\
Both use Docker and environment variables defined in the `.env` file.

> The development stack uses Docker Swarm to mimic a distributed environment, while production uses a simpler Compose setup suitable for deployment on a single host or server.

## 🧑‍💻 Development Environment (Docker Swarm)

The **development environment** uses Docker Swarm to enable service replication and monitoring via Visualizer.

### 1️⃣ Build the application image

Before deploying the stack, build the application image locally:

```bash
docker build -t smt/server:dev . --no-cache
```

### 2️⃣ Initialize Docker Swarm

If Swarm is not initialized yet:

```bash
docker swarm init --advertise-addr eth0
```

### 3️⃣ Deploy the stack

Use the `docker-compose.dev.yml` file to deploy all services (MongoDB, API, Nginx, Mongo Express, and Visualizer):

```bash
docker stack deploy -c docker-compose.dev.yml smt-stack --detach=false
```

### 4️⃣ Check service status

To verify if everything is running correctly:

```bash
docker service ls
```

### 5️⃣ Stop and remove the stack

When finished, remove all running services:

```bash
docker stack rm smt-stack
```

## 🌐 Production Environment (Docker Compose)

The **production environment** uses a simpler setup that runs via Docker Compose.

### 1️⃣ Start the containers

Run the following command:

```bash
docker compose -f docker-compose.prod.yml up -d
```

This will create:

- `nginx` (reverse proxy)
- `smt-server` (Spring Boot application)

### 2️⃣ View logs

```bash
docker compose -f docker-compose.prod.yml logs -f
```

### 3️⃣ Stop the containers

```bash
docker compose -f docker-compose.prod.yml down
```

## 🧠 Useful Tips

- To check the MongoDB or container status:

  ```bash
  docker ps
  docker logs <container-name>
  ```

- Access **Mongo Express** at:\
  🔗 [http://localhost:8081](http://localhost:8081)

- Access **Visualizer (Swarm Dashboard)** at:\
  🔗 [http://localhost:8082](http://localhost:8082)

- Main API available at:\
  🔗 [http://localhost:8080](http://localhost:8080)\
  (or via Nginx proxy at [http://localhost](http://localhost))

---

## <img src="https://img.icons8.com/ios-filled/24/6DB33F/play.png" width="20"/> Usage <hr style="visibility: hidden;">

### Authentication

<ul>
  <li>Obtain a JWT token via <code>POST /api/v1/login</code> with <code>{ "email": "...", "password": "..." }</code>.</li>
  <li>Use the token in the <code>Authorization: Bearer &lt;token&gt;</code> header for protected endpoints.</li>
</ul>

### Example Endpoints

<table>
  <tr><th>Type</th><th>Endpoint</th><th>Access</th></tr>
  <tr><td>Admin</td><td><code>/api/v1/admin/*</code></td><td>Admin only</td></tr>
  <tr><td>Profile</td><td><code>/api/v1/profile/*</code></td><td>Authenticated user</td></tr>
  <tr><td>Dashboard</td><td><code>/api/v1/dashboard/*</code></td><td>Authenticated user</td></tr>
  <tr><td>Login</td><td><code>/api/v1/login</code></td><td>Public</td></tr>
</table>

<div style="margin: 18px 0;">
  <a href="https://tavinhossaur.github.io/smt-server" style="display:inline-block; padding:10px 22px; background:#6DB33F; color:#fff; border-radius:6px; text-decoration:none; font-weight:bold;">View Full API Docs</a>
</div>

---

## <img src="https://img.icons8.com/ios-filled/24/6DB33F/folder-invoices--v1.png" width="20"/> Project Structure <hr style="visibility: hidden;">

<pre>
src/main/java/com/ifsp/tavinho/smt_backend/
├── domain/         # Entities, DTOs, Enums, Repositories, Use Cases
├── application/    # Services and Interactors (business logic)
├── infra/          # Controllers, Configurations, Middlewares, Persistence
├── shared/         # Utilities, Error Handling, Response Models
└── SmtServerApplication.java
</pre>

---

## <img src="https://img.icons8.com/ios-filled/32/6DB33F/api-settings.png" width="20"/> API Overview <hr style="visibility: hidden;">

<ul>
  <li><b>Authentication & Authorization:</b> JWT, Admin/User roles</li>
  <li><b>Entities:</b> Professors, Classrooms, Courses, Disciplines, Events, Users</li>
  <li><b>User Features:</b> Profile, password, photo, favorites</li>
  <li><b>Dashboard:</b> Professor schedules, classroom availability, course info</li>
  <li><b>Admin:</b> Full CRUD for all entities</li>
  <li><b>Error Handling:</b> Consistent API responses, global exception handler</li>
</ul>

---

## <img src="https://img.icons8.com/ios-filled/32/6DB33F/copyright.png" width="20"/> License <hr style="visibility: hidden;">

Distributed under the MIT License. See <code>LICENSE</code> for more information.

---
