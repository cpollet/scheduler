package net.cpollet.scheduler.test.stub.engine.internals.job;

import net.cpollet.scheduler.engine.api.ExecutionContext;
import net.cpollet.scheduler.engine.api.ExecutionResult;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;

import java.util.List;
import java.util.Map;

public class UnnamedJobStub extends ExecutableJob {
    public UnnamedJobStub(JobId jobId, Map<String, List<String>> parameters, Trigger trigger) {
        super(jobId, parameters, trigger, Status.STOPPED);
    }

    @Override
    public ExecutionResult execute(ExecutionContext executionContext) {
        return null;
    }
}
