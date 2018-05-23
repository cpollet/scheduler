package net.cpollet.scheduler.engine.api;

/**
 * http://www.baeldung.com/cron-expressions
 */
public interface CronTrigger extends Trigger {
    String getExpression();
}
