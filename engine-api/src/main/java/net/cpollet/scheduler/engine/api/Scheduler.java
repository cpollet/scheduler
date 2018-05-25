package net.cpollet.scheduler.engine.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Scheduler {
    Job schedule(String cronExpression, String type, Map<String, List<String>> parameters);

    Job schedule(Long period, PeriodicTrigger.Unit unit, String type, Map<String, List<String>> parameters);

    Job delete(JobId jobId);

    Job stop(JobId jobId);

    Job start(JobId jobId);

    Collection<Job> allJobs();

    Set<String> validJobTypes();
}
