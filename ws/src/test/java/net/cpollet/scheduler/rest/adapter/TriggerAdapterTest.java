package net.cpollet.scheduler.rest.adapter;

import net.cpollet.scheduler.engine.api.CronTrigger;
import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.rest.adapters.TriggerAdapter;

public class TriggerAdapterTest {
    public static void main(String[] args) {
        TriggerAdapterTest test = new TriggerAdapterTest();
        test.adaptPeriodicTrigger();
        test.adaptCronTrigger();
        test.adaptTrigger();
    }

    public void adaptTrigger() {
        TriggerAdapter.adapt(new Trigger() {
            @Override
            public Type getType() {
                return null;
            }
        });
    }

    public void adaptCronTrigger() {
        TriggerAdapter triggerAdapter = TriggerAdapter.adapt(new CronTrigger() {
            @Override
            public String getExpression() {
                return "expression";
            }

            @Override
            public Type getType() {
                return Type.CRON;
            }
        });

        System.out.println(triggerAdapter.getRate());
        System.out.println(triggerAdapter.getExpression());
    }

    public void adaptPeriodicTrigger() {
        TriggerAdapter triggerAdapter = TriggerAdapter.adapt(new PeriodicTrigger() {
            @Override
            public Long getPeriod() {
                return 1L;
            }

            @Override
            public Unit getUnit() {
                return Unit.DAY;
            }

            @Override
            public Type getType() {
                return Type.PERIODIC;
            }
        });

        System.out.println(triggerAdapter.getRate());
        System.out.println(triggerAdapter.getExpression());
    }
}
