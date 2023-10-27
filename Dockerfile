# Use uma imagem base que tenha o Java e o Maven instalados
FROM maven:3.8.6-amazoncorretto-17 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/target/techroom-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE $SERVER_PORT
CMD ["java", "-jar", "app.jar"]

