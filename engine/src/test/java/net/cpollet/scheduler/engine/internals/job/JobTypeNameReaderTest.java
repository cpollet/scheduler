package net.cpollet.scheduler.engine.internals.job;

import net.cpollet.scheduler.test.stub.engine.internals.job.DummyExecutableJobStub;
import net.cpollet.scheduler.test.stub.engine.internals.job.UnnamedJobStub;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class JobTypeNameReaderTest {
    @Test
    public void read_returnsJobTypeNameAnnotationValue_whenPresent() {
        // GIVEN
        JobTypeNameReader reader = new JobTypeNameReader(DummyExecutableJobStub.class);

        // WHEN
        String name = reader.read();

        // THEN
        Assertions.assertThat(name)
                .isEqualTo("Dummy");
    }

    @Test
    public void read_returnsCanonicalClassName_whenAnnotationNotPresent() {
        // GIVEN
        JobTypeNameReader reader = new JobTypeNameReader(UnnamedJobStub.class);

        // WHEN
        String name = reader.read();

        // THEN
        Assertions.assertThat(name)
                .isEqualTo(UnnamedJobStub.class.getCanonicalName());
    }
}
