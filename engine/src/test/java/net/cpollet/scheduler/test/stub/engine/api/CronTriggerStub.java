package net.cpollet.scheduler.test.stub.engine.api;

import net.cpollet.scheduler.engine.api.CronTrigger;
import net.cpollet.scheduler.engine.internals.AbstractTrigger;

public class CronTriggerStub extends AbstractTrigger implements CronTrigger{
    @Override
    public String getExpression() {
        return null;
    }
}
