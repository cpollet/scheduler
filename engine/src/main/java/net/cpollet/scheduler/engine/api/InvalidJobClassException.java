package net.cpollet.scheduler.engine.api;

import net.cpollet.scheduler.engine.internals.job.ExecutableJob;

public class InvalidJobClassException extends RuntimeException {
    public InvalidJobClassException(Class<? extends ExecutableJob> jobClass, String type, Throwable e) {
        super(String.format("Unable to instantiate class [%s] for job type [%s]", jobClass.getCanonicalName(), type), e);
    }
}
