FROM postgres:15

# Configura variáveis do banco
ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=pass
ENV POSTGRES_DB=user_db

# Copia o script de inicialização
COPY import.sql /docker-entrypoint-initdb.d/
