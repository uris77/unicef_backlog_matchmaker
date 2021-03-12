FROM openjdk:17-slim-buster

WORKDIR /app

COPY build/libs/unicef-backlog-matchmaker-1.0.0-SNAPSHOT-all.jar /app/app.jar
COPY perms.json /app/creds.json

ENV GOOGLE_APPLICATION_CREDENTIALS "/app/creds.json"

CMD ["java", "-jar", "/app/app.jar"]

