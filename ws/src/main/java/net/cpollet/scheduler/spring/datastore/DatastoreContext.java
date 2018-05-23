package net.cpollet.scheduler.spring.datastore;

import net.cpollet.scheduler.datastore.inmemory.InMemoryJobStore;
import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.JobStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class DatastoreContext {
    @Bean
    public JobStore jobStore() {
        return new InMemoryJobStore();
    }
}
