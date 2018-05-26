package net.cpollet.scheduler.engine.internals.spring;

import net.cpollet.scheduler.engine.api.CronTrigger;
import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.engine.internals.AbstractTrigger;

public abstract class SpringTrigger extends AbstractTrigger implements Trigger {
    public static SpringTrigger from(Trigger trigger) {
        switch (trigger.getType()) {
            case CRON:
                return from((CronTrigger) trigger);
            case PERIODIC:
                return from((PeriodicTrigger) trigger);
        }

        throw new IllegalArgumentException("Unsupported trigger type " + trigger.getType());
    }

    private static SpringTrigger from(CronTrigger trigger) {
        return new SpringCronTrigger(trigger.getExpression());
    }

    private static SpringTrigger from(PeriodicTrigger trigger) {
        return new SpringPeriodicTrigger(trigger.getPeriod(), trigger.getUnit());
    }

    abstract org.springframework.scheduling.Trigger trigger();
}
