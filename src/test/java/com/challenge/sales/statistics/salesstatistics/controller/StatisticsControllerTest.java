package com.challenge.sales.statistics.salesstatistics.controller;

import com.challenge.sales.statistics.salesstatistics.domain.Statistic;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
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

    @Before
    public void setUp() throws Exception {
        salesRepository.deleteAll();
    }

    @Test
    public void getStatistics() throws Exception {
        //given
        salesRepository.saveAmount(new BigDecimal(5.43));
        salesRepository.saveAmount(new BigDecimal(14.98));

        //when
        MvcResult response = mockMvc.perform(get("/statistics")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Statistic responseDto = objectMapper.readValue(response.getResponse().getContentAsString(), Statistic.class);
        assertEquals("Should return correct statistics data.", new Statistic("20.41", "10.21"), responseDto);
    }

}