# BestRoute
Help to find the cheaper route for your trip.

# Requirements

- Java 11
- Maven 3.0.4+

# How to run
Clone the repository
```sh
git clone https://github.com/Fuerback/bestroute.git
```

Navigate to the folder and build the project
```sh
cd bestroute
mvn clean install
```

Run the project and pass the CSV file path as argument
```sh
mvn spring-boot:run -Dspring-boot.run.arguments=--file=/home/folder/routes.csv
```

#Packages and Files Structure

```sh
├── resources
│    └── routes.csv
├── src
│    ├── main
│        ├── java
│        │   └── com
│        │       └── bexstech
│        │           └── exam
│        │               ├── controller
│        │               │   └── RouteController.java
│        │               ├── dto
│        │               │   ├── RouteDTO.java
│        │               │   └── RouteResponseDTO.java
│        │               ├── ExamApplication.java
│        │               ├── exception
│        │               │   ├── api
│        │               │       └── ApiError.java
│        │               │   ├── BadRequestException.java
│        │               │   └── handler
│        │               │       └── ApiErrorHandler.java
│        │               ├── service
│        │               │   ├── RouteScannerService.java
│        │               │   └── RouteService.java
│        │               ├── singleton
│        │               │   └── RouteSingleton.java
│        │               ├── util
│        │               │   ├── ReadFile.java
│        │               │   └── WriteFile.java
│        │               └── validation
│        │                   └── ValidateInput.java
│        └── resources
│        │   └── application.properties
│        └── test
│           └── java
│                └── com
│                    └── bexstech
│                        └── exam
│                            ├── controller
│                            │   └── RouteControllerTest.java
│                            ├── service
│                            │       ├── RouteScannerServiceTest.java
│                            │       └── RouteServiceTest.java
│                            └── validation
│                                └── ValidateInputTest.java
```

# Testing
Mockito and JUnit are used to help to create the tests

Run all the tests executing the command below
```sh
mvn test
```
Run all tests in one class
```sh
mvn -Dtest=ValidateInputTest test
```
Run a single test
```sh
mvn -Dtest=ValidateInputTest#shouldValidateTextInput test
```
