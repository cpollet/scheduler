package net.cpollet.scheduler.spring.datastore;

import net.cpollet.scheduler.datastore.nitrite.NitriteJobStore;
import net.cpollet.scheduler.datastore.nitrite.converters.Converters;
import net.cpollet.scheduler.datastore.nitrite.converters.JobConverter;
import net.cpollet.scheduler.engine.api.JobStore;
import org.dizitart.no2.Nitrite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatastoreContext {
//    @Bean
//    public JobStore jobStore() {
//        return new InMemoryJobStore();
//    }

    @Bean
    public Nitrite db() {
        return Nitrite.builder()
                .filePath("/Users/cpollet/Development/scheduler/nitrite.db")
                .openOrCreate();
    }

    @Bean
    public JobConverter jobConverter() {
        return Converters.job();
    }

    @Bean
    public JobStore jobStore(Nitrite db, JobConverter jobConverter) {
        return new NitriteJobStore(db, jobConverter);
    }
}
