package entity;

import java.sql.SQLException;

public class CartItem {
    private Ticket ticket;
    private int quantity;
    private EventDAO eventDAO = new EventDAO();

    public CartItem(Ticket ticket, int quantity) {
        this.ticket = ticket;
        this.quantity = quantity;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return ticket.getPrezzoUnitario() * quantity;
    }
    
    public int getCodiceEventoBiglietto() {
        return ticket.getCodiceEvento();
    }
    
    public String getImageItem() throws SQLException {
		return eventDAO.getImage(ticket.getCodiceEvento());
    }
}

