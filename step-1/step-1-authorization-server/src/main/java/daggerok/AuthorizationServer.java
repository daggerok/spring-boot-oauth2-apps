package daggerok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * jdk14
 * ./mvnw -f step-1/step-1-authorization-server
 * java -jar step-1/step-1-authorization-server/target/*.jar
 * curl -sS first-client:noonewilleverguess@0:8081/oauth/token -d grant_type=client_credentials -d scope=any
 * curl -sS first-client:noonewilleverguess@0:8081/oauth/token -d grant_type=password -d scope=any -d username=enduser -d password=enduserpassword
 */
@SpringBootApplication
@EnableAuthorizationServer
public class AuthorizationServer {
  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServer.class, args);
  }
}

@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    var passwordEncoder = passwordEncoder();
    return new InMemoryUserDetailsManager(
        User.builder()
            .username("enduser")
            .password(passwordEncoder.encode("enduserpassword"))
            .roles("USER")
            .build());
  }
}
