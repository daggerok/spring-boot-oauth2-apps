package daggerok;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Value
@ConstructorBinding
@ConfigurationProperties(prefix = "my")
class MyProps {
  String registrationUrl;
}

@Controller
@RequiredArgsConstructor
class MyController {

  final MyProps myProps;

  @GetMapping
  String index(Model model) {
    model.addAttribute("message", "Hello, World!");
    model.addAttribute("registrationUrl", myProps.getRegistrationUrl());
    return "index";
  }

  @GetMapping("/client-app")
  String registration(@RequestParam("code") String code, Model model) {
    model.addAttribute("code", code);
    return "registration";
  }
}

@SpringBootApplication
@EnableConfigurationProperties(MyProps.class)
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
