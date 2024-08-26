FROM openjdk:21-jdk-slim
COPY target/app.jar app.jar
CMD ["java","-jar", "/app.jar"]
