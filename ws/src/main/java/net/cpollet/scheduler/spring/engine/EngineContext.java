package net.cpollet.scheduler.spring.engine;

import net.cpollet.scheduler.engine.api.Scheduler;
import net.cpollet.scheduler.engine.api.JobStore;
import net.cpollet.scheduler.engine.internals.DynamicExecutableJobFactory;
import net.cpollet.scheduler.engine.internals.ExecutableJobStoreAdapter;
import net.cpollet.scheduler.engine.internals.spring.SpringScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class EngineContext {
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(10);
        scheduler.initialize();

        return scheduler;
    }

    @Bean
    public DynamicExecutableJobFactory jobTypes() {
        DynamicExecutableJobFactory jobFactory = new DynamicExecutableJobFactory();
        jobFactory.register(new String[]{"net.cpollet.scheduler"});
        return jobFactory;
    }

    @Bean
    public ExecutableJobStoreAdapter executableJobStoreAdapter(JobStore jobStore, DynamicExecutableJobFactory jobFactory) {
        return new ExecutableJobStoreAdapter(jobStore, jobFactory);
    }

    @Bean
    public Scheduler Scheduler(TaskScheduler taskScheduler, ExecutableJobStoreAdapter executableJobStore, DynamicExecutableJobFactory jobFactory) {
        return new SpringScheduler(taskScheduler, executableJobStore, jobFactory);
    }
}
