package net.cpollet.scheduler.datastore.nitrite.converters;

import net.cpollet.scheduler.engine.api.JobId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JobIdConverterTest {
    private JobIdConverter converter;

    @BeforeEach
    public void setup() {
        converter = new JobIdConverter();
    }

    @Test
    public void convert() {
        // GIVEN
        JobId jobId = new JobId();

        // WHEN
        String converted = converter.convert(jobId);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo(jobId.toString());
    }

    @Test
    public void convertBack() {
        // GIVEN
        JobId expectedJobId = new JobId();
        String jobId = expectedJobId.toString();

        // WHEN
        JobId converted = converter.convertBack(jobId);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo(expectedJobId);
    }
}
