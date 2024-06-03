# User Balance Service

This project is a Spring Boot application that interacts with Kafka, Zookeeper, and PostgreSQL. The setup and execution are managed using Docker Compose. This README provides instructions for setting up, running the application, and executing tests.

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/antoshaSid/payment-system.git
cd user-balance-service
```

### 2. Running the Application

To start the Docker Compose services and run the Spring Boot application, execute:

```bash
./run_application.sh
```

### 3. Running Functional Tests

To start the Docker Compose services and run the functional tests, execute:

```bash
./run_application.sh -t
```

## Using the API

### API Endpoints

#### 1. Health Check

- **URL:** `http://localhost:8080/api/actuator/health`
- **Method:** `GET`
- **Description:** Checks the health of the application.

### Example cURL Request

```bash
curl http://localhost:8080/api/actuator/health
```

### Response

It returns the status of the application.

```json
{
  "status": "UP"
}
```

#### 2. Set Users Balance

- **URL:** `http://localhost:8080/api/user/balance/batch`
- **Method:** `PUT`
- **Request Body:** `application/json`

```json
{
  "1": 100,
  "2": 200,
  "3": 300
}
```

- **Description:** Updates the balance for users with the provided IDs.

### Example cURL Request

```bash
curl -X PUT http://localhost:8080/api/user/balance/batch -H "Content-Type: application/json" -d '{
  "1": 100,
  "2": 200,
  "3": 300
}'
```

### Response
It returns the execution job batch with the status of each job. Use job IDs to check the status of individual jobs.

```json
{
  "executionJobBatch": [
    {
      "id": 1,
      "status": "IN_QUEUE"
    },
    {
      "id": 2,
      "status": "IN_QUEUE"
    },
    {
      "id": 3,
      "status": "COMPLETED"
    }
  ]
}
```

#### 3. Get Execution Job

- **URL:** `http://localhost:8080/api/execution/jobs/{jobId}`
- **Method:** `GET`
- **Description:** Retrieves the status of the execution job with the provided ID.

### Example cURL Request

```bash
curl http://localhost:8080/api/execution/jobs/3
```

### Response

It returns the status of the execution job.

```json
{
  "id": 3,
  "status": "COMPLETED"
}
```

## Simulation Test Results

### Performance Test Results

**Test Scenario:** Update balance for 100,000 users.

**Results:**

- **Total Time Taken:** 28760 ms

## Stopping Services

To stop the Docker Compose services, run:

```bash
docker-compose down
```

## Possible improvements
1. Add more Kafka Broker instances to make the system more fault-tolerant and high-available (increase replication factor). We can easily scale the Kafka cluster by adding more brokers with Docker Compose.
2. Write native SQL queries to improve the performance of the database operations. Currently, the application updates each user's balance individually. We can use a bulk update query to minimize database interactions.
3. Implement a retry mechanism for failed execution jobs. For instance, we can implement Dead letter queue (DLQ) to store failed jobs and retry them later.
4. Increase the test coverage by adding more unit and integration tests to ensure all parts of the application are thoroughly tested.
5. Implement more robust error handling mechanisms to capture and manage different types of errors. For example, we can expand a global exception handler to avoid returning stack traces to the client.


