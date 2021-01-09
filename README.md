# BestRoute
The BestRoute is a system that helps you to find the cheaper route for your trip using a graph data structure.

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

# Packages and Files Structure

```sh
.
├── docs
│	└── api
│	    ├── components
│	    │	├── paths
│	    │	│	└── route.yaml
│	    │	└── schemas
│	    │	    ├── route-insert.yaml
│	    │	    └── route-response.yaml
│	    └── openapi.yaml
├── README.md
├── resources
│	└── routes.csv
├── src
│	├── main
│	│	├── java
│	│	│	└── com
│	│	│	    └── bexstech
│	│	│	        └── exam
│	│	│	            ├── controller
│	│	│	            │	└── RouteController.java
│	│	│	            ├── dto
│	│	│	            │	├── RouteDTO.java
│	│	│	            │	└── RouteResponseDTO.java
│	│	│	            ├── engine
│	│	│	            │	└── DijkstraAlgorithm.java
│	│	│	            ├── ExamApplication.java
│	│	│	            ├── exception
│	│	│	            │	├── api
│	│	│	            │	│	└── ApiError.java
│	│	│	            │	├── BadRequestException.java
│	│	│	            │	├── handler
│	│	│	            │	│	└── ApiErrorHandler.java
│	│	│	            │	└── NotFoundException.java
│	│	│	            ├── model
│	│	│	            │	├── CheapestPath.java
│	│	│	            │	├── Edge.java
│	│	│	            │	├── Graph.java
│	│	│	            │	└── Vertex.java
│	│	│	            ├── service
│	│	│	            │	├── RouteScannerService.java
│	│	│	            │	└── RouteService.java
│	│	│	            ├── singleton
│	│	│	            │	└── RouteSingleton.java
│	│	│	            ├── util
│	│	│	            │	├── ReadFile.java
│	│	│	            │	└── WriteFile.java
│	│	│	            └── validation
│	│	│	                └── ValidateInput.java
│	│	└── resources
│	│	    └── application.properties
│	└── test
│	    └── java
│	        └── com
│	            └── bexstech
│	                └── exam
│	                    ├── controller
│	                    │	└── RouteControllerTest.java
│	                    ├── engine
│	                    │	└── DijkstraAlgorithmTest.java
│	                    ├── service
│	                    │	├── RouteScannerServiceTest.java
│	                    │	└── RouteServiceTest.java
│	                    └── validation
│	                        └── ValidateInputTest.java

```

# API Documentation

The API documentation was wrote using Swagger Editor.

![api](https://user-images.githubusercontent.com/3007452/104110886-ca846100-52ba-11eb-9369-5db7343c8e67.png)

To open the full documentation, follow the steps:

- Open https://editor.swagger.io/
- Copy the documentation compiled below
- Paste the documentation compiled on swagger editor website

<details><summary><b>Swagger API documentation compiled</b></summary>

``` yaml

openapi: 3.0.2
info:
  title: API BestRoute
  description: Best Route API description
  version: '1.0'
tags:
  - name: Route
    description: Operations related to flights routes
paths:
  /route:
    summary: Operations related to flights routes
    get:
      summary: Find the cheapest route between two locations
      tags:
        - Route
      parameters:
        - in: query
          name: source
          schema:
            type: string
          required: true
        - in: query
          name: destination
          schema:
            type: string
          required: true
      operationId: findRoute
      responses:
        '200':
          description: success
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/route-response'
        '400':
          description: invalid input
        '404':
          description: no routes found
    put:
      summary: Insert a new flight route and your price
      tags:
        - Route
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/route-insert'
        required: true
      operationId: insertRoute
      responses:
        '204':
          description: success
        '400':
          description: invalid input
components:
  schemas:
    route-response:
      type: object
      properties:
        routeDescription:
          type: string
          example: GRU - FLN - CRT
        totalPrice:
          type: integer
          format: int32
          example: 25
    route-insert:
      type: object
      properties:
        source:
          type: string
          example: GRU
        destination:
          type: string
          example: FLN
        price:
          type: integer
          format: int32
          example: 25


```
</details>

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
