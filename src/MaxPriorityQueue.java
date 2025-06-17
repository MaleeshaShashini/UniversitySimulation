// Java file for queue that gives the "most important" item first
import java.util.PriorityQueue; // Java's built-in priority queue
import java.util.Comparator;

public class MaxPriorityQueue<T> { // Can hold any type of items 
    private PriorityQueue<T> javaPriorityQueue;

    // Constructor to Sets up the queue with a custom comparator
    public MaxPriorityQueue(Comparator<T> itemComparator) {
        //  MAX-heap (biggest/most important first)
        javaPriorityQueue = new PriorityQueue<>(itemComparator.reversed());
    }

    // Adds an item to the queue
    public void insertItem(T itemToAdd) {
        javaPriorityQueue.offer(itemToAdd);
    }

    // Removes and returns the highest priority item
    public T extractMaxItem() {
        return javaPriorityQueue.poll();
    }

    // Checks if the queue is empty
    public boolean isEmpty() {
        return javaPriorityQueue.isEmpty();
    }
}