package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import com.challenge.sales.statistics.salesstatistics.domain.Statistic;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
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
        Amount amount1 = new Amount(new BigDecimal(11), LocalDateTime.now(clock));
        Amount amount2 = new Amount(new BigDecimal(9), LocalDateTime.now(clock));
        salesRepository.saveAmount(amount1);
        salesRepository.saveAmount(amount2);

        //when
        Statistic response = statisticService.getStatistic();

        //then
        assertEquals("Should return correct statistics.", new Statistic(BigDecimal.valueOf(20), 2), response);
    }

    @Test
    public void getStatisticsWhenEmpty() {
        //when
        Statistic response = statisticService.getStatistic();

        //then
        assertEquals("Should return correct zero statistics.", new Statistic(BigDecimal.ZERO, 0), response);
    }
}