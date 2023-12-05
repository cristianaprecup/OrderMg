import java.util.PriorityQueue;
import java.util.Scanner;

public class OrderProcessing {
    public void processOrders(int numOrders, int closingTime, Scanner scanner) {
        PriorityQueue<Order> orders = new PriorityQueue<>();
        System.out.println("Enter the arrival time and duration for each order:");
        for (int i = 0; i < numOrders; i++) {
            int arrivalTime = scanner.nextInt();
            int duration = scanner.nextInt();
            Order order = new Order(arrivalTime, duration);
            orders.add(order);
        }

        int maxDuration = 0;
        int previousOrderTime = -1;
        boolean unfulfilledOrder = false;

        int orderCount = 1;
        while (!orders.isEmpty() && orders.peek().arrivalTime < closingTime) {
            Order order = orders.poll();

            int theoreticalCompletionTime = order.arrivalTime + order.duration;

            int actualCompletionTime;
            if (order.arrivalTime > previousOrderTime) {
                actualCompletionTime = order.arrivalTime + order.duration;
            } else {
                actualCompletionTime = previousOrderTime + order.duration;
            }

            if (order.duration > maxDuration) {
                maxDuration = order.duration;
            }

            previousOrderTime = actualCompletionTime;

            System.out.println("Order " + orderCount + ": theoretical completion time = " + theoreticalCompletionTime
                    + ", actual completion time = " + actualCompletionTime + ";");
            orderCount++;

            if (actualCompletionTime > closingTime) {
                unfulfilledOrder = true;
            }
        }

        int startTime = 0;
        while (startTime < closingTime && !orders.isEmpty()) {
            int endTime = startTime > orders.peek().arrivalTime ? startTime : orders.peek().arrivalTime;

            if (endTime < closingTime) {
                System.out.println("The chef takes a break (the queue is empty) between the times " + endTime
                        + " and " + closingTime);
            }

            int nextArrivalTime = orders.isEmpty() ? closingTime : orders.peek().arrivalTime;
            int nextCompletionTime = startTime + orders.peek().duration;

            if (nextCompletionTime > nextArrivalTime) {
                startTime = nextArrivalTime;
            } else {
                startTime = nextCompletionTime;
            }

            orders.poll();
        }

        if (!orders.isEmpty() || previousOrderTime > closingTime) {
            unfulfilledOrder = true;
        }

        if (unfulfilledOrder) {
            System.out.println("There are orders that remain unfulfilled after the closing time of the restaurant.");
        } else {
            System.out.println("All orders are fulfilled before the closing time of the restaurant.");
        }

        System.out.println("The maximum duration the chef has to work on a single order is " + maxDuration);
    }
}