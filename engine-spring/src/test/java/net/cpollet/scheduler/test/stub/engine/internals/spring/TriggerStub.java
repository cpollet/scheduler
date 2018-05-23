package net.cpollet.scheduler.test.stub.engine.internals.spring;

import org.springframework.lang.Nullable;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.util.Date;

public class TriggerStub implements Trigger{
    @Nullable
    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        return null;
    }
}
