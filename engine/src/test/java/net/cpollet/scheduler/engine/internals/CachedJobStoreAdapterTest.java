package net.cpollet.scheduler.engine.internals;

import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Store;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CachedJobStoreAdapterTest {
    private CachedExecutableJobStoreAdapter store;
    private Store<ExecutableJob, JobId> delegate;

    @BeforeEach
    void setup() {
        //noinspection unchecked
        delegate = Mockito.mock(Store.class);
        store = new CachedExecutableJobStoreAdapter(delegate);
    }

    @Test
    void save_delegatesToWrappedJobStore() {
        // GIVEN
        ExecutableJob expectedJob = Mockito.mock(ExecutableJob.class);
        Mockito.when(expectedJob.getJobId()).thenReturn(new JobId());

        // WHEN
        store.save(expectedJob);

        // THEN
        Mockito.verify(delegate, Mockito.times(1)).save(expectedJob);
    }

    @Test
    void get_delegatesToWrappedJobStore() {
        // GIVEN
        ExecutableJob expectedJob = Mockito.mock(ExecutableJob.class);
        Mockito.when(expectedJob.getJobId()).thenReturn(new JobId());

        // WHEN
        store.get(expectedJob.getJobId());

        // THEN
        Mockito.verify(delegate, Mockito.times(1)).get(expectedJob.getJobId());
    }

    @Test
    void get_returnsSameAdaptedSavedJob() {
        // GIVEN
        ExecutableJob expectedJob = Mockito.mock(ExecutableJob.class);
        Mockito.when(expectedJob.getJobId()).thenReturn(new JobId());

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
}
