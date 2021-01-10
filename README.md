# Descrição
O programa BestRoute foi desenvolvido para suportar duas interfaces para o usuário, via REST API e linha de comando pelo terminal, e ambas as interfaces possibilitam a pesquisa da melhor rota, mas apenas é possível inserir uma nova rota através da API.

Ao executar o programa é necessário informar o caminho do arquivo CSV para que seja feita a leitura de todas as rotas e valores correspondentes. Para que as informações sejam sempre atualizadas em todas as interfaces quando houver uma atualização, foi utilizado o padrão de projeto Singleton, que além de evitar o consumo desnecessário de memória e processamento para a leitura constante do arquivo, também permite que a instância seja acessada por todas as camadas de interesse sem que haja problemas de concorrência.

As rotas com seus preços (pesos) pode ser interpretada como grafos, ou seja, cada sigla de aeroporto é um vértice (Vertex) e a rota entre elas é uma Aresta (Edge), formando assim um Grafo (Graph) como no exemplo abaixo: 

![grafo](https://user-images.githubusercontent.com/3007452/104122700-4f0bc980-5325-11eb-9f3c-e38d4b1fc93b.png)

Para encontrar a rota com menor valor no grafo foi utilizado o algoritmo de Dijkstra, que calcula o caminho de custo mínimo entre vértices em um grafo. Foram feitos alguns ajustes no algoritmo para que fosse considerado a direção das rotas e tratamento de erros adequados para o problema atual.

Existe no programa um leque le validações de input e tratamento de exceções para que fique transparente para o cliente o motivo de um possível problema.

# Pré-requisitos

- Java 11
- Maven 3.0.4+

# Como executar
Acesse a pasta do programa e execute o comando para compilar o projeto.
```sh
cd bestroute
mvn clean install
```

Execute o programa passando por parâmetro um caminho válido de um csv contendo as rotas.
```sh
mvn spring-boot:run -Dspring-boot.run.arguments=--file=/home/folder/routes.csv
```

# Estrutura de pastas e arquivos

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

# Documentação da API

A documentação da API foi feita utilizando o Swagger Editor.

![api](https://user-images.githubusercontent.com/3007452/104110886-ca846100-52ba-11eb-9369-5db7343c8e67.png)

GET - exemplo para encontrar a melhor rota através da API
```sh
curl -X GET "http://localhost:8080/route?source=GRU&destination=CDG"
```

PUT - exemplo para inserir uma nova rota através da API
```sh
curl -X PUT "http://localhost:8080/route" -H  "Content-Type: application/json" -d "{\"source\":\"GRU\",\"destination\":\"FLN\",\"price\":25}"
```

Para abrir a documentação completa da API, siga os passos abaixo:

- Abra a página https://editor.swagger.io/
- Copie a documentação compilada abaixo
- Cole a documentação compilada no editor do Swagger e visualize todos os detalhes da API

<details><summary><b>Documentação compilada Swagger Editor</b></summary>

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

# Testes
Para criar todos os testes do programa, foram utilizados o Mockito e JUnit.

Para executar todos os testes do programa, execute:
```sh
mvn test
```
Executar apenas uma classe de testes:
```sh
mvn -Dtest=ValidateInputTest test
```
Executar um único teste:
```sh
mvn -Dtest=ValidateInputTest#shouldValidateTextInput test
```
