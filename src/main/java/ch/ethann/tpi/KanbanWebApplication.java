package ch.ethann.tpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KanbanWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(KanbanWebApplication.class, args);
	}

}
