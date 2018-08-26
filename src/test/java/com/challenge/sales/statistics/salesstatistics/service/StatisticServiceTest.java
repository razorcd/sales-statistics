package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import com.challenge.sales.statistics.salesstatistics.domain.Statistic;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

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
    public void getStatisticsWhenNotEmpty() {
        //given
        Amount amount1 = new Amount(11d, Instant.now(clock).toEpochMilli());
        Amount amount2 = new Amount(9d, Instant.now(clock).toEpochMilli());
        salesRepository.saveAmount(amount1);
        salesRepository.saveAmount(amount2);

        //when
        Statistic response = statisticService.getStatistic();

        //then
        assertEquals("Should return correct statistics.", new Statistic(20d, 2), response);
    }

    @Test
    public void getStatisticsWhenEmpty() {
        //when
        Statistic response = statisticService.getStatistic();

        //then
        assertEquals("Should return correct zero statistics.", new Statistic(0d, 0), response);
    }
}