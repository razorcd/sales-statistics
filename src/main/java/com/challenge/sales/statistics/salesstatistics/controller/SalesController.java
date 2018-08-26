package com.challenge.sales.statistics.salesstatistics.controller;

import com.challenge.sales.statistics.salesstatistics.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("sales")
public class SalesController {

    private SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    /**
     * Add a new sales amount that just happened.
     *
     * @param salesAmount the sales amount to add.
     */
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, params = {"sales_amount!="})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postSales(@RequestParam(value = "sales_amount") double salesAmount) {
        salesService.store(salesAmount);
    }
}

