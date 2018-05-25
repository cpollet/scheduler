package net.cpollet.scheduler.engine.api;

// FIXME could be an actual class
public interface PeriodicTrigger extends Trigger {
    Long getPeriod();

    Unit getUnit();

    enum Unit {
        MILLISECOND(1L),
        SECOND(MILLISECOND.coeff * 1000),
        MINUTE(SECOND.coeff * 60),
        HOUR(MINUTE.coeff * 60),
        DAY(HOUR.coeff * 24),
        WEEK(DAY.coeff * 7),
        MONTH(WEEK.coeff * 4);

        private final Long coeff;

        Unit(Long coeff) {
            this.coeff = coeff;
        }

        public Long toMilliseconds(Long period) {
            return period * coeff;
        }
    }
}
