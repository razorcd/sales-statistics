package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class SalesService {

    private SalesRepository salesRepository;
    private Clock clock;

    @Autowired
    public SalesService(SalesRepository salesRepository, Clock clock) {
        this.salesRepository = salesRepository;
        this.clock = clock;
    }

    /**
     * Store an sales amount value.
     *
     * @param amountValue the sales amount value to store.
     */
    public void store(BigDecimal amountValue) {
        Amount amount = new Amount(amountValue, LocalDateTime.now(clock));

        salesRepository.saveAmount(amount);
    }
}
