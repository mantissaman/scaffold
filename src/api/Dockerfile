# docker build -t spring-in-docker:1.2 .

FROM adoptopenjdk/openjdk11:slim as build

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
RUN ./mvnw -B dependency:go-offline

COPY src src
RUN ./mvnw -B package -DskipTests

FROM adoptopenjdk/openjdk11:jre
LABEL org.opencontainers.image.source https://github.com/swat-source/claroty-bva
RUN set -eux
RUN apt-get update
RUN apt-get install -y --no-install-recommends fontconfig libfreetype6
RUN rm -rf /var/lib/apt/lists/*

COPY --from=build target/bvaapi-0.0.1-SNAPSHOT.jar bvaapi.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bvaapi.jar"]
