package net.cpollet.scheduler.engine.api;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode
public class ExecutionId {
    private final String id;

    public ExecutionId() {
        id = UUID.randomUUID().toString();
    }

    public String toString() {
        return "ExecutionId:" + id;
    }
}
