package com.challenge.sales.statistics.salesstatistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents a domain object containing statistics data for recent sale amounts.
 * Immutable object.
 */
public class Statistic {

    public static final Statistic ZERO = new Statistic(BigDecimal.ZERO, 0);
    private static final int DECIMAL_SCALE = 2;
    private static final RoundingMode DECIMAL_ROUNDING = RoundingMode.HALF_DOWN;
    /**
     * The sum of the values for the recent sale amounts.
     */
    @JsonIgnore
    private final BigDecimal totalSalesAmount;

    /**
     * The count of the recent sale amounts.
     */
    @JsonIgnore
    private final int salesAmountCount;

    public Statistic(BigDecimal totalSalesAmount, int salesAmountCount) {
        this.totalSalesAmount = totalSalesAmount;
        this.salesAmountCount = salesAmountCount;
    }

    public BigDecimal getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public int getSalesAmountCount() {
        return salesAmountCount;
    }

    @JsonProperty("total_sales_amount")
    public String getTotalSalesAmountString() {
        return totalSalesAmount.setScale(DECIMAL_SCALE, DECIMAL_ROUNDING).toString();
    }

    /**
     * The average value of the recent sales amounts.
     */
    @JsonProperty("average_amount_per_order")
    public String getAverageAmountPerOrder() {
        if (salesAmountCount==0) return BigDecimal.ZERO.setScale(DECIMAL_SCALE, DECIMAL_ROUNDING).toString();

        return totalSalesAmount.divide(BigDecimal.valueOf(salesAmountCount), DECIMAL_SCALE, DECIMAL_ROUNDING).toString();
    }

    /**
     * Get a new statistic instance by including the specified amount.
     *
     * @param amount the amount to include in the next statistic calculation.
     * @return [Statistic] a new updated instance
     */
    public Statistic add(Amount amount) {
        return new Statistic(totalSalesAmount.add(amount.getValue()), salesAmountCount+1);
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
