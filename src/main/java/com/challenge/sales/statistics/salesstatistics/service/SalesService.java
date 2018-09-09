package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

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
     * @param amountValueCents the sales amount value in cents to store.
     */
    public void store(long amountValueCents) {
        Amount amount = new Amount(amountValueCents, Instant.now(clock).toEpochMilli());

        salesRepository.saveAmount(amount);
    }
}
