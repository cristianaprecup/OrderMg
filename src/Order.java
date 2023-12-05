import java.util.PriorityQueue;
import java.util.Scanner;

class Order implements Comparable<Order> {
    int arrivalTime; // The time when the order arrives at the restaurant
    int duration;    // The time needed to prepare the order

    public Order(int arrivalTime, int duration) {
        this.arrivalTime = arrivalTime;
        this.duration = duration;
    }

    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.arrivalTime, other.arrivalTime);
    }
}