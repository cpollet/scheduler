package net.cpollet.scheduler.rest.data;

import lombok.Data;

@Data
public class JobStatusRequest {
    private Status status;
}
