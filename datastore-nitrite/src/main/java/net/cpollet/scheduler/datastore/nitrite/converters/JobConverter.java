package net.cpollet.scheduler.datastore.nitrite.converters;

import lombok.AllArgsConstructor;
import net.cpollet.scheduler.datastore.nitrite.entities.internal.NitriteJob;
import net.cpollet.scheduler.datastore.nitrite.entities.internal.NitriteTrigger;
import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Trigger;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class JobConverter implements Converter<Job, NitriteJob> {
    private final Converter<JobId, String> jobIdConverter;
    private final Converter<String, String> typeConverter;
    private final Converter<Map<String, List<String>>, Map<String, List<String>>> parametersConverter;
    private final Converter<Trigger, NitriteTrigger> triggerConverter;
    private final Converter<Job.Status, String> statusConverter;

    @Override
    public NitriteJob convert(Job job) {
        return new NitriteJob(
                jobIdConverter.convert(job.getJobId()),
                typeConverter.convert(job.getType()),
                parametersConverter.convert(job.getParameters()),
                triggerConverter.convert(job.getTrigger()),
                statusConverter.convert(job.getStatus())
        );
    }

    @Override
    public Job convertBack(NitriteJob from) {
        final JobId jobId = jobIdConverter.convertBack(from.getJobId());
        final Trigger trigger = triggerConverter.convertBack(from.getTrigger());
        final Map<String, List<String>> parameters = parametersConverter.convertBack(from.getParameters());
        final Job.Status status = statusConverter.convertBack(from.getStatus());
        final String type = typeConverter.convertBack(from.getType());

        return new Job() {
            @Override
            public JobId getJobId() {
                return jobId;
            }

            @Override
            public Trigger getTrigger() {
                return trigger;
            }

            @Override
            public Map<String, List<String>> getParameters() {
                return parameters;
            }

            @Override
            public Status getStatus() {
                return status;
            }

            @Override
            public String getType() {
                return type;
            }
        };
    }
}
