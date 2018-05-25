package net.cpollet.scheduler.datastore.nitrite.converters;

import lombok.AllArgsConstructor;
import net.cpollet.scheduler.datastore.nitrite.entities.internal.NitriteTrigger;
import net.cpollet.scheduler.engine.api.CronTrigger;
import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.api.Trigger;

@AllArgsConstructor
public class TriggerConverter implements Converter<Trigger, NitriteTrigger> {
    private final Converter<Long, Long> periodConverter;
    private final TriggerUnitConverter unitConverter;
    private final Converter<String, String> cronExpressionConverter;

    @Override
    public NitriteTrigger convert(Trigger from) {
        switch (from.getType()) {
            case PERIODIC:
                return NitriteTrigger.periodic(
                        periodConverter.convert(((PeriodicTrigger) from).getPeriod()),
                        unitConverter.convert(((PeriodicTrigger) from).getUnit())
                );

            case CRON:
                return NitriteTrigger.cron(
                        cronExpressionConverter.convert(((CronTrigger) from).getExpression())
                );
        }

        throw new IllegalArgumentException("Cannot convert trigger of type " + from.getType());
    }

    @Override
    public Trigger convertBack(NitriteTrigger from) {
        switch (from.getType()) {
            case "PERIODIC":
                final Long period = periodConverter.convertBack(from.getPeriod());
                final PeriodicTrigger.Unit unit = unitConverter.convertBack(from.getUnit());

                return new PeriodicTrigger() {
                    @Override
                    public Long getPeriod() {
                        return period;
                    }

                    @Override
                    public Unit getUnit() {
                        return unit;
                    }

                    @Override
                    public Type getType() {
                        return Type.PERIODIC;
                    }
                };
            case "CRON":
                final String cronExpression = cronExpressionConverter.convertBack(from.getCronExpression());

                return new CronTrigger() {
                    @Override
                    public String getExpression() {
                        return cronExpression;
                    }

                    @Override
                    public Type getType() {
                        return Type.CRON;
                    }
                };
        }

        throw new IllegalArgumentException("Cannot convert back trigger of type " + from.getType());
    }
}
