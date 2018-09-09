package com.challenge.sales.statistics.salesstatistics.scheduler;

import com.challenge.sales.statistics.salesstatistics.utils.Properites;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Scheduler that runs repeatedly in a separate thread.
 */
public class Scheduler {

    private final int period = Integer.parseInt(Properites.getProperties().getProperty("com.challenge.sales.scheduler.period_in_sec"));
    private final ScheduledExecutorService scheduledExecutorService;

    public Scheduler() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void start(Runnable runnable) {
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, period, TimeUnit.SECONDS);
    }
}
