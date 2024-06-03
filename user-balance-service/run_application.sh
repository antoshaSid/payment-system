#!/bin/bash

# Function to check if a service is up
check_service() {
  local service_name=$1
  local service_port=$2
  local retries=5
  local wait=10

  echo "Waiting for $service_name to be available at port $service_port..."

  until [ $retries -eq 0 ]; do
    nc -z localhost $service_port && break
    retries=$((retries - 1))
    echo "Service $service_name not available yet. Retrying in $wait seconds..."
    sleep $wait
  done

  if [ $retries -eq 0 ]; then
    echo "Service $service_name not available after multiple retries. Exiting..."
    exit 1
  fi

  echo "Service $service_name is up and running."
}

# Start Docker Compose services
docker-compose up -d

# Wait for Zookeeper, Kafka, and Postgres to be available
check_service "Zookeeper" 2181
check_service "Kafka" 9092
check_service "Postgres" 5433

# Parse the command line option
if [ "$1" == "-t" ]; then
  # Run Maven tests
  echo "Running Maven tests..."
  mvn test
  echo "Maven tests completed."
else
  # Run Spring Boot application
  echo "Starting Spring Boot application..."
  mvn spring-boot:run
  echo "Spring Boot application started."
fi
