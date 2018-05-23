package net.cpollet.scheduler.test.stub.engine.api;

import net.cpollet.scheduler.engine.api.ExecutionContext;
import net.cpollet.scheduler.engine.api.ExecutionResult;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Status;
import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;

import java.util.List;
import java.util.Map;

public class JobStub extends ExecutableJob {
    public JobStub(Map<String, List<String>> parameters, Trigger trigger) {
        super(parameters, trigger, null);
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
    public void setStatus(Status status) {

    }

    @Override
    public String getType() {
        return "Dummy";
    }

    @Override
    public ExecutionResult execute(ExecutionContext executionContext) {
        return null;
    }
}
