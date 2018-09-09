package com.challenge.sales.statistics.salesstatistics.scheduler;

import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Clock;
import java.time.Instant;

@Configuration
@EnableScheduling
public class CleanupOldAmounts {

    private static final Logger LOGGER = LoggerFactory.getLogger(CleanupOldAmounts.class);

    @Value("${com.challenge.sales.statistics.period_in_sec}")
    private int periodInSec;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    Clock clock;

    @Scheduled(fixedRate = 1000)
    public void cleanOldAmounts() {
        salesRepository.cleanOld(Instant.now(clock).minusSeconds(periodInSec).toEpochMilli());
//        LOGGER.info("Cleaning up old amounts.");
        displayCount();
    }

//    @Scheduled(fixedRate = 10000)
    public void displayCount() {
        LOGGER.info("Count recent sales amounts currently stored: {}", salesRepository.count());
    }
}
