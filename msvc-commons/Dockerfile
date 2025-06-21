FROM maven:3.9-amazoncorretto-21-alpine as builder

WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean install -DskipTests
