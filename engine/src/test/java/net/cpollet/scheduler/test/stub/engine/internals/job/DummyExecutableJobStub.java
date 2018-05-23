package net.cpollet.scheduler.test.stub.engine.internals.job;

import net.cpollet.scheduler.engine.api.ExecutionContext;
import net.cpollet.scheduler.engine.api.ExecutionResult;
import net.cpollet.scheduler.engine.api.Status;
import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;
import net.cpollet.scheduler.engine.internals.job.JobTypeName;

import java.util.List;
import java.util.Map;

@JobTypeName("Dummy")
public class DummyExecutableJobStub extends ExecutableJob {
    public DummyExecutableJobStub(Map<String, List<String>> parameters, Trigger trigger) {
        super(parameters, trigger, Status.STOPPED);
    }

    @Override
    public ExecutionResult execute(ExecutionContext executionContext) {
        return null;
    }
}
