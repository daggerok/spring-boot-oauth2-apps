package daggerok;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
@EnableAuthorizationServer
public class AuthorizationServer {

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServer.class, args);
  }
}

@Configuration
@AllArgsConstructor
class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  PasswordEncoder passwordEncoder;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
           .withClient("first-client")
           .secret(passwordEncoder.encode("noonewilleverguess"))
           .scopes("resource:read")
           .authorizedGrantTypes("authorization_code")
           .accessTokenValiditySeconds(300)
           .refreshTokenValiditySeconds(3600)
           .redirectUris("http://127.0.0.1:8080/client-app");
  }
}

@EnableWebSecurity
@AllArgsConstructor
class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

  PasswordEncoder passwordEncoder;

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(
        User.withUsername("enduser")
            .password(passwordEncoder.encode("enduserpassword"))
            .roles("USER")
            .build());
  }
}
