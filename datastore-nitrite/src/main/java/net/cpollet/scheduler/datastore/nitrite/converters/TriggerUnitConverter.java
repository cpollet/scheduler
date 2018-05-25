package net.cpollet.scheduler.datastore.nitrite.converters;

import net.cpollet.scheduler.engine.api.PeriodicTrigger;

public class TriggerUnitConverter implements Converter<PeriodicTrigger.Unit, String> {
    @Override
    public String convert(PeriodicTrigger.Unit from) {
        return from.name();
    }

    @Override
    public PeriodicTrigger.Unit convertBack(String from) {
        return PeriodicTrigger.Unit.valueOf(from);
    }
}
