package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import com.challenge.sales.statistics.salesstatistics.domain.Statistic;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatisticService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticService.class);

    @Value("${com.challenge.sales.statistics.period_in_sec}")
    private int periodInSec = 10;

    private SalesRepository salesRepository;
    private Clock clock;

    @Autowired
    public StatisticService(SalesRepository salesRepository, Clock clock) {
        this.salesRepository = salesRepository;
        this.clock = clock;
    }

    /**
     * Get statistics for recent sales amounts.
     *
     * @return [Statistic] get recent statistics.
     */
    public Statistic getStatistic() {
        List<Amount> saleAmounts = salesRepository.getAmounts();
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime fromCreatedAt = now.minusSeconds(periodInSec);

        LOGGER.info("Calculating statistics for sales between {} and {}. Total sales amount currently stored: {}", fromCreatedAt, now, saleAmounts.size());

        Statistic finalStatistic = saleAmounts.stream()
                .filter(amount -> amount.getCreatedAt().isAfter(fromCreatedAt))
                .reduce(Statistic.ZERO, Statistic::add, (a, b) -> null);

        return finalStatistic;
    }

}
