package com.challenge.sales.statistics.salesstatistics.repository;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import org.springframework.stereotype.Repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class SalesRepository {

    private final Queue<Amount> amounts;

    public SalesRepository() {
        amounts = new ConcurrentLinkedQueue<>();
    }

    public Queue<Amount> getAmounts() {
        return amounts;
    }

    public void saveAmount(Amount amount) {
        amounts.add(amount);
    }

    public void deleteAll() {
        amounts.clear();
    }
}
