package net.cpollet.scheduler.engine.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JobIdTest {
    @Test
    void new_areEqual_whenUsingSameIds() {
        // GIVEN
        JobId existingJobId = new JobId();

        // WHEN
        JobId newJobId1 = new JobId(existingJobId.toString());
        JobId newJobId2 = new JobId(existingJobId.toString());

        // THEN
        Assertions.assertThat(newJobId1)
                .isEqualTo(newJobId2);
    }

    @Test
    void new_throwsException_whenIdIsInvalidId() {
        // GIVEN
        String invalidId="invalid";

        // WHEN
        Throwable thrown = Assertions.catchThrowable(() -> new JobId(invalidId));

        // THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[invalid] is not a valid JobId");
    }
}
