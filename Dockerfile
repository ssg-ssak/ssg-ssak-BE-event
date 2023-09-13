#FROM openjdk:17-alpine
#COPY build/libs/ssg-point-app-event-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8003
#ENTRYPOINT ["java","-jar", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=prod", "/app.jar"]

# server를 위한 dockerfile

FROM openjdk:17-alpine AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM openjdk:17-alpine
COPY --from=builder build/libs/*.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=prod", "/app.jar"]