package com.challenge.sales.statistics.salesstatistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a domain object containing statistics data for recent sale amounts.
 * Immutable object.
 */
public class TotalAmount {

    public static final TotalAmount ZERO = new TotalAmount(0d, 0);

    /**
     * The total sum of the sale amounts.
     */
    @JsonIgnore
    private final double value;

    /**
     * The count of the summed sale amounts.
     */
    @JsonIgnore
    private final long salesAmountCount;

    public TotalAmount(double value, long salesAmountCount) {
        this.value = value;
        this.salesAmountCount = salesAmountCount;
    }

    public double getValue() {
        return value;
    }

    public long getSalesAmountCount() {
        return salesAmountCount;
    }

    @JsonProperty("total_sales_amount")
    public String getValueString() {
        return String.format("%.2f", value);
    }

    /**
     * The average value of the recent sales amounts.
     */
    @JsonProperty("average_amount_per_order")
    public String getAverageValueString() {
        return String.format("%.2f", getAverageSales());
    }

    /**
     * Get a new statistic instance by adding the specified statistic to current one.
     *
     * @param statistic the new statistic to add to current one.
     * @return [Statistic] the summed statistic from current and specified one.
     */
    public TotalAmount add(TotalAmount statistic) {
        return new TotalAmount(value + statistic.getValue(),
                             salesAmountCount + statistic.getSalesAmountCount());
    }

    /**
     * Get a new statistic instance by including the specified amount.
     *
     * @param amount the amount to include in the next statistic calculation.
     * @return [Statistic] a new updated instance
     */
    public TotalAmount add(Amount amount) {
        return new TotalAmount(value +amount.getValue(), salesAmountCount+1);
    }

    private double getAverageSales() {
        if (salesAmountCount==0) return 0;
        else return value / salesAmountCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalAmount statistic = (TotalAmount) o;
        return salesAmountCount == statistic.salesAmountCount &&
                Objects.equals(value, statistic.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, salesAmountCount);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Statistic{");
        sb.append("totalSalesAmount=").append(value);
        sb.append(", salesAmountCount=").append(salesAmountCount);
        sb.append('}');
        return sb.toString();
    }
}
