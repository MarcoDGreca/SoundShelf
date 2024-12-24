package entity;

import java.util.ArrayList;
import java.util.List;

public class OrderCatalogue {

    private List<Order> orders;

    public OrderCatalogue() {
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
