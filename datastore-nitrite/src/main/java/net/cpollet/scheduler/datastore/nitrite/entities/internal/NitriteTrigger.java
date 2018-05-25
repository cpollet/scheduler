package net.cpollet.scheduler.datastore.nitrite.entities.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NitriteTrigger {
    private String type;
    private Long period;
    private String unit;
    private String cronExpression;

    public static NitriteTrigger cron(String cronExpression) {
        return new NitriteTrigger("CRON", null, null, cronExpression);
    }

    public static NitriteTrigger periodic(Long period, String unit) {
        return new NitriteTrigger("PERIODIC", period, unit, null);
    }
}
