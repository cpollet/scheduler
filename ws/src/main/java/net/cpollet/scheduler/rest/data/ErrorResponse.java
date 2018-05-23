package net.cpollet.scheduler.rest.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String message;
    private final String details;
}
