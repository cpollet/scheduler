package net.cpollet.scheduler.engine.internals;

import net.cpollet.scheduler.engine.api.Trigger;

public class AbstractTrigger implements Trigger {
    @Override
    public Type getType() {
        return Type.find(this.getClass());
    }
}
