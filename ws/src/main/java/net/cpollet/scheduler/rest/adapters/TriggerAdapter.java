package net.cpollet.scheduler.rest.adapters;


import net.cpollet.scheduler.engine.api.CronTrigger;
import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.rest.data.Rate;

public abstract class TriggerAdapter {
    public static TriggerAdapter adapt(Trigger trigger) {
        if (trigger instanceof PeriodicTrigger) {
            return adapt((PeriodicTrigger) trigger);
        }
        if (trigger instanceof CronTrigger) {
            return adapt((CronTrigger) trigger);
        }
        throw new IllegalArgumentException();
    }

    private static TriggerAdapter adapt(PeriodicTrigger trigger) {
        return new TriggerAdapter() {
            @Override
            public Rate getRate() {
                switch (trigger.getUnit()) {
                    case MILLISECOND:
                        return new Rate(trigger.getPeriod(), Rate.Unit.MILLISECOND);
                    case SECOND:
                        return new Rate(trigger.getPeriod(), Rate.Unit.SECOND);
                    case MINUTE:
                        return new Rate(trigger.getPeriod(), Rate.Unit.MINUTE);
                    case HOUR:
                        return new Rate(trigger.getPeriod(), Rate.Unit.HOUR);
                    case DAY:
                        return new Rate(trigger.getPeriod(), Rate.Unit.DAY);
                    case WEEK:
                        return new Rate(trigger.getPeriod(), Rate.Unit.WEEK);
                    case MONTH:
                        return new Rate(trigger.getPeriod(), Rate.Unit.MONTH);
                }
                throw new IllegalArgumentException("Unit " + trigger.getUnit() + " is not valid");
            }
        };
    }

    private static TriggerAdapter adapt(CronTrigger trigger) {
        return new TriggerAdapter() {
            @Override
            public String getExpression() {
                return trigger.getExpression();
            }
        };
    }

    public String getExpression() {
        return null;
    }

    public Rate getRate() {
        return null;
    }
}
