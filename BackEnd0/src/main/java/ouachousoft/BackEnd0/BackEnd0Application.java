package ouachousoft.BackEnd0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
@SpringBootApplication
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BackEnd0Application {

	public static void main(String[] args) {
		SpringApplication.run(BackEnd0Application.class, args);
	}

}
