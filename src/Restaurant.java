import java.util.LinkedList;
import java.util.Queue;

/**
 * The Restaurant class represents a queue of Customer objects.
 * It provides basic queue operations such as enqueue, dequeue, peek, size, and isEmpty,
 * along with a custom toString representation.
 */
public class Restaurant {
    private Queue<Customer> customers;

    /**
     * Constructs an empty Restaurant queue.
     */
    public Restaurant() {
        customers = new LinkedList<>();
    }

    /**
     * Enqueues a new Customer into this Restaurant.
     *
     * @param c the Customer to enqueue
     */
    public void enqueue(Customer c) {
        customers.offer(c);
    }

    /**
     * Dequeues and returns the Customer at the front of this Restaurant's queue.
     *
     * @return the first Customer, or null if empty
     */
    public Customer dequeue() {
        return customers.poll();
    }

    /**
     * Returns (without removing) the Customer at the front of this Restaurant's queue.
     *
     * @return the first Customer, or null if empty
     */
    public Customer peek() {
        return customers.peek();
    }

    /**
     * Returns the number of Customers currently in this Restaurant.
     *
     * @return the number of Customers
     */
    public int size() {
        return customers.size();
    }

    /**
     * Checks if this Restaurant has no Customers.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return customers.isEmpty();
    }

    /**
     * Returns a string representation of this Restaurant's queue in the format:
     * {[#orderNumber, foodAbbrev, timeToServe min.], ...}
     *
     * @return a string describing the queue of Customers
     */
    public String toString() {
        if (customers.isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        for (Customer c : customers) {
            sb.append(c).append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }
}
