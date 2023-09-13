FROM openjdk:17-alpine
COPY build/libs/ssg-point-app-event-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8003
ENTRYPOINT ["java","-jar", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=prod", "/app.jar"]