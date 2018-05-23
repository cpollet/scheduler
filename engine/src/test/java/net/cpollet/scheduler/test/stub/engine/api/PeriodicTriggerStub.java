package net.cpollet.scheduler.test.stub.engine.api;

import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.internals.AbstractTrigger;

public class PeriodicTriggerStub extends AbstractTrigger implements PeriodicTrigger {
    @Override
    public Long getPeriod() {
        return null;
    }

    @Override
    public Unit getUnit() {
        return null;
    }
}
