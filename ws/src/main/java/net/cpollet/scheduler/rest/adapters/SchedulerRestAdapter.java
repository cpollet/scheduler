package net.cpollet.scheduler.rest.adapters;

import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.api.Scheduler;
import net.cpollet.scheduler.rest.data.JobRequest;
import net.cpollet.scheduler.rest.data.JobResponse;
import net.cpollet.scheduler.rest.data.JobStatusRequest;
import net.cpollet.scheduler.rest.data.JobStatusResponse;
import net.cpollet.scheduler.rest.data.Rate;
import net.cpollet.scheduler.rest.data.Status;

import java.util.List;
import java.util.stream.Collectors;

public class SchedulerRestAdapter {
    private final Scheduler scheduler;

    public SchedulerRestAdapter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public List<JobResponse> all() {
        return scheduler.allJobs().stream()
                .map(e -> mapToRest(e, Status.RUNNING))// FIXME status conversion
                .collect(Collectors.toList());
    }

    private JobResponse mapToRest(Job job, Status status) {
        TriggerAdapter triggerAdapter = TriggerAdapter.adapt(job.getTrigger());
        return new JobResponse(
                job.getJobId().toString(),
                status,
                triggerAdapter.getExpression(),
                triggerAdapter.getRate(),
                job.getType(),
                job.getParameters()
        );
    }

    public JobResponse schedule(JobRequest jobRequest) {
        if (jobRequest.getCronExpression() != null) {
            return null;
        }

        if (jobRequest.getRate() != null) {
            return mapToRest(
                    scheduler.schedule(
                            jobRequest.getRate().getPeriod(),
                            engineUnit(jobRequest.getRate().getUnit()),
                            jobRequest.getType(),
                            jobRequest.getParameters()
                    ),
                    Status.RUNNING
            );
        }

        throw new IllegalArgumentException("Either CronExpression or Rate must be set");
    }

    private PeriodicTrigger.Unit engineUnit(Rate.Unit unit) {
        switch (unit) {
            case MILLISECOND:
                return PeriodicTrigger.Unit.MILLISECOND;
            case SECOND:
                return PeriodicTrigger.Unit.SECOND;
            case MINUTE:
                return PeriodicTrigger.Unit.MINUTE;
            case HOUR:
                return PeriodicTrigger.Unit.HOUR;
            case DAY:
                return PeriodicTrigger.Unit.DAY;
            case WEEK:
                return PeriodicTrigger.Unit.WEEK;
            case MONTH:
                return PeriodicTrigger.Unit.MONTH;
        }

        throw new IllegalArgumentException("Invalid Unit: " + unit);
    }

    public JobResponse delete(String jobId) {
        return mapToRest(
                scheduler.delete(new JobId(jobId)),
                Status.DELETED
        );
    }

    public JobStatusResponse changeStatus(String jobId, JobStatusRequest jobStatusRequest) {
        switch (jobStatusRequest.getStatus()) {
            case RUNNING:
                return mapToJobStatusResponse(scheduler.start(new JobId(jobId)));
            case STOPPED:
                return mapToJobStatusResponse(scheduler.stop(new JobId(jobId)));
        }
        throw new IllegalArgumentException("Invalid status: " + jobStatusRequest.getStatus());
    }

    private JobStatusResponse mapToJobStatusResponse(Job job) {
        switch (job.getStatus()) {
            case RUNNING:
                return new JobStatusResponse(job.getJobId().toString(), Status.RUNNING);
            case STOPPED:
                return new JobStatusResponse(job.getJobId().toString(), Status.STOPPED);
        }

        throw new IllegalArgumentException("Invalid status: " + job.getStatus());
    }

    public JobStatusResponse getStatus(String jobId) {
        JobId jobIdObject = new JobId(jobId);

        Job job = scheduler.allJobs().stream()
                .filter(j -> j.getJobId().equals(jobIdObject))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("job with id " + jobIdObject + " not found"));

        return mapToJobStatusResponse(job);
    }
}
