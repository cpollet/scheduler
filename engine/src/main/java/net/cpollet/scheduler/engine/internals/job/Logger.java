package net.cpollet.scheduler.engine.internals.job;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.scheduler.engine.api.ExecutionContext;
import net.cpollet.scheduler.engine.api.ExecutionResult;
import net.cpollet.scheduler.engine.api.Trigger;

import java.util.List;
import java.util.Map;

@Slf4j
@JobTypeName("Logger")
public class Logger extends ExecutableJob {
    public Logger(Map<String, List<String>> parameters, Trigger trigger) {
        super(parameters, trigger, Status.STOPPED);
    }

    @Override
    public ExecutionResult execute(ExecutionContext executionContext) {
        log.info(parameters.get("line").get(0));
        return new ExecutionResult(executionContext);
    }
}
