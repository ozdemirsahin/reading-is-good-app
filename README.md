# Online Book Retail Service
It is a backend service developed by using SpringBoot and MongoDB.
It runs on an embedded Tomcat via port 8080.

Firstly, you need to create Bearer token to send request ```/authenticate``` endpoint, then use created Bearer token to send request other controller endpoints. Otherwise, UnAuthorized exception is returned

### Run

docker-compose up -d

mvn clean install

mvn spring-boot:run


### Postman Collection Directory

reading-is-good-app/ReadingIsGood.postman_collection.json

### Used Technologies and Libraries

Java 11, SpringBoot, MongoDB, Maven, Gson, Lombok, Mockito, Swagger
