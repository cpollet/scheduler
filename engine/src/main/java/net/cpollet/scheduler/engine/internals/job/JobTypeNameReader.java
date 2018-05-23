package net.cpollet.scheduler.engine.internals.job;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JobTypeNameReader {
    private final Class<?> clazz;

    public String read() {
        if (clazz.isAnnotationPresent(JobTypeName.class)) {
            return clazz.getAnnotation(JobTypeName.class).value();
        }

        return clazz.getCanonicalName();
    }
}
