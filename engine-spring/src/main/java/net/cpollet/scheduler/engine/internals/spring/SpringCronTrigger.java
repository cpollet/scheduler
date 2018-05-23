package net.cpollet.scheduler.engine.internals.spring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.cpollet.scheduler.engine.api.CronTrigger;
import net.cpollet.scheduler.engine.internals.AbstractTrigger;
import org.springframework.scheduling.Trigger;

@AllArgsConstructor
@Getter
public class SpringCronTrigger extends AbstractTrigger implements SpringTrigger, CronTrigger {
    private final String expression;

    @Override
    public Trigger trigger() {
        return new org.springframework.scheduling.support.CronTrigger(expression);
    }
}
