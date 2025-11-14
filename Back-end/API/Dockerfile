# Etapa 1: build
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/target/senai-notes-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
