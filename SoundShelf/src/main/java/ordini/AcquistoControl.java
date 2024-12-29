package ordini;

import prodotti.Product;
import utente.UtenteRegistrato;
import utente.UtenteRegistratoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

@WebServlet("/acquistoControl")
public class AcquistoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;
    private ElementoOrdineDAO elementoOrdineDAO;
    private UtenteRegistratoDAO userDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        elementoOrdineDAO = new ElementoOrdineDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            request.setAttribute("errorMessage", "Il carrello è vuoto.");
            request.getRequestDispatcher("view/error/MessaggioErrore.jsp").forward(request, response);
            return;
        }

        double totalPrice = cart.getTotalPrice();
        request.setAttribute("totalPrice", totalPrice);

        UtenteRegistrato user = (UtenteRegistrato) session.getAttribute("user");
        if (user != null) {
            request.setAttribute("savedAddress", user.getIndirizzo());
        }

        request.getRequestDispatcher("view/ordiniInterface/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        UtenteRegistrato user = (UtenteRegistrato) session.getAttribute("user");

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
                int numeroOrdine = orderDAO.addOrder(order);

                for (CartItem item : cart.getItems()) {
                    Product product = item.getProduct();
                    int quantity = item.getQuantity();
                    ElementoOrdine elementoOrdine = new ElementoOrdine();
                    elementoOrdine.setIdOrdine(numeroOrdine);
                    elementoOrdine.setIdProdotto(product.getProductCode());
                    elementoOrdine.setQuantita(quantity);
                    elementoOrdine.setPrezzoUnitario(product.getSalePrice());
                    elementoOrdineDAO.addOrderDetail(elementoOrdine);
                }

                cart.clear();
                session.setAttribute("cart", cart);

                response.sendRedirect(request.getContextPath() + "view/ordiniInterface/orderConfirmation.jsp");

            } catch (SQLException e) {
                request.setAttribute("errorMessage", "Errore nella creazione dell'ordine.");
                request.getRequestDispatcher("view/error/MessaggioErrore.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Il carrello è vuoto o l'utente non è autenticato.");
            request.getRequestDispatcher("view/error/MessaggioErrore.jsp").forward(request, response);
        }
    }
}
