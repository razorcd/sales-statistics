package com.challenge.sales.statistics.salesstatistics.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class StatisticsResponseDto {

    @JsonProperty("total_sales_amount")
    private String totalSalesAmount;

    @JsonProperty("average_amount_per_order")
    private String averageAmountPerOrder;

    public StatisticsResponseDto() {
    }

    public StatisticsResponseDto(String totalSalesAmount, String averageAmountPerOrder) {
        this.totalSalesAmount = totalSalesAmount;
        this.averageAmountPerOrder = averageAmountPerOrder;
    }

    public String getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public void setTotalSalesAmount(String totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public String getAverageAmountPerOrder() {
        return averageAmountPerOrder;
    }

    public void setAverageAmountPerOrder(String averageAmountPerOrder) {
        this.averageAmountPerOrder = averageAmountPerOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticsResponseDto that = (StatisticsResponseDto) o;
        return Objects.equals(totalSalesAmount, that.totalSalesAmount) &&
                Objects.equals(averageAmountPerOrder, that.averageAmountPerOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalSalesAmount, averageAmountPerOrder);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StatisticsResponseDto{");
        sb.append("totalSalesAmount='").append(totalSalesAmount).append('\'');
        sb.append(", averageAmountPerOrder='").append(averageAmountPerOrder).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
