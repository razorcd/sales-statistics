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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class StatisticsControllerTest {

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
    public void getStatistics() throws Exception {
        //given

        Amount amount1 = new Amount(5.43d, Instant.now().toEpochMilli());
        Amount amount2 = new Amount(14.98d, Instant.now().toEpochMilli());
        salesRepository.saveAmount(amount1);
        salesRepository.saveAmount(amount2);

        //when
        MvcResult response = mockMvc.perform(get("/statistics")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().isOk())
                .andReturn();

        //then
        StatisticsResponseDto responseDto = objectMapper.readValue(response.getResponse().getContentAsString(), StatisticsResponseDto.class);
        assertEquals("Should return correct statistics data.",
                new StatisticsResponseDto("20.41", "10.21"), responseDto);
    }

    @Test
    public void should_store_amounts_and_retrieve_statistics_only_for_last_period() throws Exception {
        //given
        mockMvc.perform(post("/sales").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).content("sales_amount=2.15"));
        Thread.sleep(600); //TODO: replace with time travel
        mockMvc.perform(post("/sales").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).content("sales_amount=3.50"));
        Thread.sleep(600);
        mockMvc.perform(post("/sales").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).content("sales_amount=5.05"));

        //when
        MvcResult response = mockMvc.perform(get("/statistics")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().isOk())
                .andReturn();

        //then
        StatisticsResponseDto responseDto = objectMapper.readValue(response.getResponse().getContentAsString(), StatisticsResponseDto.class);
        assertEquals("Should return correct statistics data.",
                new StatisticsResponseDto("8.55", "4.28"), responseDto);
    }

}