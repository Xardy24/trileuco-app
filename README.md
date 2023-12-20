# Trileuco App

Trileuco App is a Spring Boot application that serves as a proxy for the SWAPI (Star Wars API) to provide information about Star Wars characters.

### Built With

Spring Boot
Maven
Docker

## Authors

Cesar Rios

## Getting Started

These instructions will help you set up and run the Trileuco App on your local machine.

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven (for building the project)
- Docker (optional, for running the application in a container)

### Installing

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Xardy24/trileuco-app.git
   cd trileuco-app

2. **Build the project:**
   ```bash
   mvn clean install

3. **Run with Maven:**
   ```bash
   mvn spring-boot:run
   
4. **Run with Docker:**
   ```bash
   docker build -t trileuco-app .
   docker run -p 8080:8080 trileuco-app

The application will be accessible at http://localhost:8080/swapi-proxy/person-info?name=Luke%20Skywalker.

### API Endpoints
Get Person Info by Name
    http
    GET /swapi-proxy/person-info?name={characterName}

Retrieves information about a Star Wars character by name.
Example:
   ```bash
   curl "http://localhost:8080/swapi-proxy/person-info?name=Luke%20Skywalker"

