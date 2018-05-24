package net.cpollet.scheduler.engine.internals;

import net.cpollet.scheduler.engine.api.exception.ElementNotFoundException;

import java.util.Collection;

public interface Store<T, K> {
    void save(T job);

    void delete(K jobId);

    T get(K jobId) throws ElementNotFoundException;

    Collection<T> getAll();
}
