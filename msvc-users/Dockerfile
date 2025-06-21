FROM maven:3.9-amazoncorretto-21-alpine as builder

WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine as runtime

COPY --from=builder /app/target/*.jar users.jar
#EXPOSE 8080

CMD ["java", "-jar", "users.jar"]