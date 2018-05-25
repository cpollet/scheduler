package net.cpollet.scheduler.engine.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class ExecutionContext {
    private final ExecutionId executionId;
    private final LocalDateTime triggerDate;
    private final Scheduler scheduler;
}
