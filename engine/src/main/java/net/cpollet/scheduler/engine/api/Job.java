package net.cpollet.scheduler.engine.api;

import java.util.List;
import java.util.Map;

public interface Job {
    JobId getJobId();
    Trigger getTrigger();
    Map<String, List<String>> getParameters();
    Status getStatus();
    void setStatus(Status status);
    String getType();
}