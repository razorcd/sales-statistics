package com.challenge.sales.statistics.salesstatistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a domain object containing statistics data for recent sale amounts.
 * Immutable object.
 */
public class Statistic {

    public static final Statistic ZERO = new Statistic(0d, 0);

    /**
     * The sum of the values for the recent sale amounts.
     */
    @JsonIgnore
    private final double totalSalesAmount;

    /**
     * The count of the recent sale amounts.
     */
    @JsonIgnore
    private final int salesAmountCount;

    public Statistic(double totalSalesAmount, int salesAmountCount) {
        this.totalSalesAmount = totalSalesAmount;
        this.salesAmountCount = salesAmountCount;
    }

    public double getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public int getSalesAmountCount() {
        return salesAmountCount;
    }

    @JsonProperty("total_sales_amount")
    public String getTotalSalesAmountString() {
        return String.format("%.2f", totalSalesAmount);
    }

    /**
     * The average value of the recent sales amounts.
     */
    @JsonProperty("average_amount_per_order")
    public String getAverageAmountPerOrder() {
        if (salesAmountCount==0) return String.format("%.2f", 0d);

        return String.format("%.2f", getAverageSales());
    }

    /**
     * Get a new statistic instance by adding the specified statistic to current one.
     *
     * @param statistic the new statistic to add to current one.
     * @return [Statistic] the summed statistic from current and specified one.
     */
    public Statistic add(Statistic statistic) {
        return new Statistic(totalSalesAmount+statistic.getTotalSalesAmount(),
                             salesAmountCount+statistic.getSalesAmountCount());
    }

    /**
     * Get a new statistic instance by including the specified amount.
     *
     * @param amount the amount to include in the next statistic calculation.
     * @return [Statistic] a new updated instance
     */
    public Statistic add(Amount amount) {
        return new Statistic(totalSalesAmount+amount.getValue(), salesAmountCount+1);
    }

    private double getAverageSales() {
        return totalSalesAmount/salesAmountCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        return salesAmountCount == statistic.salesAmountCount &&
                Objects.equals(totalSalesAmount, statistic.totalSalesAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalSalesAmount, salesAmountCount);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Statistic{");
        sb.append("totalSalesAmount=").append(totalSalesAmount);
        sb.append(", salesAmountCount=").append(salesAmountCount);
        sb.append('}');
        return sb.toString();
    }
}
