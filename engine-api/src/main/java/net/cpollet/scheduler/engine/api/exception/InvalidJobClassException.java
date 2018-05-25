package net.cpollet.scheduler.engine.api.exception;

public class InvalidJobClassException extends RuntimeException {
    public InvalidJobClassException(Class<?> jobClass, String type, Throwable e) {
        super(String.format("Unable to instantiate class [%s] for job type [%s]", jobClass.getCanonicalName(), type), e);
    }
}
