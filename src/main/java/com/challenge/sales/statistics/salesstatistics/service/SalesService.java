package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SalesService {

    private SalesRepository salesRepository;

    @Autowired
    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public void store(BigDecimal amount) {
        salesRepository.saveAmount(amount);
    }
}
