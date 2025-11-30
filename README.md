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


## 🚀 Running the Application

This project can be executed in two main environments: **development (Swarm)** and **production (Compose)**.\
Both use Docker and environment variables defined in the `.env` file.

> The development stack uses Docker Swarm to mimic a distributed environment, while production uses a simpler Compose setup suitable for deployment on a single host or server.

## 🧑‍💻 Development Environment (Docker Swarm)

The **development environment** uses Docker Swarm to enable service replication.

### 1️⃣ Build the application image

Before deploying the stack, build the application image locally:

```bash
docker build -t smt/server:dev . --no-cache
```

### 1️⃣ Initialize Docker Swarm environment

If Swarm is not initialized yet:

```bash
docker swarm init
```

We use three different workers, each of them will have one nginx replica and three replicas of the smt-server.

to get the `MANAGER_IP` use:

```bash
docker node inspect self --format '{{.Status.Addr}}'
```

```bash
docker run -d --privileged --name worker1 --hostname worker1 -p 80:80 --network bridge docker:dind --insecure-registry <MANAGER_IP>:5000

docker run -d --privileged --name worker2 --hostname worker2 -p 81:80 --network bridge docker:dind --insecure-registry <MANAGER_IP>:5000

docker run -d --privileged --name worker3 --hostname worker3 -p 82:80 --network bridge docker:dind --insecure-registry <MANAGER_IP>:5000
```

Now we link the workers to the swarm manager

to get the `SWARM_TOKEN` use:

```bash
docker swarm join-token worker -q
```

```bash
docker exec worker1 docker swarm join --token <SWARM_TOKEN> <MANAGER_IP>:2377

docker exec worker2 docker swarm join --token <SWARM_TOKEN> <MANAGER_IP>:2377

docker exec worker3 docker swarm join --token <SWARM_TOKEN> <MANAGER_IP>:2377
```

Now you should have 1 manager and 3 workers listed

```bash
docker node ls
```

### 2️⃣ Build the smt-server image to a local registry

Create the registry:

```bash
docker service create --name registry --publish 5000:5000 registry:2
```

Build the image:

```bash
docker build -t localhost:5000/smt-server:dev . --no-cache
```

Push to the local registry

```bash
docker push localhost:5000/smt-server:dev
```

### 3️⃣ Deploy the stack

Use the `docker-compose.dev.yml` file to deploy the services MongoDB, API and Nginx:

```bash
docker stack deploy -c ./docker-compose.dev.yml smt-stack --detach=false
```

Use the `docker-compose.dev.monitor.yml` file to deploy the services Mongo Express and Docker Visualizer:

```bash
docker compose -f ./docker-compose.dev.monitor.yml up -d
```

### 4️⃣ Check service status

To verify if everything is running correctly:

```bash
docker service ls
```

### 5️⃣ Stop and remove the stack

When finished, remove all running services:

```bash
# Stack
docker stack rm smt-stack

# Monitoring containers
docker compose -f docker-compose.dev.monitor.yml down -v
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

- To check status of the nodes, services and containers

  ```bash
  docker node ls
  docker service ls
  docker stack ps smt
  docker ps
  ```

- To get logs of a service

  ```bash
  docker service logs <SERVICE-NAME>
  ```

- Access **Mongo Express** at:\
  🔗 [http://localhost:8081](http://localhost:8081)

- Access **Docker Visualizer** at:\
  🔗 [http://localhost:8082](http://localhost:8082)

- Main API available via Nginx at:\
  🔗 [http://localhost/api/v1/health](http://localhost/api/v1/health)

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
