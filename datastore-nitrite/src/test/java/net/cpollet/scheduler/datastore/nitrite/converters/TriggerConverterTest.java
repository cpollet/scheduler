package net.cpollet.scheduler.datastore.nitrite.converters;

import net.cpollet.scheduler.datastore.nitrite.entities.internal.NitriteTrigger;
import net.cpollet.scheduler.engine.api.CronTrigger;
import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.api.Trigger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TriggerConverterTest {
    private TriggerConverter converter;

    @BeforeEach
    public void setup() {
        converter = new TriggerConverter(
                new IdentityConverter<>(),
                new TriggerUnitConverter(),
                new IdentityConverter<>()
        );
    }

    @Test
    public void convert_periodicTrigger_1() {
        // GIVEN
        Long period = 1L;

        PeriodicTrigger trigger = Mockito.mock(PeriodicTrigger.class);
        Mockito.when(trigger.getType()).thenReturn(Trigger.Type.PERIODIC);
        Mockito.when(trigger.getPeriod()).thenReturn(period);
        Mockito.when(trigger.getUnit()).thenReturn(PeriodicTrigger.Unit.SECOND);

        // WHEN
        NitriteTrigger converted = converter.convert(trigger);

        // THEN
        Assertions.assertThat(converted)
                .hasFieldOrPropertyWithValue("type", "PERIODIC")
                .hasFieldOrPropertyWithValue("period", period)
                .hasFieldOrPropertyWithValue("unit", "SECOND");
    }

    @Test
    public void convert_periodicTrigger_2() {
        // GIVEN
        Long period = 2L;

        PeriodicTrigger trigger = Mockito.mock(PeriodicTrigger.class);
        Mockito.when(trigger.getType()).thenReturn(Trigger.Type.PERIODIC);
        Mockito.when(trigger.getPeriod()).thenReturn(period);
        Mockito.when(trigger.getUnit()).thenReturn(PeriodicTrigger.Unit.MINUTE);

        // WHEN
        NitriteTrigger converted = converter.convert(trigger);

        // THEN
        Assertions.assertThat(converted)
                .hasFieldOrPropertyWithValue("type", "PERIODIC")
                .hasFieldOrPropertyWithValue("period", period)
                .hasFieldOrPropertyWithValue("unit", "MINUTE");
    }

    @Test
    public void convert_cronTrigger() {
        // GIVEN
        String cronExpression = "* * * * * *";

        CronTrigger trigger = Mockito.mock(CronTrigger.class);
        Mockito.when(trigger.getType()).thenReturn(Trigger.Type.CRON);
        Mockito.when(trigger.getExpression()).thenReturn(cronExpression);

        // WHEN
        NitriteTrigger converted = converter.convert(trigger);

        // THEN
        Assertions.assertThat(converted)
                .hasFieldOrPropertyWithValue("type", "CRON")
                .hasFieldOrPropertyWithValue("cronExpression", cronExpression);
    }

    @Test
    public void convertBack_periodicTrigger() {
        // GIVEN
        Long period = 1L;

        NitriteTrigger trigger = Mockito.mock(NitriteTrigger.class);
        Mockito.when(trigger.getType()).thenReturn("PERIODIC");
        Mockito.when(trigger.getPeriod()).thenReturn(period);
        Mockito.when(trigger.getUnit()).thenReturn("SECOND");

        // WHEN
        Trigger converted = converter.convertBack(trigger);

        // THEN
        Assertions.assertThat(converted)
                .isInstanceOf(PeriodicTrigger.class)
                .hasFieldOrPropertyWithValue("type", Trigger.Type.PERIODIC)
                .hasFieldOrPropertyWithValue("period", period)
                .hasFieldOrPropertyWithValue("unit", PeriodicTrigger.Unit.SECOND);
    }

    @Test
    public void convertBack_cronTrigger() {
        // GIVEN
        String cronExpression = "* * * * * *";

        NitriteTrigger trigger = Mockito.mock(NitriteTrigger.class);
        Mockito.when(trigger.getType()).thenReturn("CRON");
        Mockito.when(trigger.getCronExpression()).thenReturn(cronExpression);

        // WHEN
        Trigger converted = converter.convertBack(trigger);

        // THEN
        Assertions.assertThat(converted)
                .isInstanceOf(CronTrigger.class)
                .hasFieldOrPropertyWithValue("type", Trigger.Type.CRON)
                .hasFieldOrPropertyWithValue("expression", cronExpression);
    }
}
