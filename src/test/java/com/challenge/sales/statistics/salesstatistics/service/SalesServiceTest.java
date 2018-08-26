package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class SalesServiceTest {

    private SalesService salesService;
    private SalesRepository salesRepository;
    private Clock clock;

    @Before
    public void setUp() throws Exception {
        salesRepository = new SalesRepository();
        clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        salesService = new SalesService(salesRepository, clock);
    }

    @Test
    public void shouldStoreSalesAmount() {

        salesService.store(10d);

        Amount expectedAmount = new Amount(10d, Instant.now(clock).toEpochMilli());
        assertThat("Should store the amount.", salesRepository.getAmounts(), hasItems(expectedAmount));
    }

}