package com.challenge.sales.statistics.salesstatistics.service;

import com.challenge.sales.statistics.salesstatistics.domain.Statistic;
import com.challenge.sales.statistics.salesstatistics.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@Service
public class StatisticService {

    private SalesRepository salesRepository;

    @Autowired
    public StatisticService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public Statistic getStatistic() {
        List<BigDecimal> saleAmounts = salesRepository.getAmounts();

        BigDecimal totalAmount = saleAmounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal averageAmount = totalAmount.equals(BigDecimal.ZERO) ?
                BigDecimal.ZERO : totalAmount.divide(new BigDecimal(saleAmounts.size()));

        return new Statistic(totalAmount.setScale(2, RoundingMode.HALF_EVEN).toString(),
                             averageAmount.setScale(2, RoundingMode.HALF_EVEN).toString());
    }

}
