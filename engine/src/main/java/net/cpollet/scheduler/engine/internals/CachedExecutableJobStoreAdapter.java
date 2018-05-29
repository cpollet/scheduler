package net.cpollet.scheduler.engine.internals;

import lombok.AllArgsConstructor;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Store;
import net.cpollet.scheduler.engine.api.exception.ElementNotFoundException;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class CachedExecutableJobStoreAdapter implements Store<ExecutableJob, JobId> {
    private final Store<ExecutableJob, JobId> delegate;
    private final Map<JobId, ExecutableJob> cache = new HashMap<>();

    @Override
    public void save(ExecutableJob job) {
        delegate.save(job);
        cache.put(job.getJobId(), job);
    }

    @Override
    public void delete(JobId jobId) {
        delegate.delete(jobId);
        cache.remove(jobId);
    }

    @Override
    public ExecutableJob get(JobId jobId) throws ElementNotFoundException {
        if (cache.containsKey(jobId)) {
            return cache.get(jobId);
        }

        ExecutableJob job = delegate.get(jobId);
        cache.put(jobId, job);

        return job;
    }

    @Override
    public Collection<ExecutableJob> getAll() {
        Collection<ExecutableJob> jobs = delegate.getAll();

        jobs.forEach(j -> cache.put(j.getJobId(), j));

        return jobs;
    }
}
