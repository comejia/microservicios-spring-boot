FROM maven:3.9-amazoncorretto-21-alpine as builder

WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine as runtime

COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8090

CMD ["java", "-jar", "app.jar"]