package net.cpollet.scheduler.engine.internals.spring;

import net.cpollet.scheduler.engine.api.CronTrigger;
import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.api.Trigger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class SpringTriggerTest {
    private final static Map<Trigger.Type, Trigger> triggers = new HashMap<Trigger.Type, Trigger>() {{
        put(Trigger.Type.CRON, Mockito.when(Mockito.mock(CronTrigger.class).getType()).thenReturn(Trigger.Type.CRON).getMock());
        put(Trigger.Type.PERIODIC, Mockito.when(Mockito.mock(PeriodicTrigger.class).getType()).thenReturn(Trigger.Type.PERIODIC).getMock());
    }};

    @Test
    public void from_worksForAllEnums() {
        for (Trigger.Type type : Trigger.Type.values()) {
            SpringTrigger result = SpringTrigger.from(trigger(type));

            Assertions.assertThat(result)
                    .isNotNull();
        }
    }

    private Trigger trigger(Trigger.Type type) {
        Trigger trigger = triggers.get(type);

        Assertions.assertThat(trigger)
                .overridingErrorMessage("Test misconfigured, trigger of type " + type + " not found in triggers map")
                .isNotNull();

        return trigger;
    }
}
