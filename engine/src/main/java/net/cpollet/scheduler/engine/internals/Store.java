package net.cpollet.scheduler.engine.internals;

import java.util.Collection;

public interface Store<T,K> {
    void save(T job);

    void delete(K jobId);

    T get(K jobId);

    Collection<T> getAll();
}
