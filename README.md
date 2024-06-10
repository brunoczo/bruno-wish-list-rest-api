
java-webflux-rabbit-mongo-redis-bdd-kubenerts


# Wishlist Service

This is the main project of the developed solution. 

* Project that is part of the solution::
  * https://github.com/brunoczo/bruno-wish-list-rest-api-receiver 
  * Purpose: to receive data from a queue and save it in the database.
  * Choice: to remove all write load from the main application.


## Technologies used:
![Alt text](docs/arquitetura_macro.png?raw=true "Title")
* **API Documentation Creation**
    * Macro Architecture: docs/arquitetura-macro.png
    * API design created before implementation - Facilitates understanding and optimizes development time since the code is generated from the file
        * https://editor.swagger.io/
        * https://github.com/swagger-api/swagger-codegen
        * Local file: src/main/resources/openapi/yaml/openapi-wishlist.yaml
        * Code generation command:  mvn clean compile

* **IDE used**
  * Intellij

* **Spring boot with WebFlux**
    * Reactive programming.
    * Choice: reactive programming has an advantage in environments with many requests, because it can handle a high volume of requests
    * **Dependencies used**
        * Swegger - OpiAPI
            * http://localhost:8080/api/webjars/swagger-ui/index.html
        * Lombok

* **ReabbitMQ**
    * Queue solution to process insertions into MongoDb
    * Choice: Market standard solution

* **Redis**
    * Cache solution
    * Choice: Speed up data query and insertion

* **Mongo DB**
    * Database to guarantee whishlist information.
    * Choice: database with very good read performance, scalable
      mongosh "mongodb://192.168.49.2:31427/wishlist"

* **Kubenerts**
    * Agility in infrastructure management
    * Deployments folder:
      * k8s/
    * Commands 
      * kubectl apply -f redis-config.yaml
      * kubectl apply -f services.yaml
      * minikube service   reddis-db-svc
      * minikube service  mongo-db-svc
        Liberar acesso local ao redis
      * Rabbitmq:
        * https://www.rabbitmq.com/kubernetes/operator/using-operator.html
        * kubectl expose deployment rabbitmq-d --type=NodePort

* **BDD**
  * Run test: Cucumber java + webclient
  * Create tests: Intellij Gherkin
    * Source: src/test/resources/wishlistservice.feature

