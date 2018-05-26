package net.cpollet.scheduler.engine.internals.spring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.internals.AbstractTrigger;
import org.springframework.scheduling.Trigger;

@Getter
@AllArgsConstructor
public class SpringPeriodicTrigger extends SpringTrigger implements PeriodicTrigger {
    private final Long period;
    private final Unit unit;

    @Override
    public Trigger trigger() {
        return new org.springframework.scheduling.support.PeriodicTrigger(unit.toMilliseconds(period));
    }
}
