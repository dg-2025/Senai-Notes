# Imagem base do Java
FROM eclipse-temurin:17-jdk

# Define diretório de trabalho
WORKDIR /app

# Copia o .jar gerado pelo Spring Boot
COPY target/senai-notes-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
