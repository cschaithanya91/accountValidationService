# Bank Account Validation App

## Overview
This is a Spring Boot project which contains REST API to validate back account details. This service doen't store any data but sends validation requests to other data providers, aggregates the response from data providers and returs it to the client.


### Prerequisites

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.account.validation.AccountValidationServiceApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

- To run Unit Tests use the following command

```shell
mvn clean test
```

## Swagger
- Swagger is already configured in this project in `SwaggerConfiguration.java`.
- The Swagger UI can be seen at [http://localhost:9191/swagger-ui.html](http://localhost:9191/swagger-ui.html)
- You can try the REST API directly from the Swagger interface!


## Features showcase
This project has REST API with following features:

- Accepts bank account details as request and validates the details
- It sends the request to other account data providers, aggregates the result
- If the providers list in the request is empty then all the providers configured in the application are used to validate the data
- Data providers are configurable in the application and are set as properties
- Application also demonstrates how the data providers can be configured for different environments 


## Author
- Chaithanya CS
