package com.challenge.sales.statistics.salesstatistics.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@Controller
@RequestMapping("sales")
public class SalesController {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, params = {"sales_amount!="})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postSales(@RequestParam(value = "sales_amount") BigDecimal salesAmount) {
        System.out.println("Rezult: " + salesAmount);

    }
}

