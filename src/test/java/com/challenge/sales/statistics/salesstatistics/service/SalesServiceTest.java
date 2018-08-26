package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

public class SalesServiceTest {

    private SalesService salesService;
    private SalesRepository salesRepository;

    @Before
    public void setUp() throws Exception {
        salesRepository = new SalesRepository();
        salesService = new SalesService(salesRepository);
    }

    @Test
    public void shouldStoreSalesAmount() {

        salesService.store(BigDecimal.TEN);

        assertThat("Should store the amount.", salesRepository.getAmounts(), hasItems(BigDecimal.TEN));
    }

}