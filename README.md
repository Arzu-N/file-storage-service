File Storage Service
This project is a Spring Boot microservice designed for the secure storage and management of files. It utilizes API key-based authentication for file uploading, downloading, and deletion.

🚀 Key Technologies
Java 17+ & Spring Boot 3.x

PostgreSQL (Relational Database)

MinIO (Object Storage Service)

Docker & Docker Compose

🛠️ How to Run
The project is fully containerized with Docker Compose. Open your terminal in the project's root directory and run the following command:

Bash
docker compose up -d
This command will automatically set up and start the PostgreSQL, MinIO, and application containers.

🔑 API Endpoints
1. Administrative Operations (Admin)
Create API Key: POST /api-keys/create

Description: Generates a new API key for the system.

Header: X-ADMIN-SECRET (Must match the ADMIN_SECRET value defined in your Docker configuration).

2. File Operations
All of the following endpoints require authentication via the X-API-Key header.

Upload File: POST /files

Description: Uploads a file in multipart/form-data format.

Download File: GET /files/{id}

Description: Downloads the file with the specified ID in application/octet-stream format.

Delete File: DELETE /files/{id}

Description: Deletes the file with the specified ID from the system.

⚙️ Configuration
PostgreSQL: Port 5432

MinIO: Port 9000 (Console: 9001)

Timezone: Set to Asia/Baku to ensure accurate timestamps for all operations.

📝 Troubleshooting
If you make changes to your code, you can rebuild and restart the containers with the following command:

Bash
docker compose up -d --build
