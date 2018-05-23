package net.cpollet.scheduler.test.stub.engine.api;

import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.JobStore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JobStoreStub implements JobStore {
    private final Map<JobId, Job> store = new HashMap<>();

    @Override
    public void save(Job job) {
        store.put(job.getJobId(), job);
    }

    @Override
    public void delete(JobId jobId) {
        store.remove(jobId);
    }

    @Override
    public Job get(JobId jobId) {
        return store.get(jobId);
    }

    @Override
    public Collection<Job> getAll() {
        return store.values();
    }
}
