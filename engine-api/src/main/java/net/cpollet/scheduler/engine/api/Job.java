package net.cpollet.scheduler.engine.api;

import java.util.List;
import java.util.Map;

// FIXME could be an actual class
public interface Job {
    JobId getJobId();
    Trigger getTrigger();
    Map<String, List<String>> getParameters();
    Status getStatus();
    String getType();

    enum Status {
        RUNNING, STOPPED
    }
}