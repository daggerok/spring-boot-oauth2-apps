# spring-boot-oauth2-apps [![CI](https://github.com/daggerok/spring-boot-oauth2-apps/workflows/CI/badge.svg)](https://github.com/daggerok/spring-boot-oauth2-apps/actions/)
Yet another... in progress...

```
     +-----+                                 +-----+                               +---------------+
     |  B  | -- (1) protected resource ----> |     | -- (4) authorization grant -> | authorization |
     |  R  | <- (2) unauthorized ----------- |  A  | <- (5) access token --------- |     server    |
 \O_ |  O  | -- (3) authorization ---------> |     |                               +---------------+
  |  |  W  |                                 |  P  |
 / \ |  S  |                                 |     |                               +---------------+
     |  E  | <- (8) protected resource ----- |  P  | -- (6) access token --------> |    resource   |
     |  R  |                                 |     | <- (7) protected resource --- |     server    |
     +-----+                                 +-----+                               +---------------+
```

## build, run and test

1. using java 14, build and run apps:
   ```bash
   brew cask reinstall adoptopenjdk14
   ./mvnw clean ; ./mvnw -pl :authorization-server,:app
   java -jar authorization-server/target/*.jar &
   java -jar app/target/*.jar &
   ```
1. open in browser: http://127.0.0.1:8080
1. click register
1. you should be redirected with url: http://localhost:8081/oauth/authorize?grant_type=authorization_code&response_type=code&client_id=first-client
1. authenticate with: `enduser` / `enduserpassword`
1. you should be redirected on authorization page
1. allow or deny...
1. once allowed, click Authorize
1. you should be redirected back to target page with authorization code present in a browser url

### jib

```bash
./mvnw clean package jib:docker
```

## step 1

__

```bash
jdk14
./mvnw -f step-1/step-1-authorization-server
java -jar ./step-1/step-1-authorization-server/target/*.jar
curl -sS first-client:noonewilleverguess@0:8081/oauth/token -d grant_type=client_credentials -d scope=any
curl -sS first-client:noonewilleverguess@0:8081/oauth/token -d grant_type=password -d scope=any -d username=enduser -d password=enduserpassword
```

## step 0

__

```bash
jdk14
./mvnw -f step-0/step-0-authorization-server
java -jar ./step-0/step-0-authorization-server/target/*.jar
curl first-client:noonewilleverguess@localhost:8081/oauth/token -dgrant_type=client_credentials -dscope=any
```

# resources

* [Spring Security OAuth2 Authentication Server reference](https://docs.spring.io/spring-security-oauth2-boot/docs/current/reference/htmlsingle/)
* [Spring Boot 2 – OAuth2 Auth and Resource Server](https://howtodoinjava.com/spring-boot2/oauth2-auth-server/)
