package com.challenge.sales.statistics.salesstatistics.controller;

import com.challenge.sales.statistics.salesstatistics.controller.dto.StatisticsResponseDto;
import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class StatisticsControllerHighTrafficTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        salesRepository.deleteAll();
    }

    @Test
    public void should_store_a_lot_of_amounts_and_retreive_precise_statistics() throws Exception {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(() -> this.postALotOfSales(2000, "123456.78"));
        executorService.submit(() -> this.postALotOfSales(2000, "123456.78"));
        executorService.submit(() -> this.postALotOfSales(2000, "123.45"));
        executorService.submit(() -> this.postALotOfSales(2000, "123.45"));
        executorService.submit(() -> this.postALotOfSales(2000, "0.12"));
        executorService.submit(() -> this.postALotOfSales(2000, "0.12"));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        //when
        MvcResult response = mockMvc.perform(get("/statistics")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().isOk())
                .andReturn();

        //then
        StatisticsResponseDto responseDto = objectMapper.readValue(response.getResponse().getContentAsString(), StatisticsResponseDto.class);
        assertEquals("Should return correct statistics data.",
                new StatisticsResponseDto("494321400.00", "41193.45"), responseDto);
    }

    private void postALotOfSales(int postCount, String amount) {
        try {
            for (int i = 0; i < postCount; i++) {
                mockMvc.perform(post("/sales").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).content("sales_amount="+amount));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while posting sales from multiple threads.", e);
        }
    }

}