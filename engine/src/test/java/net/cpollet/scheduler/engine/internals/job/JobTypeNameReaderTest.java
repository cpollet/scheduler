package net.cpollet.scheduler.engine.internals.job;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JobTypeNameReaderTest {
    @Test
    void read_returnsJobTypeNameAnnotationValue_whenPresent() {
        // GIVEN
        JobTypeNameReader reader = new JobTypeNameReader(Named.class);

        // WHEN
        String name = reader.read();

        // THEN
        Assertions.assertThat(name)
                .isEqualTo("Named");
    }

    @Test
    void read_returnsCanonicalClassName_whenAnnotationNotPresent() {
        // GIVEN
        JobTypeNameReader reader = new JobTypeNameReader(Unnamed.class);

        // WHEN
        String name = reader.read();

        // THEN
        Assertions.assertThat(name)
                .isEqualTo(Unnamed.class.getCanonicalName());
    }

    @JobTypeName("Named")
    private class Named {
        // nothing
    }

    private class Unnamed {
        // nothing
    }
}
