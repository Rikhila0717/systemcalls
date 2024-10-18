FROM openjdk:17

WORKDIR /app
ARG OAUTH2_CLIENT_ID
ARG OAUTH2_CLIENT_SECRET

ENV GITHUB_CLIENT_ID=$OAUTH2_CLIENT_ID
ENV GITHUB_CLIENT_SECRET=$OAUTH2_CLIENT_SECRET

CMD ["./gradlew","clean","bootJar"]

COPY build/libs/*.jar systemcalls.jar

ENTRYPOINT ["java", "-jar", "systemcalls.jar"]
