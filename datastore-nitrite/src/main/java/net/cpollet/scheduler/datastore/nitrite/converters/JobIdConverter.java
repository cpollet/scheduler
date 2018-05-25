package net.cpollet.scheduler.datastore.nitrite.converters;

import net.cpollet.scheduler.engine.api.JobId;

public class JobIdConverter implements Converter<JobId, String> {
    @Override
    public String convert(JobId from) {
        return from.toString();
    }

    @Override
    public JobId convertBack(String from) {
        return new JobId(from);
    }
}
