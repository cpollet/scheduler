package net.cpollet.scheduler.datastore.nitrite.entities.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dizitart.no2.objects.Id;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NitriteJob {
    @Id
    private String jobId;
    private String type;
    private Map<String, List<String>> parameters;
    private NitriteTrigger trigger;
    private String status;
}
