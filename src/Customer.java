/**
 * The Customer class represents a single diner in the simulation.
 * Each Customer is given a unique order number, a chosen food item, the price of that food,
 * the time of arrival in the simulation, and the total time needed to finish eating (timeToServe).
 * The class also keeps track of the total number of customers created.
 */
public class Customer {
    private static int totalCustomers = 0;
    private int orderNumber;
    private String food;
    private int priceOfFood;
    private int timeArrived;
    private int timeToServe;

    /**
     * Default constructor that increments the totalCustomers count
     * and assigns a unique orderNumber to this Customer.
     */
    public Customer() {
        totalCustomers++;
        orderNumber = totalCustomers;
    }

    /**
     * Constructs a new Customer with specified food item, arrival time, time to serve, and price.
     * Also increments the totalCustomers count and assigns a unique orderNumber.
     *
     * @param food         the name of the food that the Customer orders
     * @param timeArrived  the simulation step at which the Customer arrived
     * @param timeToServe  the total time needed (cooking + eating)
     * @param priceOfFood  the price for the chosen food item
     */
    public Customer(String food, int timeArrived, int timeToServe, int priceOfFood) {
        totalCustomers++;
        orderNumber = totalCustomers;
        this.food = food;
        this.timeArrived = timeArrived;
        this.timeToServe = timeToServe;
        this.priceOfFood = priceOfFood;
    }

    /**
     * Returns the total number of Customers created so far.
     *
     * @return the total number of Customers
     */
    public static int getTotalCustomers() {
        return totalCustomers;
    }

    /**
     * Returns the unique order number for this Customer.
     *
     * @return the Customer's order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Returns the name of the food ordered by this Customer.
     *
     * @return the Customer's chosen food
     */
    public String getFood() {
        return food;
    }

    /**
     * Sets the name of the food ordered by this Customer.
     *
     * @param food the new food name
     */
    public void setFood(String food) {
        this.food = food;
    }

    /**
     * Returns the price of the food ordered by this Customer.
     *
     * @return the price of the food
     */
    public int getPriceOfFood() {
        return priceOfFood;
    }

    /**
     * Sets the price of the food ordered by this Customer.
     *
     * @param priceOfFood the new price
     */
    public void setPriceOfFood(int priceOfFood) {
        this.priceOfFood = priceOfFood;
    }

    /**
     * Returns the simulation step when this Customer arrived.
     *
     * @return the arrival time (simulation step)
     */
    public int getTimeArrived() {
        return timeArrived;
    }

    /**
     * Sets the time at which this Customer arrived in the simulation.
     *
     * @param timeArrived the new arrival time (simulation step)
     */
    public void setTimeArrived(int timeArrived) {
        this.timeArrived = timeArrived;
    }

    /**
     * Returns the time it will take to serve this Customer (cooking + eating).
     *
     * @return the time to serve this Customer
     */
    public int getTimeToServe() {
        return timeToServe;
    }

    /**
     * Sets the time it will take to serve this Customer (cooking + eating).
     *
     * @param timeToServe the new time to serve
     */
    public void setTimeToServe(int timeToServe) {
        this.timeToServe = timeToServe;
    }

    /**
     * Converts a full food name (e.g., "Chicken Tenders") into its abbreviation (e.g., "CT").
     *
     * @param f the full name of the food
     * @return the abbreviated form
     */
    private String abbreviate(String f) {
        switch (f) {
            case "Steak": return "S";
            case "Chicken Wings": return "CW";
            case "Chicken Tenders": return "CT";
            case "Grilled Cheese": return "GC";
            case "Cheeseburger": return "C";
            default: return f;
        }
    }

    /**
     * Returns a string representation of this Customer in the format:
     * [#orderNumber, foodAbbreviation, timeToServe min.]
     *
     * @return a string describing this Customer
     */
    public String toString() {
        return "[#" + orderNumber + ", " + abbreviate(food) + ", " + timeToServe + " min.]";
    }
}
