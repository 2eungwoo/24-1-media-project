FROM openjdk:17-oracle
ARG JAR_FILE=/build/libs/Its-0.0.1-SNAPSHOT.jar
ARG PROFILES
ARG ENV
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profgiiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-jar", "app.jar"]