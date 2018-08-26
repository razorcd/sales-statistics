package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
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

        salesService.store(BigDecimal.TEN);

        Amount expectedAmount = new Amount(BigDecimal.TEN, LocalDateTime.now(clock));
        assertThat("Should store the amount.", salesRepository.getAmounts(), hasItems(expectedAmount));
    }


//    @Test
//    public void shouldStoreSalesAmount() {
//        Clock clock1 = Clock.fixed(Instant.EPOCH, ZoneId.systemDefault());
//        salesService = new SalesService(salesRepository, clock1);
//        salesService.store(BigDecimal.TEN);
//
//        Clock clock2 = Clock.fixed(Instant.EPOCH.plusMillis(600), ZoneId.systemDefault());
//        salesService = new SalesService(salesRepository, clock2);
//        salesService.store(new BigDecimal(44.23));
//
//        Clock clock3 = Clock.fixed(Instant.EPOCH.plusMillis(1200), ZoneId.systemDefault());
//        salesService = new SalesService(salesRepository, clock3);
//        salesService.store(new BigDecimal(11.71));
//
//
//        BigDecimal
//
//        assertThat("Should store the amount.", salesRepository.getAmounts(), hasItems(BigDecimal.TEN));
//    }
}