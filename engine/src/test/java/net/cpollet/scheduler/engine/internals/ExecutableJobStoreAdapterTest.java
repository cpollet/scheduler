package net.cpollet.scheduler.engine.internals;

import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;
import net.cpollet.scheduler.test.stub.engine.api.JobStoreStub;
import net.cpollet.scheduler.test.stub.engine.api.JobStub;
import net.cpollet.scheduler.test.stub.engine.internals.job.DummyJobStub;
import net.cpollet.scheduler.test.stub.engine.internals.job.ExecutableJobFactoryStub;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class ExecutableJobStoreAdapterTest {
    private ExecutableJobStoreAdapter store;
    private JobStoreStub jobStore;

    @BeforeEach
    public void setup() {
        ExecutableJobFactory jobFactory = new ExecutableJobFactoryStub();
        jobStore = new JobStoreStub();
        store = new ExecutableJobStoreAdapter(jobStore, jobFactory);
    }

    @Test
    public void get_returnsSameAdaptedSavedJob() {
        // GIVEN
        ExecutableJob expectedJob = new JobStub(new JobId(), null, null);

        // WHEN
        store.save(expectedJob);

        // THEN
        ExecutableJob job1 = store.get(expectedJob.getJobId());
        ExecutableJob job2 = store.get(expectedJob.getJobId());

        Assertions.assertThat(job1)
                .isNotNull().
                isSameAs(job2);
        Assertions.assertThat(job1.getJobId())
                .isSameAs(expectedJob.getJobId());
    }

    @Test
    public void get_wrapsAndReturnsSameAdaptedSavedJob() {
        // GIVEN
        Job dummyJob = new DummyJobStub();
        jobStore.save(dummyJob);

        // WHEN
        ExecutableJob job1 = store.get(dummyJob.getJobId());
        ExecutableJob job2 = store.get(dummyJob.getJobId());

        // THEN
        Assertions.assertThat(job1)
                .isNotNull()
                .isSameAs(job2);
    }

    @Test
    public void getAll_returnsCollection() {
        // GIVEN
        Job dummyJob = new DummyJobStub();
        jobStore.save(dummyJob);

        // WHEN
        Collection<ExecutableJob> jobs = store.getAll();

        // THEN
        Assertions.assertThat(jobs)
                .isNotNull()
                .hasSize(1);
    }
}
