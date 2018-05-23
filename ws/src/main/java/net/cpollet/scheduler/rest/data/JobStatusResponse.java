package net.cpollet.scheduler.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class JobStatusResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    public final String jobId;
    public final Status running;
}
