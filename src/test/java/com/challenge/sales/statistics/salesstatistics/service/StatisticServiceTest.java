package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import com.challenge.sales.statistics.salesstatistics.domain.TotalAmount;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


public class StatisticServiceTest {

    private StatisticService statisticService;
    private SalesRepository salesRepository;

    private Clock clock;

    @Before
    public void setUp() throws Exception {
        clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        salesRepository = new SalesRepository();

        statisticService = new StatisticService(salesRepository, clock);
    }

    @Test
    public void getRecentTotalAmount_whenNotEmpty() {
        //given
        Amount amount1 = new Amount(11L, Instant.now(clock).toEpochMilli());
        Amount amount2 = new Amount(9L, Instant.now(clock).toEpochMilli());
        salesRepository.saveAmount(amount1);
        salesRepository.saveAmount(amount2);

        //when
        TotalAmount response = statisticService.getRecentTotalAmount();

        //then
        assertEquals("Should return correct total amount.", new TotalAmount(20L, 2), response);
    }

    @Test
    public void getRecentTotalAmount_whenEmpty() {
        //when
        TotalAmount response = statisticService.getRecentTotalAmount();

        //then
        assertEquals("Should return correct zero total amount.", new TotalAmount(0L, 0), response);
    }

    @Test
    public void getRecentTotalAmount_whenALotOfAmounts() {
        //given
        List<Amount> amountList = IntStream.range(0, 1_000_000)
                .mapToObj(i -> new Amount(123_456L, Instant.now(clock).toEpochMilli()))
                .collect(Collectors.toList());
        amountList.forEach(amount -> salesRepository.saveAmount(amount));

        //when
        TotalAmount response = statisticService.getRecentTotalAmount();

        //then
        assertEquals("Should return correct total amount.", new TotalAmount(123_456_000_000L, 1_000_000), response);
    }
}