FROM openjdk:11.0.14.1-jre-slim

WORKDIR /home/app

COPY target/*.jar /home/app/app.jar

EXPOSE 8072

ENTRYPOINT ["java", "-jar", "/home/app/app.jar"]