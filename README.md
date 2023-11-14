# Serviço Wishlist


## Tecnologias utilizadas:
* **Criação da Documentação da API**
    * Desenho da api criada antes da implementação - Facilita o entedimento e atimiza o tempo de desenvolvimento  visto que pelo arquivo o codigo é gerado
        * https://editor.swagger.io/
        * https://github.com/swagger-api/swagger-codegen
        * Arquivo local: src/main/resources/openapi/yaml/openapi-wishlist.yaml
        * Comando gerar codigo:  mvn clean compile

* **IDE utilizada**
  * Intellij

* **Spring boot com WebFlux**
    * Programação reativa.
    * Escolha: programação reativa tem uma vantagem em ambientes com muitas requisições, porque conseguir lidar com um alto volume de requisições
    * **Dependecias utilizadas**
        * Swegger - OpiAPI
            * http://localhost:8080/api/webjars/swagger-ui/index.html
        * Lombok

* **ReabbitMQ**
    * Solução de fila para processar as inserções no MongoDb para não deixar a inserção de dados influenciar na aplicação
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

