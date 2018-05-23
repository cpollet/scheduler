package net.cpollet.scheduler.rest.resource;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.scheduler.rest.adapters.SchedulerRestAdapter;
import net.cpollet.scheduler.rest.data.JobRequest;
import net.cpollet.scheduler.rest.data.JobResponse;
import net.cpollet.scheduler.rest.data.JobStatusRequest;
import net.cpollet.scheduler.rest.data.JobStatusResponse;
import net.cpollet.scheduler.rest.data.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@Slf4j
public class JobController {
    private final SchedulerRestAdapter scheduler;

    @Autowired
    public JobController(SchedulerRestAdapter scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping
    public ResponseEntity<JobResponse> create(@RequestBody @Valid JobRequest jobRequest) {
        return new ResponseEntity<>(scheduler.schedule(jobRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{jobId}/status")
    public ResponseEntity<JobStatusResponse> updateStatus(@PathVariable("jobId") String jobId, @RequestBody JobStatusRequest jobRequest) {
        // FIXME check job exists
        return new ResponseEntity<>(scheduler.changeStatus(jobId, jobRequest), HttpStatus.OK);
    }

    @GetMapping("/{jobId}/status")
    public ResponseEntity<JobStatusResponse> getStatus(@PathVariable("jobId") String jobId) {
        // FIXME check job exists
        return new ResponseEntity<>(scheduler.getStatus(jobId), HttpStatus.OK);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<JobResponse> delete(@PathVariable("jobId") String jobId) {
        // FIXME check job exists
        return new ResponseEntity<>(scheduler.delete(jobId), HttpStatus.OK);
    }

    @GetMapping()
    public List<JobResponse> jobs() {
        return scheduler.all();
    }
}
