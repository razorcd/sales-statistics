package com.challenge.sales.statistics.salesstatistics.controller;


import com.challenge.sales.statistics.salesstatistics.domain.TotalAmount;
import com.challenge.sales.statistics.salesstatistics.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("statistics")
public class StatisticsController {

    private StatisticService statisticService;

    @Autowired
    public StatisticsController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    /**
     * Get statistics for recent sale amounts.
     *
     * @return [Statistics] statistics for latest sales amount.
     */
    @GetMapping
    public TotalAmount getStatistics() {
        return statisticService.getRecentTotalAmount();
    }

}
