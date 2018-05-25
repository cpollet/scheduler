package net.cpollet.scheduler.datastore.nitrite.converters;

import net.cpollet.scheduler.datastore.nitrite.entities.internal.NitriteJob;
import net.cpollet.scheduler.datastore.nitrite.entities.internal.NitriteTrigger;
import net.cpollet.scheduler.engine.api.CronTrigger;
import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.api.Trigger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobConverterTest {
    private JobConverter converter;

    @BeforeEach
    public void setup() {
        converter = Converters.job();
    }

    @Test
    public void convert() {
        // GIVEN
        Trigger trigger = Mockito.mock(CronTrigger.class);
        Mockito.when(trigger.getType()).thenReturn(Trigger.Type.CRON);

        Job job = Mockito.mock(Job.class);
        Mockito.when(job.getJobId()).thenReturn(new JobId());
        Mockito.when(job.getType()).thenReturn("JobType");
        Mockito.when(job.getStatus()).thenReturn( Job.Status.RUNNING);
        Mockito.when(job.getTrigger()).thenReturn(trigger);
        Mockito.when(job.getParameters()).thenReturn(Collections.emptyMap());

        // WHEN
        NitriteJob converted = converter.convert(job);

        // THEN
        Assertions.assertThat(converted)
                // converters are unit tested on their own, we want to make sure everything is filled
                .hasNoNullFieldsOrProperties();
    }

    @Test
    public void convertBack() {
        // GIVEN
        NitriteTrigger trigger = Mockito.mock(NitriteTrigger.class);
        Mockito.when(trigger.getType()).thenReturn("PERIODIC");
        Mockito.when(trigger.getUnit()).thenReturn("SECOND");
        Mockito.when(trigger.getPeriod()).thenReturn(1L);

        NitriteJob job = Mockito.mock(NitriteJob.class);
        Mockito.when(job.getJobId()).thenReturn(new JobId().toString());
        Mockito.when(job.getType()).thenReturn("JobType");
        Mockito.when(job.getParameters()).thenReturn(Collections.emptyMap());
        Mockito.when(job.getTrigger()).thenReturn(trigger);
        Mockito.when(job.getStatus()).thenReturn("RUNNING");

        // WHEN
        Job converted = converter.convertBack(job);

        // THEN
        Assertions.assertThat(converted)
                // converters are unit tested on their own, we want to make sure everything is filled
                .hasNoNullFieldsOrProperties();
    }

    @Test
    public void convertBack_cronTrigger() {
        // GIVEN
        NitriteTrigger trigger = Mockito.mock(NitriteTrigger.class);
        Mockito.when(trigger.getType()).thenReturn("CRON");
        Mockito.when(trigger.getCronExpression()).thenReturn("* * * * * *");

        JobId jobId=new JobId();
        NitriteJob job = Mockito.mock(NitriteJob.class);
        Mockito.when(job.getJobId()).thenReturn(jobId.toString());
        Mockito.when(job.getType()).thenReturn("JobType");
        Mockito.when(job.getParameters()).thenReturn(Collections.emptyMap());
        Mockito.when(job.getTrigger()).thenReturn(trigger);
        Mockito.when(job.getStatus()).thenReturn("RUNNING");

        // WHEN
        Job converted = converter.convertBack(job);

        // THEN
        Assertions.assertThat(converted)
                .hasFieldOrPropertyWithValue("type", "JobType")
                .hasFieldOrPropertyWithValue("status", Job.Status.RUNNING)
                .hasFieldOrPropertyWithValue("parameters", Collections.emptyMap())
                .hasFieldOrPropertyWithValue("jobId", jobId);

        Assertions.assertThat(converted.getTrigger())
                .isInstanceOf(CronTrigger.class);
    }
}
