package net.cpollet.scheduler.engine.api.exception;

public class JobNameNotRegisteredException extends RuntimeException {
    public JobNameNotRegisteredException(String jobName) {
        super(String.format("Job name [%s] not found", jobName));
    }
}
