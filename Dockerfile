FROM openjdk:22-jdk-alpine
COPY target/app.jar app.jar
CMD ["java","-jar", "/app.jar"]
