package net.cpollet.scheduler.engine.internals;

import net.cpollet.scheduler.engine.api.ExecutionContext;
import net.cpollet.scheduler.engine.api.ExecutionResult;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.exception.InvalidJobClassException;
import net.cpollet.scheduler.engine.api.exception.JobNameNotRegisteredException;
import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;
import net.cpollet.scheduler.engine.internals.job.JobTypeName;
import net.cpollet.scheduler.engine.internals.job.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

class DynamicExecutableJobFactoryTest {
    private DynamicExecutableJobFactory factory;

    @BeforeEach
    void setup() {
        factory = new DynamicExecutableJobFactory();
        factory.register(new String[]{getClass().getPackage().getName()});
    }

    @Test
    void create_returnsExecutableJob() {
        // GIVEN
        Map<String, List<String>> parameters = null;
        Trigger trigger = null;

        // WHEN
        ExecutableJob job = factory.create("Named", parameters, trigger);

        // THEN
        Assertions.assertThat(job)
                .isInstanceOf(NamedExecutableJob.class);
    }

    @Test
    void create_throwsException_whenJobTypeNotRegistered() {
        // GIVEN
        Map<String, List<String>> parameters = null;
        Trigger trigger = null;

        // WHEN
        Throwable thrown = Assertions.catchThrowable(() -> factory.create("NotRegistered", parameters, trigger));

        // THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(JobNameNotRegisteredException.class)
                .hasMessage("Job name [NotRegistered] not found");
    }

    @Test
    void create_throwsException_whenJobClassIsInvalid() {
        // GIVEN
        Map<String, List<String>> parameters = null;
        Trigger trigger = null;

        // WHEN
        Throwable thrown = Assertions.catchThrowable(() -> factory.create("Invalid", parameters, trigger));

        // THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(InvalidJobClassException.class)
                .hasMessage("Unable to instantiate class [" + InvalidExecutableJob.class.getCanonicalName() + "] for job type [Invalid]");
    }

    @Test
    void getValidJobTypes_returnsTheListOfJobTypes() {
        // WHEN
        Set<String> jobTypes = factory.getValidJobTypes();

        // THEN
        Assertions.assertThat(jobTypes)
                .containsExactlyInAnyOrder(
                        "Named",
                        "Invalid",
                        UnnamedExecutableJob.class.getCanonicalName(),
                        "Logger" // probably we want to get rid of it here...
                );
    }

    @JobTypeName("Named")
    private static class NamedExecutableJob extends ExecutableJob {
        public NamedExecutableJob(JobId jobId, Map<String, List<String>> parameters, Trigger trigger) {
            super(jobId, parameters, trigger, Status.STOPPED);
        }

        @Override
        public ExecutionResult execute(ExecutionContext executionContext) {
            return null;
        }
    }

    @JobTypeName("Invalid")
    private static class InvalidExecutableJob extends ExecutableJob {
        public InvalidExecutableJob(JobId jobId, Map<String, List<String>> parameters, Trigger trigger, Status status) {
            super(jobId, parameters, trigger, status);
        }

        @Override
        public ExecutionResult execute(ExecutionContext executionContext) {
            return null;
        }
    }

    private static class UnnamedExecutableJob extends ExecutableJob {
        public UnnamedExecutableJob(JobId jobId, Map<String, List<String>> parameters, Trigger trigger) {
            super(jobId, parameters, trigger, Status.STOPPED);
        }

        @Override
        public ExecutionResult execute(ExecutionContext executionContext) {
            return null;
        }
    }
}
