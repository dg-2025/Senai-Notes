# Etapa 1: build da aplicação usando Maven
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Copia o projeto completo
COPY . .

# Compila e gera o .jar (sem rodar testes para acelerar o build)
RUN ./mvnw clean package -DskipTests

# Etapa 2: imagem final para execução
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copia o .jar gerado da etapa anterior
COPY --from=builder /app/target/senai-notes-0.0.1-SNAPSHOT.jar app.jar

# Define a porta (Render vai injetar a porta correta com $PORT)
ENV PORT=8080
EXPOSE 8080

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
