package ch.ethann.tpi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import ch.ethann.tpi.service.KanbanService;
import ch.ethann.tpi.service.impl.DefaultKanbanService;

@Configuration
public class BeanConfiguration {
	@Bean
	ProjectionFactory projectionFactory() {
		return new SpelAwareProxyProjectionFactory();
	}

    @Bean
	KanbanService kanbanService() {
		return new DefaultKanbanService();
	}
}