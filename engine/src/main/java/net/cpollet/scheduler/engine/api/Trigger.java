package net.cpollet.scheduler.engine.api;

import java.util.Arrays;

// FIXME could be an abstract class
public interface Trigger {
    Type getType(); // FIXME is that still useful?

    enum Type {
        CRON(CronTrigger.class),
        PERIODIC(PeriodicTrigger.class);

        private final Class<? extends Trigger> clazz;

        Type(Class<? extends Trigger> clazz) {
            this.clazz = clazz;
        }

        public static Type find(Class<?> clazz) {
            return Arrays.stream(values())
                    .filter(v -> v.clazz.isAssignableFrom(clazz))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No trigger type found for " + clazz));
        }
    }


}
