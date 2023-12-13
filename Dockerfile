FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /src

COPY src src
COPY .mvn .mvn
COPY mvnw.cmd .
COPY mvnw .
COPY pom.xml .


#compile th java application
RUN mvn package -Dmaven.test.skip=true

FROM maven:3-eclipse-temurin-21

WORKDIR /app

#copy and rename to app.jar
COPY --from=builder /src/target/day17ws-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080
ENV SPRING_REDIS_HOST=localhost
ENV SPRING_REDIS_PORT=1234
ENV SPRING_REDIS_USERNAME=
ENV SPRING_REDIS_PASSWORD=
ENV SPRING_REDIS_DATABASE=0
ENV NEWSAPI_KEY=

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar ./app.jar
