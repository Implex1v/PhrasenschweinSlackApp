FROM openjdk:17.0.2-jdk-slim-bullseye AS base
WORKDIR /app

FROM base AS build
ARG sonar_token=unset

COPY ./ /app
ENV SONAR_TOKEN ${sonar_token}
RUN ./gradlew -Dsonar.login=${sonar_token} build sonarqube installDist

FROM base AS release
COPY --from=build /app/build/install/phrasenschwein-slack-app .
ENV APP_ENABLE_METRICS true

EXPOSE 5000
CMD ["./bin/phrasenschwein-slack-app"]
