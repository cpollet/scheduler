package net.cpollet.scheduler.datastore.nitrite.converters;

import net.cpollet.scheduler.engine.api.Job;

public class JobStatusConverter implements Converter<Job.Status, String> {
    @Override
    public String convert(Job.Status from) {
        return from.name();
    }

    @Override
    public Job.Status convertBack(String from) {
        return Job.Status.valueOf(from);
    }
}
