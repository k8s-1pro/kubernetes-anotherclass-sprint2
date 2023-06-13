FROM openjdk:18
COPY ./app-0.0.1-SNAPSHOT.jar /usr/src/myapp/app.jar
ENTRYPOINT ["java", "-jar", "/usr/src/myapp/app.jar"]
EXPOSE 8090
WORKDIR /usr/src/myapp
