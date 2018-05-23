package net.cpollet.scheduler.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class JobResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String jobId;
    private final Status status;
    private final String cronExpression;
    private final Rate rate;
    private final String type;
    private final Map<String, List<String>> parameters;
}