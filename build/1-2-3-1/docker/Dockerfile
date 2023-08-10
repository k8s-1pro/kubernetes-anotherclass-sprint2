FROM openjdk:17
COPY ./app-0.0.1-SNAPSHOT.jar /usr/src/myapp/app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${spring-profiles-active}", "-Dapplication.role=${application-role}", "-Dpostgresql.filepath=${postgresql-filepath}", "-jar", "/usr/src/myapp/app.jar"]
EXPOSE 8080
WORKDIR /usr/src/myapp