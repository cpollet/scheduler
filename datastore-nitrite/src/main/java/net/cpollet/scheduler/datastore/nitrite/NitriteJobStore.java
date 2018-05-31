package net.cpollet.scheduler.datastore.nitrite;

import lombok.AllArgsConstructor;
import net.cpollet.scheduler.datastore.nitrite.converters.JobConverter;
import net.cpollet.scheduler.datastore.nitrite.entities.internal.NitriteJob;
import net.cpollet.scheduler.engine.api.Job;
import net.cpollet.scheduler.engine.api.JobId;
import net.cpollet.scheduler.engine.api.Store;
import net.cpollet.scheduler.engine.api.exception.ElementNotFoundException;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public class NitriteJobStore implements Store<Job, JobId> {
    private final Nitrite db;
    private final JobConverter jobConverter;

    @Override
    public void save(Job job) {
        NitriteJob nitriteJob = jobConverter.convert(job);
        repository().update(nitriteJob, true);
    }

    private ObjectRepository<NitriteJob> repository() {
        return db.getRepository(NitriteJob.class);
    }

    @Override
    public void delete(JobId jobId) {
        repository().remove(filter(jobId));
    }

    @Override
    public Job get(JobId jobId) throws ElementNotFoundException {
        Cursor<NitriteJob> jobs = repository().find(filter(jobId));

        if (jobs.size() != 1) {
            throw new ElementNotFoundException(jobId.toString());
        }

        return jobConverter.convertBack(jobs.firstOrDefault());
    }

    private ObjectFilter filter(JobId jobId) {
        return ObjectFilters.eq("jobId", jobId.toString());
    }


    @Override
    public Collection<Job> getAll() {
        return StreamSupport.stream(repository().find().spliterator(), false)
                .map(j -> jobConverter.convertBack(j))
                .collect(Collectors.toList());
    }
}
