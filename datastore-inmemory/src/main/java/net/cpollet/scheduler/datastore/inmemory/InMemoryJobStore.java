package net.cpollet.scheduler.datastore.inmemory;

import net.cpollet.scheduler.engine.api.exception.ElementNotFoundException;
import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.JobStore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryJobStore implements JobStore {
    private final Map<JobId, Job> jobs;

    public InMemoryJobStore() {
        this.jobs = new HashMap<>();
    }

    @Override
    public void save(Job job) {
        jobs.put(job.getJobId(), job);
    }

    @Override
    public void delete(JobId jobId) {
        jobs.remove(jobId);
    }

    @Override
    public Job get(JobId jobId) {
        if (!jobs.containsKey(jobId)) {
            throw new ElementNotFoundException(jobId.toString());
        }
        return jobs.get(jobId);
    }

    @Override
    public Collection<Job> getAll() {
        return jobs.values();
    }
}
