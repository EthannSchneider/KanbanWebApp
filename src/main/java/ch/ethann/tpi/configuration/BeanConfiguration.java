package ch.ethann.tpi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import ch.ethann.tpi.service.KanbanService;
import ch.ethann.tpi.service.TaskService;
import ch.ethann.tpi.service.impl.DefaultKanbanService;
import ch.ethann.tpi.service.impl.DefaultTaskService;

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

	@Bean
	TaskService taskService() {
		return new DefaultTaskService();
	}
}