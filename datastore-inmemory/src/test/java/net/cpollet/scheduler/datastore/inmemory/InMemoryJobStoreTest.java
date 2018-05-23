package net.cpollet.scheduler.datastore.inmemory;

import net.cpollet.scheduler.test.stub.JobStub;
import net.cpollet.scheduler.engine.api.exception.ElementNotFoundException;
import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class InMemoryJobStoreTest {
    private InMemoryJobStore store;


    @BeforeEach
    public void setup() {
        store = new InMemoryJobStore();
    }

    @Test
    public void get_returnsSavedJob() {
        // GIVEN
        Job expectedJob = new JobStub();

        // WHEN
        store.save(expectedJob);

        // THEN
        Job job = store.get(expectedJob.getJobId());
        Assertions.assertThat(job)
                .isSameAs(expectedJob);
    }

    @Test
    public void get_throwsException_whenJobIsNonexistent() {
        // GIVEN
        JobId jobId = new JobId();

        // WHEN
        Throwable thrown = Assertions.catchThrowable(() -> store.get(jobId));

        // THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessage(String.format("Element with ID [%s] not found", jobId.toString()));
    }

    @Test
    public void delete_deletesJob() {
        // GIVEN
        Job expectedJob = new JobStub();

        // WHEN
        store.save(expectedJob);
        store.delete(expectedJob.getJobId());

        // THEN
        Throwable thrown = Assertions.catchThrowable(() -> store.get(expectedJob.getJobId()));
        Assertions.assertThat(thrown)
                .isNotNull();
    }

    @Test
    public void getAll_returnsAllJobs() {
        // GIVEN
        Job expectedJob1 = new JobStub();
        Job expectedJob2 = new JobStub();
        store.save(expectedJob1);
        store.save(expectedJob2);

        // WHEN
        Collection<Job> jobs = store.getAll();

        // THEN
        Assertions.assertThat(jobs)
                .containsExactlyInAnyOrder(expectedJob1, expectedJob2);

    }

    @Test
    public void getAll_returnsAnEmptyCollection_whenNoJobsAreSaved() {
        // WHEN
        Collection<Job> jobs = store.getAll();

        // THEN
        Assertions.assertThat(jobs)
                .isEmpty();
    }
}
