package net.cpollet.scheduler.engine.api;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
public class JobId {
    private final String PREFIX = "JobId:";
    private final String id;

    public JobId() {
        this.id = UUID.randomUUID().toString();
    }

    public JobId(String jobId) {
        if (!jobId.startsWith(PREFIX)) {
            throw new IllegalArgumentException(String.format("[%s] is not a valid JobId", jobId));
        }

        this.id = jobId.substring(PREFIX.length());
    }

    public String toString() {
        return PREFIX + id;
    }
}
