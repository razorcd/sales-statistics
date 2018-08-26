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
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

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
        List<Statistic> statisticList = salesRepository.onEachQueueExecute(this::getStatisticsFrom);

        Statistic finalStatistic = statisticList.stream().reduce(Statistic.ZERO, Statistic::add, (a, b) -> null);

        return Optional.ofNullable(finalStatistic)
                .orElse(Statistic.ZERO);
    }

    private Statistic getStatisticsFrom(Queue<Amount> saleAmounts) {
        Instant now = Instant.now(clock);
        long fromCreatedAt = now.minusSeconds(periodInSec).toEpochMilli();

        return saleAmounts.stream()
                .filter(amount -> amount.isAfter(fromCreatedAt))
                .reduce(Statistic.ZERO, Statistic::add, (a, b) -> null);
    }

}
