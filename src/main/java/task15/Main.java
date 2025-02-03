package task15;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import task15.services.Calculator;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class);
  }

  @Bean
  public CommandLineRunner run(ApplicationContext context) {
    return args -> {
      Calculator calculator = context.getBean(Calculator.class);

      System.out.println("Первый вызов:");
      System.out.println(calculator.fibonacci(10));

      System.out.println("\nВторой вызов:");
      System.out.println(calculator.fibonacci(10));

      System.out.println("\nТретий вызов:");
      System.out.println(calculator.fibonacci(16));
    };
  }
}