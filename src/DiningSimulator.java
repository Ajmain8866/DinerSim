import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The DiningSimulator class manages the entire simulation of multiple restaurants.
 * Each restaurant seats Customers who arrive with a certain probability. Customers
 * order various foods, and cooking times can be adjusted based on the number of chefs.
 * Once served, Customers add to the Restaurant's profit and leave after finishing their meals.
 */
public class DiningSimulator {
    private List<Restaurant> restaurants;
    private int chefs;
    private int duration;
    private double arrivalProb;
    private int maxCustomerSize;
    private int numRestaurants;
    private int customersLost;
    private int totalServiceTime;
    private int customersServed;
    private int profit;

    /**
     * Constructs a new DiningSimulator with no initial restaurants.
     */
    public DiningSimulator() {
        restaurants = new ArrayList<>();
    }

    /**
     * Returns an integer in the range [minVal, maxVal].
     *
     * @param minVal the minimum bound (inclusive)
     * @param maxVal the maximum bound (inclusive)
     * @return a random integer between minVal and maxVal
     */
    private int randInt(int minVal, int maxVal) {
        return (int)(Math.random() * (maxVal - minVal + 1)) + minVal;
    }

    /**
     * Returns the base cooking time for a given food when there are exactly 3 chefs (the baseline).
     *
     * @param food the food name
     * @return the base cooking time in minutes
     */
    private int getBaseTime(String food) {
        switch (food) {
            case "Steak":
            case "Chicken Wings": return 30;
            case "Cheeseburger":
            case "Chicken Tenders": return 25;
            case "Grilled Cheese": return 15;
            default: return 0;
        }
    }

    /**
     * Returns the price of a given food.
     *
     * @param food the food name
     * @return the price in dollars
     */
    private int getBasePrice(String food) {
        switch (food) {
            case "Steak": return 25;
            case "Chicken Wings": return 20;
            case "Cheeseburger": return 15;
            case "Chicken Tenders": return 10;
            case "Grilled Cheese": return 10;
            default: return 0;
        }
    }

    /**
     * Adjusts the cooking time for the given food based on the number of chefs.
     * Every chef above 3 reduces cooking time by 5 minutes, up to 10 minutes total.
     * The final result is clamped to 0 as a minimum.
     *
     * @param food the name of the food
     * @return the adjusted cooking time in minutes (NOT counting eating time)
     */
    private int adjustedCookTime(String food) {
        int base = getBaseTime(food);
        int diff = chefs - 3;
        int cook = base - (diff * 5);

        // If we have extra chefs, we can't reduce by more than 10 total.
        if (diff > 0) {
            // clamp to base - 10 at lowest
            cook = Math.max(base - 10, cook);
        }
        // cooking time shouldn't go below 0
        cook = Math.max(cook, 0);
        return cook;
    }

    /**
     * Runs the simulation for the specified number of time units, managing arrivals,
     * serving Customers, and printing results at each step.
     *
     * @return the average time spent per served customer
     */
    public double simulate() {
        customersLost = 0;
        totalServiceTime = 0;
        customersServed = 0;
        profit = 0;

        System.out.println();

        for (int i = 1; i <= duration; i++) {
            System.out.println("Time: " + i);

            // 1. ARRIVALS: seat up to 4 new customers per restaurant
            for (int r = 0; r < numRestaurants; r++) {
                for (int a = 0; a < 4; a++) {
                    if (Math.random() <= arrivalProb) {
                        if (restaurants.get(r).size() < maxCustomerSize) {
                            String[] foods = {
                                    "Steak",
                                    "Chicken Wings",
                                    "Cheeseburger",
                                    "Chicken Tenders",
                                    "Grilled Cheese"
                            };
                            String chosen = foods[randInt(0, foods.length - 1)];

                            // cooking time + 15 minutes for eating
                            int cookTime = adjustedCookTime(chosen);
                            int totalTime = cookTime + 15;
                            int price = getBasePrice(chosen);

                            Customer c = new Customer(chosen, i, totalTime, price);
                            restaurants.get(r).enqueue(c);

                            System.out.println("Customer #" + c.getOrderNumber()
                                    + " has entered Restaurant " + (r + 1) + ".");
                            System.out.println("Customer #" + c.getOrderNumber()
                                    + " has been seated with order \"" + chosen + "\".");
                        } else {
                            customersLost++;
                            System.out.println("A customer cannot be seated at Restaurant "
                                    + (r + 1) + "! They have left the restaurant.");
                        }
                    }
                }
            }

            // 2. PRINT QUEUES
            // so newly arrived customers appear with full cooking+eating time
            for (int r = 0; r < numRestaurants; r++) {
                System.out.println("R" + (r + 1) + ": " + restaurants.get(r));
            }
            System.out.println();

            // 3. DECREMENT / SERVE
            // Now we simulate that 5 minutes passes. The customers lose 5 min from timeToServe.
            for (int r = 0; r < numRestaurants; r++) {
                List<Customer> temp = new ArrayList<>();
                while (!restaurants.get(r).isEmpty()) {
                    Customer front = restaurants.get(r).dequeue();

                    // subtract 5 minutes from each customer's time
                    front.setTimeToServe(front.getTimeToServe() - 5);

                    if (front.getTimeToServe() <= 0) {
                        System.out.println("Customer #" + front.getOrderNumber()
                                + " has enjoyed their food! $" + front.getPriceOfFood() + " profit.");
                        profit += front.getPriceOfFood();

                        int timeSpent = (i - front.getTimeArrived()) * 5;  // actual minutes
                        totalServiceTime += timeSpent;
                        customersServed++;
                    } else {
                        temp.add(front);
                    }
                }
                // re-queue those not yet finished
                for (Customer c : temp) {
                    restaurants.get(r).enqueue(c);
                }
            }

            System.out.println();
        }

        System.out.println("Simulation ending...");
        System.out.println();

        double avg = (customersServed == 0)
                ? 0
                : (double) totalServiceTime / customersServed;

        System.out.println("Total customer time: " + totalServiceTime + " minutes");
        System.out.println("Total customers served: " + customersServed);
        System.out.printf("Average customer time lapse: %.2f minutes per order\n", avg);
        System.out.println("Total Profit: $" + profit);
        System.out.println("Customers that left: " + customersLost);
        System.out.println();

        return avg;
    }

    /**
     * The main entry point for the DiningSimulator program.
     * Prompts the user for parameters, constructs a DiningSimulator,
     * and runs simulations until the user decides to quit.
     *
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Starting simulator...");
            System.out.println();

            DiningSimulator ds = new DiningSimulator();

            System.out.print("Enter the number of restaurants: ");
            ds.numRestaurants = Integer.parseInt(input.nextLine());

            for (int i = 0; i < ds.numRestaurants; i++) {
                ds.restaurants.add(new Restaurant());
            }

            System.out.print("Enter the maximum number of customers a restaurant can serve: ");
            ds.maxCustomerSize = Integer.parseInt(input.nextLine());

            System.out.print("Enter the arrival probability of a customer: ");
            ds.arrivalProb = Double.parseDouble(input.nextLine());

            System.out.print("Enter the number of chefs: ");
            ds.chefs = Integer.parseInt(input.nextLine());

            System.out.print("Enter the number of simulation units: ");
            ds.duration = Integer.parseInt(input.nextLine());

            ds.simulate();

            System.out.print("Do you want to try another simulation? (y/n): ");
            String ans = input.nextLine();
            System.out.println();

            if (!ans.equalsIgnoreCase("y")) {
                System.out.println("Program terminating normally...");
                break;
            }
        }
    }
}
