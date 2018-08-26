package com.challenge.sales.statistics.salesstatistics.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a sales amount entity.
 */
public class Amount {

    /**
     * The sales amount value.
     */
    private BigDecimal value;

    /**
     * The time when the amount is created.
     */
    private LocalDateTime createdAt;

    public Amount() {
    }

    public Amount(BigDecimal value, LocalDateTime createdAt) {
        this.value = value;
        this.createdAt = createdAt;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return Objects.equals(value, amount.value) &&
                Objects.equals(createdAt, amount.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, createdAt);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Amount{");
        sb.append("value=").append(value);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
