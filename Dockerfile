FROM maven:3.6.2-jdk-11-slim as builder
WORKDIR /build
COPY pom.xml .
RUN mvn de.qaware.maven:go-offline-maven-plugin:resolve-dependencies
COPY .git ./.git
COPY src ./src
RUN mvn clean package

FROM openjdk:14-jdk-alpine
HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 \
  CMD [ "curl", "--fail", "-H", "Accept: application/json", "http://localhost:8080/actuator/health" ]
RUN \
  addgroup -g 8080 app && \
  adduser -u 8080 -G app -D app && \
  apk add curl
USER app
ENV APP_HOME /home/app
ENV APP_JAR "${APP_HOME}/singlepage-springboot.jar"
COPY --chown=app:app --from=builder /build/target/singlepage-springboot-*.jar $APP_JAR
CMD java -jar $APP_JAR
