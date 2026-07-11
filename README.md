# REST API Automation Framework

Production-grade API testing framework built with REST Assured, TestNG, and Allure.

## Tech Stack
- Java 17
- REST Assured 5.4.0
- TestNG 7.9.0
- Jackson 2.16.1
- Allure Reports
- JSON Schema Validation

## Framework Architecture
src
├── main/java/com/api/automation
│   ├── client       → ApiClient (GET, POST, PUT, DELETE)
│   ├── config       → ConfigManager (Singleton)
│   ├── models       → User, LoginRequest (POJOs)
│   └── utils        → SchemaValidator
└── test/java/com/api/automation
├── base         → BaseTest
└── tests        → GetUserTest, CreateUserTest

## Key Features
- Reusable ApiClient with RequestSpecBuilder
- API Key authentication
- JSON Schema Validation
- Allure reporting with request/response logs
- TestNG parallel execution

## How To Run
mvn clean test

## API Under Test
https://reqres.in