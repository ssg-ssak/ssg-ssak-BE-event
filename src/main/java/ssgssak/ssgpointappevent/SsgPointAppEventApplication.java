package ssgssak.ssgpointappevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SsgPointAppEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsgPointAppEventApplication.class, args);
	}

}
