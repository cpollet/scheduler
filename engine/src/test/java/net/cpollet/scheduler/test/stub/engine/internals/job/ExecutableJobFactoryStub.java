package net.cpollet.scheduler.test.stub.engine.internals.job;

import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.engine.api.exception.JobNameNotRegisteredException;
import net.cpollet.scheduler.engine.internals.ExecutableJobFactory;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExecutableJobFactoryStub implements ExecutableJobFactory {
    @Override
    public ExecutableJob create(String type, Map<String, List<String>> parameters, Trigger trigger) {
        return new DummyExecutableJobStub(new JobId(), parameters, trigger);
    }

    @Override
    public ExecutableJob restore(JobId jobId, String type, Map<String, List<String>> parameters, Trigger trigger) throws JobNameNotRegisteredException {
        return new DummyExecutableJobStub(jobId, parameters, trigger);
    }

    @Override
    public Set<String> getValidJobTypes() {
        return Collections.emptySet();
    }
}
