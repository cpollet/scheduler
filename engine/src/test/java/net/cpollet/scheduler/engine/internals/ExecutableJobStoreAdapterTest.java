package net.cpollet.scheduler.engine.internals;

import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobStore;
import net.cpollet.scheduler.engine.internals.job.ExecutableJob;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

class ExecutableJobStoreAdapterTest {
    private ExecutableJobStoreAdapter adapter;
    private JobStore jobStore;

    @BeforeEach
    void setup() {
        ExecutableJobFactory jobFactory = Mockito.mock(ExecutableJobFactory.class);
        Mockito.when(jobFactory.restore(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Mockito.mock(ExecutableJob.class));

        jobStore = Mockito.mock(JobStore.class);
        adapter = new ExecutableJobStoreAdapter(jobStore, jobFactory);
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
