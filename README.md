# Microservicios con Spring Boot

Gestion de productos usando microservicios


## Características :clipboard:

* Arquitectura de Microservicios con Spring Boot y Spring Cloud
    * Servicios disponibles: Products, Items, Users, OAuth, Gateway
* Configuración centralizada
* Autorización con OAuth2
* Uso del patron Circuit Breaker para resiliencia
* Observabilidad con Micrometer para las trazas


## Tecnologias :hammer_and_wrench:
* Java 21
* Spring boot 3 y Spring Cloud (Eureka, Config y Gateway)
* OAuth2
* Zipkin
* Elasticsearch
* MySQL
* Docker
* Docker compose
* Postman


## Instalación :electric_plug:

1) Levantar Zipkin
```bash
$ docker-compose -f docker-compose-tracing.yml up --build
```

2) Levantar los microservicios
```bash
$ docker-compose -f docker-compose-microservices.yml up --build
```

3) Importar los datos en la DB:
```bash
$ mariadb-dump -u root --databases microservicios > ./projects/backup/microservicios.sql
```


## Uso :computer:

Para ver los microservicios disponibles (Service discovery): http://localhost:8761/

Visualización de las trazas: http://localhost:9411/zipkin


## Referencias :mag:

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Cloud](https://spring.io/projects/spring-cloud)
* [Zipkin](https://zipkin.io/)
* [Micrometer trazing](https://docs.micrometer.io/tracing/reference/)
* [Opentelemetry](https://opentelemetry.io/docs/languages/java/)
* [Microservicios](https://spring.io/microservices)
* [Access/Refresh token](https://datatracker.ietf.org/doc/html/rfc6749#section-1.4)


