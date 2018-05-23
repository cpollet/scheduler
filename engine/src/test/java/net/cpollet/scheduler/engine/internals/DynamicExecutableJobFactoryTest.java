package net.cpollet.scheduler.engine.internals;

import net.cpollet.scheduler.engine.api.InvalidJobClassException;
import net.cpollet.scheduler.engine.api.exception.JobNameNotRegisteredException;
import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;
import net.cpollet.scheduler.test.stub.engine.internals.job.DummyExecutableJobStub;
import net.cpollet.scheduler.test.stub.engine.internals.job.InvalidExecutableJobStub;
import net.cpollet.scheduler.test.stub.engine.internals.job.UnnamedJobStub;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DynamicExecutableJobFactoryTest {
    private DynamicExecutableJobFactory factory;

    @BeforeEach
    public void setup() {
        factory = new DynamicExecutableJobFactory();
        factory.register(new String[]{"net.cpollet.scheduler.test.stub.engine.internals.job"});
    }

    @Test
    public void create_returnsExecutableJob() {
        // GIVEN
        Map<String, List<String>> parameters = null;
        Trigger trigger = null;

        // WHEN
        ExecutableJob job = factory.create("Dummy", parameters, trigger);

        // THEN
        Assertions.assertThat(job)
                .isInstanceOf(DummyExecutableJobStub.class);
    }

    @Test
    public void create_throwsException_whenJobTypeNotRegistered() {
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
    public void create_throwsException_whenJobClassIsInvalid() {
        // GIVEN
        Map<String, List<String>> parameters = null;
        Trigger trigger = null;

        // WHEN
        Throwable thrown = Assertions.catchThrowable(() -> factory.create("Invalid", parameters, trigger));

        // THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(InvalidJobClassException.class)
                .hasMessage("Unable to instantiate class [" + InvalidExecutableJobStub.class.getCanonicalName() + "] for job type [Invalid]");
    }

    @Test
    public void getValidJobTypes_returnsTheListOfJobTypes() {
        // WHEN
        Set<String> jobTypes = factory.getValidJobTypes();

        // THEN
        Assertions.assertThat(jobTypes)
                .containsExactlyInAnyOrder(
                        "Dummy",
                        "Invalid",
                        UnnamedJobStub.class.getCanonicalName()
                );
    }
}
