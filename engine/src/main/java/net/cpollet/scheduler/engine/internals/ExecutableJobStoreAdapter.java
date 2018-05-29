package net.cpollet.scheduler.engine.internals;

import lombok.AllArgsConstructor;
import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.JobStore;
import net.cpollet.scheduler.engine.api.Store;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ExecutableJobStoreAdapter implements Store<ExecutableJob, JobId> {
    private final JobStore jobStore;
    private final ExecutableJobFactory jobFactory;

    @Override
    public void save(ExecutableJob job) {
        jobStore.save(job);
    }

    @Override
    public void delete(JobId jobId) {
        jobStore.delete(jobId);
    }

    @Override
    public ExecutableJob get(JobId jobId) {
        return makeExecutable(jobStore.get(jobId));
    }

    private ExecutableJob makeExecutable(Job job) {
        ExecutableJob executableJob = jobFactory.restore(job.getJobId(), job.getType(), job.getParameters(), job.getTrigger());
        executableJob.setStatus(job.getStatus());

        return executableJob;
    }

    @Override
    public Collection<ExecutableJob> getAll() {
        return jobStore.getAll().stream()
                .map(this::makeExecutable)
                .collect(Collectors.toList());
    }
}
