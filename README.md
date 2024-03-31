# ````Unit Integration Testing````

> This project was built with Spring Boot. In the project, unit and integration test examples were implemented via a simple user service api.

> Unit and integration tests were carried out using **JUnit5** and **Mockito** libraries.

---

## ````Docker Setup````
> The project was dockerized with **Docker**. Database and application services can be stood up using **Docker Compose**.

- Spring Boot application is dockerized.
```shell
docker build -t user-service-api:0.0.1 .
```
- Services are instantiated collectively with Docker Compose commands.
```shell
docker compose up
```
- Services are stopped en masse with Docker Compose commands.
```shell
docker compose down
```