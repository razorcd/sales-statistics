package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import com.challenge.sales.statistics.salesstatistics.domain.TotalAmount;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import com.challenge.sales.statistics.salesstatistics.utils.Properites;
import org.rapidoid.annotation.Service;

import javax.inject.Inject;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Service
public class StatisticService {

    private int periodInSec = Integer.parseInt(Properites.getProperties().getProperty("com.challenge.sales.statistics.period_in_sec"));

    private SalesRepository salesRepository;
    private Clock clock;

    @Inject
    public StatisticService(SalesRepository salesRepository, Clock clock) {
        this.salesRepository = salesRepository;
        this.clock = clock;
    }

    /**
     * Get total amount for recent sales amounts.
     *
     * @return {@link TotalAmount} get recent total amount.
     */
    public TotalAmount getRecentTotalAmount() {
        long recentTimeLimitMilli = getRecentTimeLimitMilli();

        List<TotalAmount> totalAmountList = salesRepository.onEachQueueExecute(queue -> getStatisticsFrom(queue, recentTimeLimitMilli));

        TotalAmount totalAmount = totalAmountList.stream().reduce(TotalAmount.ZERO, TotalAmount::add, (a, b) -> null);

        return Optional.ofNullable(totalAmount)
                .orElse(TotalAmount.ZERO);
    }

    private TotalAmount getStatisticsFrom(Queue<Amount> saleAmounts, long fromCreatedAtMilli) {
        return saleAmounts.stream()
                .filter(amount -> amount.isAfter(fromCreatedAtMilli))
                .reduce(TotalAmount.ZERO, TotalAmount::add, (a, b) -> null);
    }

    private long getRecentTimeLimitMilli() {
        return Instant.now(clock)
                .minusSeconds(periodInSec)
                .toEpochMilli();
    }
}
