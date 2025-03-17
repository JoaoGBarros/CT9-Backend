FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
COPY src/main/resources/ct9-na-palma-da-mao-firebase-adminsdk.json /app/res/ct9-na-palma-da-mao-firebase-adminsdk.json
COPY .env.docker ./.env.docker
RUN chmod +x mvnw && ./mvnw package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY .env.docker ./.env.docker
COPY src/main/resources/ct9-na-palma-da-mao-firebase-adminsdk.json /app/res/ct9-na-palma-da-mao-firebase-adminsdk.json
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
