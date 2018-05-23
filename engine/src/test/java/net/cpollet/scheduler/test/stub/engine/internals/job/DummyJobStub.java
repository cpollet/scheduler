package net.cpollet.scheduler.test.stub.engine.internals.job;

import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Status;
import net.cpollet.scheduler.engine.api.Trigger;

import java.util.List;
import java.util.Map;

public class DummyJobStub implements Job {
    private final JobId jobId = new JobId();

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
    public void setStatus(Status status) {

    }

    @Override
    public String getType() {
        return null;
    }
}
