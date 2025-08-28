# MS-AUTH: Autenticação e Segurança

Este microserviço é responsável por:

- Geração e validação de tokens JWT (Access e Refresh)
- Gerenciamento de chaves RSA (private/public)
- Autenticação centralizada para outros microserviços

## Estrutura do MS-AUTH

```
ms-auth-domain/
ms-auth-application/
ms-auth-web/
ms-auth-infrastructure/
```

## Executando

### Docker Compose
```bash
docker-compose up -d
docker-compose logs -f
```

### Localmente
```bash
cd ms-auth
mvn clean install
mvn spring-boot:run
```

## Endpoints Principais
- POST /auth/register
- POST /auth/login
- POST /auth/refresh