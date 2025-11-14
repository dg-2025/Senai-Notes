# Etapa 1: build
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Copia tudo
COPY . .

# Dá permissão ao Maven Wrapper
RUN chmod +x mvnw

# Compila e gera o jar
RUN ./mvnw clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copia o jar gerado
COPY --from=builder /app/target/senai-notes-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
