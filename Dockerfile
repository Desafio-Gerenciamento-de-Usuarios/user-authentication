# Etapa de build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia pom.xml raiz e os dos módulos
COPY pom.xml .
COPY ms-auth-application/pom.xml ms-auth-application/
COPY ms-auth-domain/pom.xml ms-auth-domain/
COPY ms-auth-infrastructure/pom.xml ms-auth-infrastructure/
COPY ms-auth-web/pom.xml ms-auth-web/

# Baixa dependências (cache otimizado)
RUN mvn dependency:go-offline

# Copia o código-fonte completo
COPY . .

# Compila (gera jar dentro de ms-auth-web/target)
RUN mvn clean package -DskipTests


# Etapa final (runtime)
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copia o jar do módulo web (ajuste o nome se necessário)
COPY --from=build /app/ms-auth-web/target/*.jar app.jar

# Executa o jar
ENTRYPOINT ["java", "-jar", "app.jar"]
