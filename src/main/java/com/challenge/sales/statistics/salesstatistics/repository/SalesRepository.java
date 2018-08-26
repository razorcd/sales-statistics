package com.challenge.sales.statistics.salesstatistics.repository;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;
import org.springframework.stereotype.Repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class SalesRepository {

    private final Queue<Amount> amounts;

    public SalesRepository() {
        amounts = new ConcurrentLinkedQueue<>();
    }

    public Queue<Amount> getAmounts() {
        return amounts;
//        return new ConcurrentLinkedQueue(amounts); // TODO: return copy? too slow
    }

    /**
     * Adds amount to the front end of the queue.
     *
     * @param amount the amount to store
     */
    public void saveAmount(Amount amount) {
        amounts.add(amount);
    }

    public void deleteAll() {
        amounts.clear();
    }

    /**
     * Removes all amounts that are older than the specified time from the ending side of the queue.
     *
     * @param time the time limit that defines an old amount.
     */
    public void cleanOld(long time) {
        while (!amounts.isEmpty() && amounts.peek().isBefore(time)) {
            amounts.poll();
        }
    }
}
