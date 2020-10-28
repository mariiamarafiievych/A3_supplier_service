FROM openjdk:14-jdk-alpine
ARG JAR_FILE=target/A3_supplier_service-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]