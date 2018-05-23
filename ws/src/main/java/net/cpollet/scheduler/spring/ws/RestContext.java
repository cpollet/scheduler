package net.cpollet.scheduler.spring.ws;

import net.cpollet.scheduler.engine.api.Scheduler;
import net.cpollet.scheduler.rest.adapters.SchedulerRestAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class RestContext {
    @Bean
    public SchedulerRestAdapter jobAdapter(Scheduler scheduler) {
        return new SchedulerRestAdapter(scheduler);
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}
