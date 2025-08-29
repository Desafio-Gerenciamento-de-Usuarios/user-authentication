create table usuario
(
    id_user      uuid         not null
        primary key,
    email        varchar(255) not null
        unique,
    nome_usuario varchar(255) not null,
    senha        varchar(255) not null,
    user_name    varchar(255) not null
        unique
);

alter table usuario
    owner to "user";

