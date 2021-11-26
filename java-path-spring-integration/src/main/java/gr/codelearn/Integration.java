package gr.codelearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application initializer.
 */
@SpringBootApplication
public class Integration {
	public static void main(String[] args) {
		SpringApplication.run(ProcessingAgent.class, args);
	}
}
