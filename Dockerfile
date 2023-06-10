# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
ARG PROJECT_NAME=tg-yunxiao-bot
ARG BASE=/myapp
ARG PROJECT_PATH=/myapp/$PROJECT_NAME

WORKDIR $PROJECT_PATH

COPY pom.xml $PROJECT_PATH/
COPY src $PROJECT_PATH/src

RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim
ARG PROJECT_NAME=tg-yunxiao-bot
ARG BASE=/myapp
ARG PROJECT_PATH=/myapp/$PROJECT_NAME
ARG JAVA_CMD=/usr/local/openjdk-17/bin/java

WORKDIR $PROJECT_PATH

COPY --from=build $PROJECT_PATH/target/$PROJECT_NAME-*.jar app.jar

CMD ["bash", "-c", "nohup $JAVA_CMD -jar app.jar >> app.log 2>&1 & echo $! > pid.log && tail -f app.log"]