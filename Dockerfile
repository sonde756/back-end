# Use uma imagem base que tenha o Java e o Maven instalados
FROM maven:3.6.3-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/techroom-1.0-SNAPSHOT.jar ./app.jar
EXPOSE $SERVER_PORT
CMD ["java", "-jar", "app.jar"]
