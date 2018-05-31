package net.cpollet.scheduler.engine.internals;

import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Store;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

class JobStoreAdapterTest {
    private JobStoreAdapter adapter;
    private Store<Job, JobId> jobStore;

    @BeforeEach
    void setup() {
        ExecutableJobFactory jobFactory = Mockito.mock(ExecutableJobFactory.class);
        Mockito.when(jobFactory.restore(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Mockito.mock(ExecutableJob.class));

        jobStore = jobStore();
        adapter = new JobStoreAdapter(jobStore, jobFactory);
    }

    @SuppressWarnings("unchecked")
    private Store<Job, JobId> jobStore() {
        return Mockito.mock(Store.class);
    }

    @Test
    void getAll_returnsCollection() {
        // GIVEN
        Job job = Mockito.mock(Job.class);
        Mockito.when(jobStore.getAll()).thenReturn(Arrays.asList(job));

        // WHEN
        Collection<ExecutableJob> jobs = adapter.getAll();

        // THEN
        Assertions.assertThat(jobs)
                .isNotNull()
                .hasSize(1);
    }
}
