# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia pom.xml e os poms dos módulos
COPY pom.xml .
COPY ms-auth-application/pom.xml ms-auth-application/
COPY ms-auth-domain/pom.xml ms-auth-domain/
COPY ms-auth-infrastructure/pom.xml ms-auth-infrastructure/
COPY ms-auth-web/pom.xml ms-auth-web/

# Baixa dependências sem copiar código ainda (cache)
RUN mvn dependency:go-offline

# Agora copia todo o código
COPY . .

# Compila (gera jar dentro do módulo web geralmente)
RUN mvn clean package -DskipTests