# Usa a imagem oficial do Temurin Java 21
FROM eclipse-temurin:21-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia o .jar gerado pela sua aplicação Spring Boot
COPY target/*.jar app.jar

# Expõe a porta configurada
EXPOSE ${SERVER_PORT}

# Executa o .jar
ENTRYPOINT ["java", "-jar", "app.jar"]