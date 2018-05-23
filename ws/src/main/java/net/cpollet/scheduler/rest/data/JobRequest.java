package net.cpollet.scheduler.rest.data;

import lombok.Data;
import net.cpollet.scheduler.rest.validator.ValidJobTypeName;
import net.cpollet.scheduler.rest.validator.ValidateAtLeastOneNotNull;

import java.util.List;
import java.util.Map;

@Data
@ValidateAtLeastOneNotNull(fields = {"cronExpression", "rate"})
public class JobRequest {
    @ValidJobTypeName(message = "Invalid job type")
    private String type;
    private String cronExpression;
    private Rate rate;
    private Map<String, List<String>> parameters;
}
