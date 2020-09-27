FROM openjdk:11-jdk-slim
USER 1001:1001
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} .
ENTRYPOINT java -XX:+UseG1GC -Dspring.profiles.active=docker -Djava.security.egd=file:/dev/./urandom -XX:+UnlockExperimentalVMOptions -jar /discovery-service.jar

