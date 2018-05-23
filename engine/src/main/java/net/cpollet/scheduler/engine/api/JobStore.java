package net.cpollet.scheduler.engine.api;

import net.cpollet.scheduler.engine.internals.Store;

public interface JobStore extends Store<Job, JobId> {
}
