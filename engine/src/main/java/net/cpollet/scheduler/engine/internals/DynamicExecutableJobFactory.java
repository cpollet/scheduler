package net.cpollet.scheduler.engine.internals;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.scheduler.engine.api.exception.InvalidJobClassException;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.exception.JobNameNotRegisteredException;
import net.cpollet.scheduler.engine.api.Trigger;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;
import net.cpollet.scheduler.engine.internals.job.JobTypeNameReader;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class DynamicExecutableJobFactory implements ExecutableJobFactory {
    private final Map<String, Class<? extends ExecutableJob>> clazz;

    public DynamicExecutableJobFactory() {
        clazz = new HashMap<>();
    }

    public void register(String[] packagesToScan) {
        for (String packageToScan : packagesToScan) {
            scanPackage(packageToScan);
        }
    }

    private void scanPackage(String packageToScan) {
        Reflections reflections = new Reflections(packageToScan);
        Set<Class<? extends ExecutableJob>> executables = reflections.getSubTypesOf(ExecutableJob.class);

        for (Class<? extends ExecutableJob> executable : executables) {
            registerExecutableJob(executable);
        }
    }

    private void registerExecutableJob(Class<? extends ExecutableJob> executable) {
        String jobTypeName = new JobTypeNameReader(executable).read();
        clazz.put(jobTypeName, executable);
        log.info(String.format("Registered %s as %s", jobTypeName, executable.getCanonicalName()));
    }

    @Override
    public ExecutableJob create(String type, Map<String, List<String>> parameters, Trigger trigger) throws JobNameNotRegisteredException {
        if (!clazz.containsKey(type)) {
            throw new JobNameNotRegisteredException(type);
        }

        try {
            return clazz.get(type).getConstructor(JobId.class, Map.class, Trigger.class).newInstance(new JobId(), parameters, trigger);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new InvalidJobClassException(clazz.get(type), type, e);
        }
    }

    @Override
    public ExecutableJob restore(JobId jobId, String type, Map<String, List<String>> parameters, Trigger trigger) throws JobNameNotRegisteredException {
        if (!clazz.containsKey(type)) {
            throw new JobNameNotRegisteredException(type);
        }

        try {
            return clazz.get(type).getConstructor(JobId.class, Map.class, Trigger.class).newInstance(jobId, parameters, trigger);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new InvalidJobClassException(clazz.get(type), type, e);
        }
    }

    @Override
    public Set<String> getValidJobTypes() {
        return clazz.keySet();
    }
}
