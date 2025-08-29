-- Cria os schemas
CREATE SCHEMA IF NOT EXISTS management AUTHORIZATION "user";
CREATE SCHEMA IF NOT EXISTS auth AUTHORIZATION "user";

-- Schema: management (Usuário + Endereço)
CREATE TABLE management.usuarios
(
    id_usuario UUID PRIMARY KEY,
    documento  VARCHAR(50)  NOT NULL UNIQUE,
    email      VARCHAR(255) NOT NULL UNIQUE,
    nome       VARCHAR(255) NOT NULL,
    telefone   VARCHAR(20),
    dt_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE management.enderecos
(
    id_endereco UUID PRIMARY KEY,
    id_usuario  UUID         NOT NULL,
    logradouro  VARCHAR(255) NOT NULL,
    numero      VARCHAR(20)  NOT NULL,
    cidade      VARCHAR(100) NOT NULL,
    estado      VARCHAR(100) NOT NULL,
    cep         VARCHAR(20)  NOT NULL,
    dt_criacao  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES management.usuarios (id_usuario) ON DELETE CASCADE
);

ALTER TABLE management.usuarios OWNER TO "user";
ALTER TABLE management.enderecos OWNER TO "user";


-- Schema: auth (Autenticação)
CREATE TABLE auth.usuarios
(
    id_usuario    UUID PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    nome_usuario  VARCHAR(255) NOT NULL,
    senha         VARCHAR(255) NOT NULL,
    usuario_login VARCHAR(255) NOT NULL UNIQUE,
    dt_criacao    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE auth.usuarios OWNER TO "user";
-- Índices para otimização de consultas
