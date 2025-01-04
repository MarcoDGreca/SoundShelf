import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ordini.AcquistoControl;
import ordini.Cart;
import ordini.CartItem;
import ordini.ElementoOrdineDAO;
import ordini.Order;
import ordini.OrderDAO;
import prodotti.Product;
import prodotti.ProductDAO;
import utente.UtenteRegistrato;
import util.DataSource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TC2 {

    @InjectMocks
    private AcquistoControl acquistoControl;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private Cart cart;

    @Mock
    private UtenteRegistrato user;

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private ElementoOrdineDAO elementoOrdineDAO;

    @Mock
    private Product product;

    @Mock
    private ProductDAO productDAO;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @BeforeEach
    public void setUp() throws ServletException, SQLException {
        lenient().when(request.getSession()).thenReturn(session);
        lenient().when(session.getAttribute("cart")).thenReturn(cart);
        lenient().when(session.getAttribute("user")).thenReturn(user);
        lenient().when(dataSource.getConnection()).thenReturn(connection);
        DataSource.init("jdbc:mysql://localhost:3306/SoundShelf", "root", "W23e45f78.");
        acquistoControl.orderDAO = orderDAO;
        acquistoControl.elementoOrdineDAO = elementoOrdineDAO;
        acquistoControl.productDAO = productDAO;
    }

    @Test
    public void testTC3_2_UnauthenticatedUser() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(cart.isEmpty()).thenReturn(false); // Carrello non vuoto
        when(request.getRequestDispatcher("view/error/messaggioErrore.jsp")).thenReturn(requestDispatcher);

        acquistoControl.doGet(request, response);

        verify(request).setAttribute("errorMessage", "Per procedere con l'acquisto è necessario essere autenticati.");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testTC3_3_AuthenticatedUserWithSavedAddress() throws ServletException, IOException, SQLException {
        String shippingAddress = "Via Orsetti Gommosi 17";
        double totalPrice = 100.0;

        when(cart.isEmpty()).thenReturn(false);
        when(cart.getTotalPrice()).thenReturn(totalPrice);
        when(request.getParameter("shippingAddress")).thenReturn(shippingAddress);
        when(orderDAO.addOrder(any(Order.class))).thenReturn(1);
        when(product.getAvailability()).thenReturn(10);

        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(product, 1));
        when(cart.getItems()).thenReturn(items);

        acquistoControl.doPost(request, response);

        verify(response).sendRedirect(anyString());
        verify(orderDAO).addOrder(any(Order.class));
    }

    @Test
    public void testTC3_4_ProductNotAvailable() throws ServletException, IOException, SQLException {
        when(cart.isEmpty()).thenReturn(false);
        when(cart.getTotalPrice()).thenReturn(100.0);
        when(request.getParameter("shippingAddress")).thenReturn("Indirizzo Valido");

        when(product.getAvailability()).thenReturn(0); // Prodotto non disponibile
        when(product.getName()).thenReturn("Prodotto Test");

        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(product, 1));
        when(cart.getItems()).thenReturn(items);
        when(request.getRequestDispatcher("view/error/messaggioErrore.jsp")).thenReturn(requestDispatcher);

        acquistoControl.doPost(request, response);

        verify(request).setAttribute("errorMessage", "Il prodotto Prodotto Test non è disponibile nella quantità richiesta.");
        verify(requestDispatcher).forward(request, response);
    }


}
