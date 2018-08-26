package com.challenge.sales.statistics.salesstatistics.controller;

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

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class SalesControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void before() {
    }

    @Test
    public void postValidSales() throws Exception {
        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .content("sales_amount=10.00"))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().isAccepted());
    }

    @Test
    public void postEmptySales() throws Exception {
        mockMvc.perform(post("/sales")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("sales_amount="))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postNaNSales() throws Exception {
        mockMvc.perform(post("/sales")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("sales_amount=not_a_number"))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().isBadRequest());
    }
}
