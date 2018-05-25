package net.cpollet.scheduler.engine.api;

/**
 * http://www.baeldung.com/cron-expressions
 */
// FIXME could be an actual class
public interface CronTrigger extends Trigger {
    String getExpression();
}
