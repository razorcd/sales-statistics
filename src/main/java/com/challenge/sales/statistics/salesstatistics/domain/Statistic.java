package com.challenge.sales.statistics.salesstatistics.domain;

import java.util.Objects;

public class Statistic {

    private String total_sales_amount;
    private String average_amount_per_order;

    public Statistic() {
    }

    public Statistic(String total_sales_amount, String average_amount_per_order) {
        this.total_sales_amount = total_sales_amount;
        this.average_amount_per_order = average_amount_per_order;
    }

    public String getTotal_sales_amount() {
        return total_sales_amount;
    }

    public void setTotal_sales_amount(String total_sales_amount) {
        this.total_sales_amount = total_sales_amount;
    }

    public String getAverage_amount_per_order() {
        return average_amount_per_order;
    }

    public void setAverage_amount_per_order(String average_amount_per_order) {
        this.average_amount_per_order = average_amount_per_order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic that = (Statistic) o;
        return Objects.equals(total_sales_amount, that.total_sales_amount) &&
                Objects.equals(average_amount_per_order, that.average_amount_per_order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(total_sales_amount, average_amount_per_order);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StatisticDto{");
        sb.append("total_sales_amount='").append(total_sales_amount).append('\'');
        sb.append(", average_amount_per_order='").append(average_amount_per_order).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
