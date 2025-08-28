# Etapa 1: build com Maven
FROM maven:3.9.11-eclipse-temurin-24 AS builder

# Define diretório de trabalho
WORKDIR /app

# Copia os arquivos de projeto (pom e código)
COPY pom.xml .
COPY src ./src

# Executa o build da aplicação
RUN mvn clean package -DskipTests

# Etapa 2: imagem final para rodar o jar
FROM eclipse-temurin:24-jdk-alpine-3.21

# Define diretório de trabalho na imagem final
WORKDIR /app

# Copia o JAR da build anterior
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]