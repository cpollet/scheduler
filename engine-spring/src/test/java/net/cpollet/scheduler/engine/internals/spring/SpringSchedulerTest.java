package net.cpollet.scheduler.engine.internals.spring;

import com.google.common.collect.ImmutableSet;
import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import net.cpollet.scheduler.engine.internals.ExecutableJobFactory;
import net.cpollet.scheduler.engine.internals.ExecutableJobStoreAdapter;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;
import net.cpollet.scheduler.test.stub.engine.internals.job.ExecutableJobStub;
import net.cpollet.scheduler.test.stub.engine.internals.spring.TriggerStub;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

public class SpringSchedulerTest {
    private SpringScheduler scheduler;
    private TaskScheduler taskScheduler;
    private ExecutableJobStoreAdapter jobStore;
    private ExecutableJobFactory jobFactory;
    private JobId jobId;

    @BeforeEach
    public void setup() {
        taskScheduler = Mockito.mock(TaskScheduler.class);
        jobStore = Mockito.mock(ExecutableJobStoreAdapter.class);
        jobFactory = Mockito.mock(ExecutableJobFactory.class);

        scheduler = new SpringScheduler(taskScheduler, jobStore, jobFactory);

        SpringCronTrigger trigger = Mockito.mock(SpringCronTrigger.class);
        Mockito.when(trigger.getType()).thenReturn(net.cpollet.scheduler.engine.api.Trigger.Type.CRON);
        Mockito.when(trigger.getExpression()).thenReturn("* * * * * *");

        ExecutableJob job = new ExecutableJobStub(new JobId(), null, trigger, Job.Status.STOPPED);
        jobId = job.getJobId();

        Mockito.when(jobFactory.create(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(job);

        Mockito.when(jobStore.get(ArgumentMatchers.any()))
                .thenReturn(job);
    }

    @Test
    public void schedule_schedulesJob() {
        // WHEN
        Job startedJob = scheduler.schedule(1L, PeriodicTrigger.Unit.SECOND, "type", null);

        // THEN
        Mockito.verify(taskScheduler, Mockito.times(1))
                .schedule(Mockito.any(), ArgumentMatchers.isA(Trigger.class));

        Assertions.assertThat(startedJob.getStatus())
                .isEqualTo(Job.Status.RUNNING);
    }

    @Test
    public void schedule_savesJob() {
        // WHEN
        scheduler.schedule(1L, PeriodicTrigger.Unit.SECOND, "type", null);

        // THEN
        ArgumentCaptor<ExecutableJob> captor = ArgumentCaptor.forClass(ExecutableJob.class);
        Mockito.verify(jobStore, Mockito.atLeast(1)).save(captor.capture());

        Assertions.assertThat(captor.getValue().getStatus())
                .isEqualTo(Job.Status.RUNNING);
    }

    @Test
    public void start_schedulesJob() {
        // WHEN
        Job startedJob = scheduler.start(jobId);

        // THEN
        Mockito.verify(taskScheduler, Mockito.times(1))
                .schedule(Mockito.any(), ArgumentMatchers.isA(Trigger.class));

        Assertions.assertThat(startedJob.getStatus())
                .isEqualTo(Job.Status.RUNNING);
    }

    @Test
    public void start_savesJob() {
        // WHEN
        scheduler.start(jobId);

        // THEN
        ArgumentCaptor<ExecutableJob> captor = ArgumentCaptor.forClass(ExecutableJob.class);
        Mockito.verify(jobStore, Mockito.atLeast(1)).save(captor.capture());

        Assertions.assertThat(captor.getValue().getStatus())
                .isEqualTo(Job.Status.RUNNING);
    }

    @Test
    public void start_isNoOp_whenJobStarted() {
        // GIVEN
        jobStore.get(jobId).setStatus(Job.Status.RUNNING);

        // WHEN
        Job startedJob = scheduler.start(jobId);

        // THEN
        Assertions.assertThat(startedJob.getStatus())
                .isEqualTo(Job.Status.RUNNING);

        Mockito.verify(taskScheduler, Mockito.never())
                .schedule(Mockito.any(), ArgumentMatchers.isA(Trigger.class));

        Mockito.verify(jobStore, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    public void stop_stopsJob() {
        // GIVEN
        ScheduledFuture future = Mockito.mock(ScheduledFuture.class);
        Mockito.when(taskScheduler.schedule(ArgumentMatchers.any(), ArgumentMatchers.<Trigger>any()))
                .thenReturn(future);

        Job stoppedJob = scheduler.start(jobId);

        // WHEN
        scheduler.stop(jobId);

        // THEN
        Assertions.assertThat(stoppedJob.getStatus())
                .isEqualTo(Job.Status.STOPPED);

        Mockito.verify(future, Mockito.times(1))
                .cancel(true);
    }

    @Test
    public void stop_savesJob() {
        // GIVEN
        ScheduledFuture future = Mockito.mock(ScheduledFuture.class);
        Mockito.when(taskScheduler.schedule(ArgumentMatchers.any(), ArgumentMatchers.<Trigger>any()))
                .thenReturn(future);

        scheduler.start(jobId);

        // WHEN
        scheduler.stop(jobId);

        ArgumentCaptor<ExecutableJob> captor = ArgumentCaptor.forClass(ExecutableJob.class);
        Mockito.verify(jobStore, Mockito.atLeast(1)).save(captor.capture());

        Assertions.assertThat(captor.getValue().getStatus())
                .isEqualTo(Job.Status.STOPPED);
    }

    @Test
    public void stop_isNoOp_whenJobStopped() {
        // GIVEN
        ScheduledFuture future = Mockito.mock(ScheduledFuture.class);
        Mockito.when(taskScheduler.schedule(ArgumentMatchers.any(), ArgumentMatchers.<Trigger>any()))
                .thenReturn(future);

        // WHEN
        Job stoppedJob = scheduler.stop(jobId);

        // THEN
        Assertions.assertThat(stoppedJob.getStatus())
                .isEqualTo(Job.Status.STOPPED);

        Mockito.verify(future, Mockito.never())
                .cancel(ArgumentMatchers.anyBoolean());

        Mockito.verify(jobStore, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    public void delete_stopsJob() {
        // GIVEN
        ScheduledFuture future = Mockito.mock(ScheduledFuture.class);
        Mockito.when(taskScheduler.schedule(ArgumentMatchers.any(), ArgumentMatchers.<Trigger>any()))
                .thenReturn(future);

        scheduler.start(jobId);

        // WHEN
        Job deletedJob = scheduler.delete(jobId);

        // THEN
        Assertions.assertThat(deletedJob.getStatus())
                .isEqualTo(Job.Status.STOPPED);

        Mockito.verify(future, Mockito.times(1))
                .cancel(true);
    }

    @Test
    public void delete_deletesJob() {
        // GIVEN
        ScheduledFuture future = Mockito.mock(ScheduledFuture.class);
        Mockito.when(taskScheduler.schedule(ArgumentMatchers.any(), ArgumentMatchers.<Trigger>any()))
                .thenReturn(future);

        scheduler.start(jobId);

        // WHEN
        scheduler.delete(jobId);

        // THEN
        Mockito.verify(jobStore, Mockito.times(1))
                .delete(jobId);
    }

    @Test
    public void allJobs_returnsAllJobs() {
        // GIVEN
        ExecutableJob job1 = new ExecutableJobStub(new JobId(), null, null, Job.Status.STOPPED);
        ExecutableJob job2 = new ExecutableJobStub(new JobId(), null, null, Job.Status.STOPPED);
        Mockito.when(jobStore.getAll()).thenReturn(Arrays.asList(job1, job2));

        // WHEN
        Collection<Job> jobs = scheduler.allJobs();

        // THEN
        Assertions.assertThat(jobs)
                .containsExactly(job1, job2);
    }

    @Test
    public void validJobTypes_returnsValidJobTypes() {
        // GIVEN
        Mockito.when(jobFactory.getValidJobTypes())
                .thenReturn(ImmutableSet.of("Type1", "Type2"));

        // WHEN
        Set<String> jobTypes = scheduler.validJobTypes();

        // THEN
        Assertions.assertThat(jobTypes)
                .containsExactly("Type1", "Type2");
    }
}
