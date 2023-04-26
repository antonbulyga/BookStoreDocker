FROM maven:latest AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM eclipse-temurin:17
COPY --from=build /home/app/target/bookstore-0.0.2-SNAPSHOT.jar bookstore.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","bookstore.jar"]

