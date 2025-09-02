# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o arquivo pom.xml e baixa dependências
COPY pom.xml .
RUN mvn dependency:resolve

# Copia o código-fonte
COPY src ./src

# Compila a aplicação sem rodar testes
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o JAR gerado do estágio de build (genérico)
COPY --from=build /app/target/*.jar app.jar

# Configurações do Spring Boot
ENV SPRING_PROFILES_ACTIVE=prod

# Expõe a porta
EXPOSE 8080

# Define o ponto de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
