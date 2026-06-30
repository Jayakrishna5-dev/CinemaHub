package cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CinemaHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaHubApplication.class, args);
	}

}
