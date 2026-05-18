# JWT Resource Server

This folder is named `jwt-resource-server`, but the application itself is now a simple JWT authentication server for your microservice setup.

## What it provides

- A visible login endpoint that returns JWT tokens
- A JWKS endpoint so other microservices can validate JWT signatures
- A public-key endpoint for services that want the raw RSA public key
- JWT access tokens with `roles`, `user_id`, and `email` claims
- Database-backed users with BCrypt-hashed passwords
- A public registration endpoint at `POST /api/users/register`

## Important endpoints

- `POST /api/auth/login`
- `GET /api/auth/jwks`
- `GET /api/auth/public-key`
- `/api/users/register`
- `/api/users/me`

## Environment variables

- `JWT_ISSUER`
- `JWT_EXPIRATION_MINUTES`
- `JWT_PUBLIC_KEY`
- `JWT_PRIVATE_KEY`
- `JWT_KEY_ID`

You can generate development keys with:

```bash
./mvnw -q -DskipTests compile
java -cp target/classes se.sprinto.hakan.springboot2.keygen.JwtKeyGenerator
```

If no JWT keys are configured, the application generates a temporary key pair on startup. That is convenient for development, but you should set stable keys for any environment shared with other services.

The default runtime database is now H2 in-memory, configured in `src/main/resources/application.properties`. That makes local startup simple, but all users, clients, and auth state are recreated on restart.

## How other microservices should trust this server

Microservices have two straightforward options:

1. Use the JWKS endpoint:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: http://localhost:9000/api/auth/jwks
```

2. Use the public key directly if you prefer to distribute the RSA public key yourself.

If the resource service needs role-based authorization, it should map the `roles` claim to `ROLE_` authorities in the same way this project does in `SecurityConfig`.

If you still want to validate `iss`, point the resource service at the same issuer configured in `app.jwt.issuer`.

Example login request:

```yaml
POST /api/auth/login
Content-Type: application/json

{
  "username": "bob",
  "password": "Secret123"
}
```
