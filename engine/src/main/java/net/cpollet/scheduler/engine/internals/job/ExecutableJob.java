package net.cpollet.scheduler.engine.internals.job;

import net.cpollet.scheduler.engine.api.ExecutionContext;
import net.cpollet.scheduler.engine.api.ExecutionResult;
import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Trigger;

import java.util.List;
import java.util.Map;

public abstract class ExecutableJob implements Job {
    protected final JobId jobId;
    protected final Trigger trigger;
    protected final Map<String, List<String>> parameters;
    protected Status status;
    private final JobTypeNameReader jobTypeNameReader;

    public ExecutableJob(Map<String, List<String>> parameters, Trigger trigger, Status status) {
        this.jobId = new JobId();
        this.parameters = parameters;
        this.trigger = trigger;
        this.status = status;
        this.jobTypeNameReader = new JobTypeNameReader(this.getClass());
    }

    public String getType() {
        return jobTypeNameReader.read();
    }

    public abstract ExecutionResult execute(ExecutionContext executionContext);

    @Override
    public JobId getJobId() {
        return jobId;
    }

    @Override
    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public Map<String, List<String>> getParameters() {
        return parameters;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
