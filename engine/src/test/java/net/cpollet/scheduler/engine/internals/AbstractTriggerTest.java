package net.cpollet.scheduler.engine.internals;

import net.cpollet.scheduler.engine.api.Trigger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractTriggerTest {
    @Test
    void getType_returnsCronTrigger() {
        // GIVEN
        Trigger trigger = new CronTrigger();

        // WHEN
        Trigger.Type type = trigger.getType();

        // THEN
        Assertions.assertThat(type)
                .isEqualTo(Trigger.Type.CRON);
    }

    @Test
    void getType_returnsPeriodicTrigger() {
        // GIVEN
        Trigger trigger = new PeriodicTrigger();

        // WHEN
        Trigger.Type type = trigger.getType();

        // THEN
        Assertions.assertThat(type)
                .isEqualTo(Trigger.Type.PERIODIC);
    }

    private class CronTrigger extends AbstractTrigger implements net.cpollet.scheduler.engine.api.CronTrigger {
        @Override
        public String getExpression() {
            return null;
        }
    }

    private class PeriodicTrigger extends AbstractTrigger implements net.cpollet.scheduler.engine.api.PeriodicTrigger {
        @Override
        public Long getPeriod() {
            return null;
        }

        @Override
        public Unit getUnit() {
            return null;
        }
    }

}
