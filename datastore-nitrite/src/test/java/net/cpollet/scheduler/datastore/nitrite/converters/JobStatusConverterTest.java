package net.cpollet.scheduler.datastore.nitrite.converters;

import net.cpollet.scheduler.engine.api.Job;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JobStatusConverterTest {
    private JobStatusConverter converter;

    @BeforeEach
    public void setup() {
        converter = new JobStatusConverter();
    }

    @Test
    public void convert_RUNNING() {
        // GIVEN
        Job.Status status = Job.Status.RUNNING;

        // WHEN
        String converted = converter.convert(status);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo("RUNNING");
    }

    @Test
    public void convert_STOPPED() {
        // GIVEN
        Job.Status status = Job.Status.STOPPED;

        // WHEN
        String converted = converter.convert(status);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo("STOPPED");
    }

    @Test
    public void convertBack_RUNNING() {
        // GIVEN
        String status = "RUNNING";

        // WHEN
        Job.Status converted = converter.convertBack(status);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo(Job.Status.RUNNING);
    }

    @Test
    public void convertBack_STOPPED() {
        // GIVEN
        String status = "STOPPED";

        // WHEN
        Job.Status converted = converter.convertBack(status);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo(Job.Status.STOPPED);
    }
}
