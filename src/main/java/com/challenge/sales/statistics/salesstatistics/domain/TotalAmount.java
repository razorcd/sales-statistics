package com.challenge.sales.statistics.salesstatistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a domain object containing statistics data for recent sale amounts.
 * Immutable object.
 */
public class TotalAmount {

    public static final TotalAmount ZERO = new TotalAmount(0L, 0);

    /**
     * The total sum of the sale amounts in cents.
     */
    @JsonIgnore
    private final long value;

    /**
     * The count of the summed sale amounts.
     */
    @JsonIgnore
    private final long salesAmountCount;

    public TotalAmount(long value, long salesAmountCount) {
        this.value = value;
        this.salesAmountCount = salesAmountCount;
    }

    public long getValue() {
        return value;
    }

    public long getSalesAmountCount() {
        return salesAmountCount;
    }

    @JsonProperty("total_sales_amount")
    public String getValueString() {
        return String.format("%.2f", (double)value / 100);
    }

    /**
     * The average value of the recent sales amounts.
     */
    @JsonProperty("average_amount_per_order")
    public String getAverageValueString() {
        return String.format("%.2f", getAverageSales() / 100);
    }

    /**
     * Get a new TotalAmount instance by including the specified Amount.
     *
     * @param amount the amount to include in the next total amount calculation.
     * @return {@link TotalAmount} a new updated instance
     */
    public TotalAmount add(Amount amount) {
        return new TotalAmount(value +amount.getValue(), salesAmountCount+1);
    }

    /**
     * Get a new TotalAmount instance by adding the specified TotalAmount to current one.
     *
     * @param totalAmount the new total amount to add to current one.
     * @return {@link TotalAmount} the summed total amount from current and specified one.
     */
    public TotalAmount add(TotalAmount totalAmount) {
        return new TotalAmount(value + totalAmount.getValue(),
                             salesAmountCount + totalAmount.getSalesAmountCount());
    }

    private double getAverageSales() {
        if (salesAmountCount==0) return 0;
        else return (double)value / salesAmountCount;
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
