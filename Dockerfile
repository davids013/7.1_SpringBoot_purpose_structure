#FROM pisarenko/jre11-slim
#FROM adoptopenjdk/openjdk11:jre-11.0.13_8-alpine
FROM openjdk:8-jdk-alpine
EXPOSE 8081
ADD target/_7-1_SpringBoot_purpose-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java", "-jar", "myapp.jar"]