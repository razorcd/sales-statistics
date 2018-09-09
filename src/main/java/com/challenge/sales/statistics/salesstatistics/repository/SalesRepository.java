package com.challenge.sales.statistics.salesstatistics.repository;

import com.challenge.sales.statistics.salesstatistics.domain.Amount;

import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
public class SalesRepository {

    /**
     * Holds a list of concurrent queues.
     * All sales are stored in separate concurrent queues
     * therefor intensive multi threaded writing will not block threads.
     */
    private final List<Queue<Amount>> amountsQueueList;

    private final int queueCount;

    public SalesRepository() {
        this.amountsQueueList = Arrays.asList(
                new ConcurrentLinkedQueue<>(),
                new ConcurrentLinkedQueue<>(),
                new ConcurrentLinkedQueue<>(),
                new ConcurrentLinkedQueue<>(),
                new ConcurrentLinkedQueue<>(),
                new ConcurrentLinkedQueue<>(),
                new ConcurrentLinkedQueue<>(),
                new ConcurrentLinkedQueue<>(),
                new ConcurrentLinkedQueue<>(),
                new ConcurrentLinkedQueue<>()
        );
        this.queueCount = amountsQueueList.size();
    }

    /**
     * Adds sales amount to the current queue.
     *
     * @param amount the amount to store
     */
    public void saveAmount(Amount amount) {
        getCurrentQueue().add(amount);
    }

    public void deleteAll() {
        amountsQueueList.forEach(Collection::clear);
    }

    /**
     * Removes all amounts that are older than the specified time from the ending side of the queue.
     *
     * @param time the time limit that defines an old amount.
     */
    public void cleanOld(long time) {
        amountsQueueList.forEach(queue -> {
            while (!queue.isEmpty() && queue.peek().isBefore(time)) {
                queue.poll();
            }
        });
    }

    /**
     * Execute a function on all queues in parallel.
     *
     * @param function the function to execute.
     * @param <S> the type of the return of the function.
     * @return {@code List<S>} a list of function return types.
     */
    public <S> List<S> onEachQueueExecute(Function<Queue<Amount>, S> function) {
        return amountsQueueList.parallelStream()
                .map(function)
                .collect(Collectors.toList());
    }

    /**
     * Get all amounts stored. Use for testing.
     * @return {@code List<Amount>} list of all stored amounts.
     */
    public List<Amount> getAmounts() {
        return amountsQueueList.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Count all stored amounts.
     *
     * @return [int] count of stored amounts.
     */
    public int count() {
        return onEachQueueExecute(Queue::size).stream().reduce(Integer::sum)
                .orElse(0);
    }

    /**
     * Get current queue. Every time this method is called the next queue in line will be returned.
     * @return {@code Queue<Amount>} the current queue.
     */
    private Queue<Amount> getCurrentQueue() {
        int queueIndex = new SplittableRandom().nextInt(queueCount);

        return amountsQueueList.get(queueIndex);
    }
}