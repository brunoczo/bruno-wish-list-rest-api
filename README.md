
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
    * Solução de fila para processar as inserções no MongoDb
    * Escolha: Solução padrão de mercado

* **Redis**
    * Solução de Cache
    * Escolha: Agilizar a consulta e a inserção de dados

* **Mongo DB**
    * Banco de dados para garantir as informações da whishlist.
    * Escolha: banco com muito boa performance de  leitura, escalável
      mongosh "mongodb://192.168.49.2:31427/wishlist"

* **Kubenerts**
    * Agilidade na gestão da infra
    * Pasta dos deployments:
      * k8s/
    * Comandos 
      * kubectl apply -f redis-config.yaml
      * kubectl apply -f services.yaml
      * minikube service   reddis-db-svc
      * minikube service  mongo-db-svc
        Liberar acesso local ao redis
      * Rabbitmq:
        * https://www.rabbitmq.com/kubernetes/operator/using-operator.html
        * kubectl expose deployment rabbitmq-d --type=NodePort

* **BDD**
  * Executar teste: Cucumber java + webclient
  * Criar testes: Intellij Gherkin
    * Source: src/test/resources/wishlistservice.feature

