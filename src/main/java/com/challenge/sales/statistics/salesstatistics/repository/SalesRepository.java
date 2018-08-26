package com.challenge.sales.statistics.salesstatistics.repository;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class SalesRepository {

    private final List<Amount> amounts;

    public SalesRepository() {
        amounts = new CopyOnWriteArrayList<>();
    }

    public List<Amount> getAmounts() {
        return amounts;
    }

    public void saveAmount(Amount amount) {
        amounts.add(amount);
    }

    public void deleteAll() {
        amounts.clear();
    }
}
