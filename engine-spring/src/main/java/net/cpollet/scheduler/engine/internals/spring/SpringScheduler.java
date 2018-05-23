package net.cpollet.scheduler.engine.internals.spring;

import com.google.common.collect.ImmutableCollection;
import lombok.extern.slf4j.Slf4j;
import net.cpollet.scheduler.engine.api.ExecutionContext;
import net.cpollet.scheduler.engine.api.ExecutionId;
import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.ExecutionResult;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Status;
import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.api.Scheduler;
import net.cpollet.scheduler.engine.internals.DynamicExecutableJobFactory;
import net.cpollet.scheduler.engine.internals.ExecutableJobFactory;
import net.cpollet.scheduler.engine.internals.ExecutableJobStoreAdapter;
import net.cpollet.scheduler.engine.internals.Store;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;
import org.slf4j.MDC;
import org.springframework.scheduling.TaskScheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Slf4j
public class SpringScheduler implements Scheduler {
    private final TaskScheduler taskScheduler;
    private final Store<ExecutableJob, JobId> jobStore;
    private final ExecutableJobFactory jobFactory;
    private final Map<JobId, ScheduledFuture<?>> scheduledJobs;

    public SpringScheduler(TaskScheduler taskScheduler, Store<ExecutableJob, JobId> executableJobStore, ExecutableJobFactory jobFactory) {
        this.taskScheduler = taskScheduler;
        this.jobStore = executableJobStore;
        this.jobFactory = jobFactory;
        this.scheduledJobs = new HashMap<>();
    }

    @Override
    public Job schedule(String cronExpression, String type, Map<String, List<String>> parameters) {
        return null;
    }

    @Override
    public Job schedule(Long period, PeriodicTrigger.Unit unit, String type, Map<String, List<String>> parameters) {
        SpringPeriodicTrigger trigger = new SpringPeriodicTrigger(period, unit);

        ExecutableJob job = jobFactory.create(
                type,
                parameters,
                trigger
        );

        jobStore.save(job);

        return start(job.getJobId());
    }

    @Override
    public Job delete(JobId jobId) {
        Job job = stop(jobId);

        jobStore.delete(jobId);
        job.setStatus(Status.STOPPED);

        return job;
    }

    @Override
    public Job stop(JobId jobId) {
        ExecutableJob job = jobStore.get(jobId);

        if (job.getStatus() == Status.STOPPED) {
            return job;
        }

        scheduledJobs.get(jobId).cancel(true);
        scheduledJobs.remove(jobId);

        job.setStatus(Status.STOPPED);
        jobStore.save(job);

        return job;
    }

    @Override
    public Job start(JobId jobId) {
        ExecutableJob job = jobStore.get(jobId);

        if (job.getStatus() == Status.RUNNING) {
            return job;
        }

        final Scheduler scheduler = this;
        Runnable runnable = () -> {
            MDC.put("JobID", job.getJobId().toString());
            ExecutionContext executionContext = new ExecutionContext(new ExecutionId(), LocalDateTime.now(), scheduler);
            ExecutionResult execution = job.execute(executionContext);
        };

        // FIXME: can we have an actual result here?
        ScheduledFuture<?> future = taskScheduler.schedule(runnable, ((SpringTrigger) job.getTrigger()).trigger());
        scheduledJobs.put(job.getJobId(), future);

        job.setStatus(Status.RUNNING);

        jobStore.save(job);
        return job;
    }

    @Override
    public Collection<Job> allJobs() {
        return Collections.unmodifiableCollection(jobStore.getAll());
    }

    @Override
    public Set<String> validJobTypes() {
        return Collections.unmodifiableSet(jobFactory.getValidJobTypes());
    }
}
