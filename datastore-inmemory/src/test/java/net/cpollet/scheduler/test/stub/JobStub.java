package net.cpollet.scheduler.test.stub;

import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Trigger;

import java.util.List;
import java.util.Map;

public class JobStub implements Job {
    private final JobId jobId;

    public JobStub() {
        jobId = new JobId();
    }

    @Override
    public JobId getJobId() {
        return jobId;
    }

    @Override
    public Trigger getTrigger() {
        return null;
    }

    @Override
    public Map<String, List<String>> getParameters() {
        return null;
    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }
}
