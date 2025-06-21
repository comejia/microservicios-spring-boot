#FROM maven:3.9-amazoncorretto-21-alpine as builder
# using commons as image because this project has a dependency on commons
FROM commons as builder

WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine as runtime

COPY --from=builder /app/target/*.jar items.jar
#EXPOSE 9001

CMD ["java", "-jar", "items.jar"]