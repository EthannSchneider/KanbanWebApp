package ch.ethann.tpi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@EnableAutoConfiguration
@SpringBootTest(properties = "spring.profiles.active=test")
public class KanbanWebApplicationTest {
    
    @Test
	void contextLoads() {
	}
    
}
