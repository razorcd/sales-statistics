package com.challenge.sales.statistics.salesstatistics;

import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import com.challenge.sales.statistics.salesstatistics.scheduler.Scheduler;
import com.challenge.sales.statistics.salesstatistics.service.SalesService;
import com.challenge.sales.statistics.salesstatistics.service.StatisticService;
import com.challenge.sales.statistics.salesstatistics.utils.Properites;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;

public class SalesStatisticsApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesStatisticsApplication.class);

    public static void main(String[] args) {
        Properites.initialize("application.properties");

        SalesRepository salesRepository = new SalesRepository();
        Clock clock = Clock.systemDefaultZone();

        SalesService salesService = new SalesService(salesRepository, clock);
        StatisticService statisticService = new StatisticService(salesRepository, clock);

        new Scheduler().start(() -> {
                salesService.cleanOldSales();
                LOGGER.info("Count recent sales amounts currently stored: {}", salesService.getSalesCount());
            });

        ControllerHandler controllerHandler = new ControllerHandler(salesService, statisticService);
        controllerHandler.run();
    }
}


