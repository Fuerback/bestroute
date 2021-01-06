# BestRoute
Help to find the cheaper route for your trip.

# Prerequisites

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

Run the project and inform the file path as argument
```sh
mvn spring-boot:run -Dspring-boot.run.arguments=--file=/home/felipe/Docs/routes.csv
```
