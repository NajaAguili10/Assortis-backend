# ──────────────────────────────────────────────────────────────
# Stage: Runtime
# Base image: eclipse-temurin:17-jre-alpine
#   - Matches the project's Java 17 requirement
#   - Alpine keeps the image size minimal
# ──────────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the packaged JAR without tying the image to one exact version string.
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Copy the templates directory required by the project-references feature
# (project-references.templates-path=./templates in application.properties)
COPY templates/ templates/

# Expose the application port
EXPOSE 6969

# JVM tuning options – override at container runtime with -e JAVA_OPTS="..."
# Example: -e JAVA_OPTS="-Xmx512m -Xms256m"
ENV JAVA_OPTS=""

# SERVER_PORT is read by Spring Boot via ${SERVER_PORT:6969} in application.properties
ENV SERVER_PORT=6969

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
