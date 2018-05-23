package net.cpollet.scheduler.engine.internals.spring;

import net.cpollet.scheduler.engine.api.Trigger;

public interface SpringTrigger extends Trigger {
    org.springframework.scheduling.Trigger trigger();
}
