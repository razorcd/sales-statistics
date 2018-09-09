package com.challenge.sales.statistics.salesstatistics.domain;

import java.util.Objects;

/**
 * Represents a sales amount entity.
 */
public class Amount {

    /**
     * The sales amount value.
     */
    private final double value;

    /**
     * The time when the amount is created in ms.
     */
    private final long createdAt;

    public Amount(double value, long createdAt) {
        this.value = value;
        this.createdAt = createdAt;
    }

    /**
     * Check if current amount was created before specified time in ms.
     *
     * @param timeInMs the time in ms to compare with.
     * @return [boolean] if amount was created before specified time.
     */
    public boolean isBefore(long timeInMs) {
        return createdAt <= timeInMs;
    }

    /**
     * Check if current amount was created after specified time in ms.
     *
     * @param timeInMs the time in ms to compare with.
     * @return [boolean] if amount was created after specified time.
     */
    public boolean isAfter(long timeInMs) {
        return createdAt > timeInMs;
    }

    public double getValue() {
        return value;
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
