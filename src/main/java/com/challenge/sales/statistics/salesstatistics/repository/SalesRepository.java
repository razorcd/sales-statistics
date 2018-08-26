package com.challenge.sales.statistics.salesstatistics.repository;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalesRepository {

    private final List<BigDecimal> amounts;

    public SalesRepository() {
        amounts = new ArrayList<>();
    }

    public List<BigDecimal> getAmounts() {
        return amounts;
    }

    public void saveAmount(BigDecimal amount) {
        amounts.add(amount);
    }

    public void deleteAll() {
        amounts.clear();
    }
}
