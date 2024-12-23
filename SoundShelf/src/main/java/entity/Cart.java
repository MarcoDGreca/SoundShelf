package entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addTicket(Ticket ticket) {
        for (CartItem item : items) {
            if (item.getTicket().getCodiceBiglietto() == ticket.getCodiceBiglietto()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        items.add(new CartItem(ticket, 1));
    }

    public void removeTicket(Ticket ticket) {
        items.removeIf(item -> item.getTicket().getCodiceBiglietto() == ticket.getCodiceBiglietto());
    }

    public void updateQuantity(Ticket ticket, int quantity) {
        for (CartItem item : items) {
            if (item.getTicket().getCodiceBiglietto() == ticket.getCodiceBiglietto()) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    items.remove(item);
                }
                return;
            }
        }
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
}
