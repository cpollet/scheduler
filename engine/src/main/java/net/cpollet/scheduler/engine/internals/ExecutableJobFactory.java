package net.cpollet.scheduler.engine.internals;

import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ExecutableJobFactory {
    ExecutableJob create(String type, Map<String, List<String>> parameters, Trigger trigger);

    Set<String> getValidJobTypes();
}
