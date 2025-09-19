FROM openjdk:17
WORKDIR /app
COPY build/libs/volts-server-0.0.1-SNAPSHOT.jar /app/volts-server-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/volts-server-0.0.1-SNAPSHOT.jar"]