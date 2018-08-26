package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.domain.Statistic;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class StatisticServiceTest {


    private StatisticService statisticService;
    private SalesRepository salesRepository;

    @Before
    public void setUp() throws Exception {
        salesRepository = new SalesRepository();

        statisticService = new StatisticService(salesRepository);
    }

    @Test
    public void getStatisticsWhenNotEmpty() {
        //given
        salesRepository.saveAmount(new BigDecimal(11));
        salesRepository.saveAmount(new BigDecimal(9));

        //when
        Statistic response = statisticService.getStatistic();

        //then
        assertEquals("Should return correct statistics.", new Statistic("20", "10"), response);
    }

    @Test
    public void getStatisticsWhenEmpty() {
        //when
        Statistic response = statisticService.getStatistic();

        //then
        assertEquals("Should return correct zero statistics.", new Statistic("0", "0"), response);
    }
}