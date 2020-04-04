package daggerok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * jdk14
 * ./mvnw -f step-0/step-0-authorization-server
 * java -jar step-0/step-0-authorization-server/target/*.jar
 * curl -sS first-client:noonewilleverguess@0:8081/oauth/token -d grant_type=client_credentials -d scope=any
 */
@SpringBootApplication
@EnableAuthorizationServer
public class AuthorizationServer {
  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServer.class, args);
  }
}
