## YourNextHome 
This is the project is a Spring and Hibernate based web application that personalizes home goods

### Project Technical Overview:
This application is developed in Spring Framework by using 
Spring Boot, Spring Data, Hibernate, Spring RESTful web services, 
Postman, Maven, PostgresSql, Docker, Amazon SQS, and Amazon S3.
1. Project Business Rules
2. Relationships
       Project Approach:
       Created Users, Roles, Oders, Products, Categories, Brands 
       
## Configure local environment
### 1.Setup local database with docker
Reference to postgres docker [image](http://hub.docker.com/_/postgres) for environment option.
```
docker run --name homeGoodsDB -e POSTGRES_DB=homeGoods -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=password -p 5555:5432 -d postgres
```
### migrate database schema
Refer to flyway setup [documentation](https://flywaydb.org/documentation/migrations), find all [migration schema](src/main/resources/db/migrate)
```
mvn clean compile flyway:mygrate
```
### Testing 
Tests are done using JUnit and Mockito. 
```
mvn compile test -Dspring.profiles.active=${unit} -Daws.region=${us-east-1} -Ddatabase.url=jdbc:postgresql://localhost:5555/homeGoods -Ddatabase.user=${admin} -Ddatabase.password=${password} 
```
