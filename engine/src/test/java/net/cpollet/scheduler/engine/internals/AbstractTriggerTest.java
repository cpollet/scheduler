package net.cpollet.scheduler.engine.internals;

import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.test.stub.engine.api.CronTriggerStub;
import net.cpollet.scheduler.test.stub.engine.api.PeriodicTriggerStub;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractTriggerTest {
    @Test
    public void getType_returnsCronTrigger() {
        // GIVEN
        Trigger trigger = new CronTriggerStub();

        // WHEN
        Trigger.Type type = trigger.getType();

        // THEN
        Assertions.assertThat(type)
                .isEqualTo(Trigger.Type.CRON);
    }

    @Test
    public void getType_returnsPeriodicTrigger() {
        // GIVEN
        Trigger trigger = new PeriodicTriggerStub();

        // WHEN
        Trigger.Type type = trigger.getType();

        // THEN
        Assertions.assertThat(type)
                .isEqualTo(Trigger.Type.PERIODIC);
    }
}
