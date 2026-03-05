
FROM gradle:9.3.1-jdk21 AS builder

WORKDIR /app

# Copy only Gradle config first (better caching)
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Download dependencies only (no source yet)
RUN gradle dependencies --no-daemon

# Copy source code
COPY src ./src

# Build executable jar
RUN gradle clean bootJar -x test --no-daemon


# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jdk-jammy AS runner

WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/build/libs/apigateway-**.jar app.jar

EXPOSE 4004
ENTRYPOINT ["java", "-jar", "app.jar"]