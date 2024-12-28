package ordini;

import prodotti.Product;
import utente.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

@WebServlet("/checkout")
public class AcquistoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;
    private OrderDetailDAO orderDetailDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        orderDetailDAO = new OrderDetailDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            request.setAttribute("errorMessage", "Il carrello è vuoto.");
            request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
            return;
        }

        double totalPrice = cart.getTotalPrice();
        request.setAttribute("totalPrice", totalPrice);

        // Mostra anche l'indirizzo di spedizione salvato nell'utente
        Utente user = (Utente) session.getAttribute("user");
        if (user != null) {
            request.setAttribute("savedAddress", user.getIndirizzo()); // Aggiungiamo l'indirizzo salvato
        }

        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        Utente user = (Utente) session.getAttribute("user");

        if (cart != null && !cart.isEmpty() && user != null) {
            String shippingAddress = request.getParameter("shippingAddress");
            if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
                shippingAddress = user.getIndirizzo();
            }

            Order order = new Order();
            order.setEmailCliente(user.getEmail());
            order.setPrezzoTotale(cart.getTotalPrice());
            order.setDataOrdine(new java.sql.Date(System.currentTimeMillis()));
            order.setIndirizzoSpedizione(shippingAddress);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            java.sql.Date dataConsegna = new java.sql.Date(calendar.getTimeInMillis());
            order.setDataConsegna(dataConsegna);
            order.setStato(StatoOrdine.IN_LAVORAZIONE);

            try {
                int orderId = orderDAO.addOrder(order);

                for (CartItem item : cart.getItems()) {
                    Product product = item.getProduct();
                    int quantity = item.getQuantity();
                    orderDetailDAO.addOrderDetail(orderId, product.getProductCode(), quantity);
                }

                cart.clear();
                session.setAttribute("cart", cart);

                response.sendRedirect(request.getContextPath() + "/orderConfirmation.jsp");

            } catch (SQLException e) {
                request.setAttribute("errorMessage", "Errore nella creazione dell'ordine.");
                request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Il carrello è vuoto o l'utente non è autenticato.");
            request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
        }
    }
}
