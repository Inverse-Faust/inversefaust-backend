FROM gradle:7.3.3-jdk17 AS build

WORKDIR /app

COPY . .

RUN ./gradlew build --no-daemon -x test

FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/build/libs/inversefaust-backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]

EXPOSE 8090
