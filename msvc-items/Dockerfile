# Using commons image as base because this project has a dependency on pom.xml
# and we first need to build the project.
FROM commons as builder
#FROM maven:3.9-amazoncorretto-21-alpine as builder

WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine as runtime

COPY --from=builder /app/target/*.jar items.jar
#EXPOSE 9001

CMD ["java", "-jar", "items.jar"]