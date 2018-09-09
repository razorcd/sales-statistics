package com.challenge.sales.statistics.salesstatistics.scheduler;

import com.challenge.sales.statistics.salesstatistics.service.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CleanupOldAmounts {

    private static final Logger LOGGER = LoggerFactory.getLogger(CleanupOldAmounts.class);

    @Value("${com.challenge.sales.statistics.period_in_sec}")
    private int periodInSec;

    private final SalesService salesService;

    @Autowired
    public CleanupOldAmounts(SalesService salesService) {
        this.salesService = salesService;
    }

    @Scheduled(fixedRate = 1000)
    public void cleanOldAmounts() {
        salesService.cleanOldSales();
        LOGGER.info("Count recent sales amounts currently stored: {}", salesService.getSalesCount());
    }
}
