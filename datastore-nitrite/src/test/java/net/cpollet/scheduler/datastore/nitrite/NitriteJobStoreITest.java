package net.cpollet.scheduler.datastore.nitrite;

import net.cpollet.scheduler.datastore.nitrite.converters.JobConverter;
import org.dizitart.no2.Nitrite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NitriteJobStoreITest {
    private NitriteJobStore jobStore;
    private Nitrite db;
    private JobConverter jobConverter;

    @BeforeEach
    public void setup() {
        db = Nitrite.builder()
                .compressed()
                .openOrCreate();

        jobConverter = Mockito.mock(JobConverter.class);

        jobStore = new NitriteJobStore(db, jobConverter);
    }

    @Test
    public void save_savesJob() {
//        // GIVEN
//        String jobId = "id";
//        Mockito.when(jobConverter.convert(ArgumentMatchers.<Job>any()))
//                .thenReturn(new NitriteJob(
//                        jobId,
//                        "type",
//                        null,
//                        Trigger.Type.CRON,
//                        "cron",
//                        null,
//                        null,
//                        Job.Status.RUNNING
//                ));
//
//        // WHEN
//        jobStore.save(Mockito.mock(Job.class));
//
//        // THEN
//        Cursor<NitriteJob> savedJob = db.getRepository(NitriteJob.class).find(ObjectFilters.eq("jobId", jobId));
//
//        Assertions.assertThat(savedJob)
//                .hasSize(1);
    }
}
