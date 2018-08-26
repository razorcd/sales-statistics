package com.challenge.sales.statistics.salesstatistics.controller;

import com.challenge.sales.statistics.salesstatistics.SalesStatisticsApplication;
import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@Import({SalesStatisticsApplication.class})
public class SalesControllerTest {

    @Configuration
    static class Config {
        @Bean
        @Primary
        public Clock clockFixed() {
            return Clock.fixed(Instant.now(), ZoneId.systemDefault());
        }
    }

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Clock clock;

    @Before
    public void before() {
        salesRepository.deleteAll();
    }

    @Test
    public void postValidSales() throws Exception {
        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .content("sales_amount=10.00"))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().isAccepted());

        Amount amount = new Amount(new BigDecimal("10.00"), LocalDateTime.now(clock));
        assertThat("Should persist amount at current time.", salesRepository.getAmounts(), hasItems(amount));
    }

    @Test
    public void postEmptySales() throws Exception {
        mockMvc.perform(post("/sales")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("sales_amount="))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().isBadRequest());

        assertThat("Should not persist anything.", salesRepository.getAmounts(), empty());
    }

    @Test
    public void postNaNSales() throws Exception {
        mockMvc.perform(post("/sales")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("sales_amount=not_a_number"))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().isBadRequest());

        assertThat("Should not persist anything.", salesRepository.getAmounts(), empty());
    }
}
