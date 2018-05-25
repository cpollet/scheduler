package net.cpollet.scheduler.test.stub.engine.internals.job;

import net.cpollet.scheduler.engine.api.ExecutionContext;
import net.cpollet.scheduler.engine.api.ExecutionResult;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;

import java.util.List;
import java.util.Map;

public class ExecutableJobStub extends ExecutableJob {
    public ExecutableJobStub(JobId jobId, Map<String, List<String>> parameters, Trigger trigger, Status status) {
        super(jobId, parameters, trigger, status);
    }

    @Override
    public ExecutionResult execute(ExecutionContext executionContext) {
        return null;
    }
}
