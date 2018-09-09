package com.challenge.sales.statistics.salesstatistics;

import com.challenge.sales.statistics.salesstatistics.service.SalesService;
import com.challenge.sales.statistics.salesstatistics.service.StatisticService;
import org.rapidoid.http.MediaType;
import org.rapidoid.setup.On;

class ControllerHandler {

    private final SalesService salesService;
    private final StatisticService statisticService;

    ControllerHandler(SalesService salesService, StatisticService statisticService) {
        this.salesService = salesService;
        this.statisticService = statisticService;
    }

    void run() {
        On.post("/sales")
                .contentType(MediaType.create("application/x-www-form-urlencoded"))
                .serve((req, res) -> {
                    String salesAmount = req.posted("sales_amount");
                    salesService.store(mapAmountToCents(salesAmount));

                    res.code(202);
                    res.body("".getBytes());
                    return res;
                });

        On.get("/statistics")
                .json(statisticService::getRecentTotalAmount);
    }

    private long mapAmountToCents(String salesAmount) {
        return (long) (Double.valueOf(salesAmount) * 100);
    }
}