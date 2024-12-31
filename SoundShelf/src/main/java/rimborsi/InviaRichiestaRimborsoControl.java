package rimborsi;

import ordini.ElementoOrdine;
import ordini.ElementoOrdineDAO;
import ordini.Order;
import ordini.OrderDAO;
import prodotti.ProductDAO;
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
import java.util.List;

@WebServlet("/inviaRichiestaRimborsoControl")
public class InviaRichiestaRimborsoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO;
    private ElementoOrdineDAO elementoOrdineDAO;
    private RefoundRequestDAO refoundRequestDAO;
    private UtenteRegistratoDAO utenteDAO;

    @Override
    public void init() throws ServletException {
    	productDAO = new ProductDAO();
        elementoOrdineDAO = new ElementoOrdineDAO();
        refoundRequestDAO = new RefoundRequestDAO();
        utenteDAO = new UtenteRegistratoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UtenteRegistrato user = (UtenteRegistrato) session.getAttribute("user");

        if (user == null) {
            request.setAttribute("errorMessage", "Devi essere loggato per richiedere un rimborso.");
            request.getRequestDispatcher("view/error/messaggioErrore.jsp").forward(request, response);
            return;
        }
        
        String detailCode = request.getParameter("detailCode");

        if (detailCode != null && !detailCode.isEmpty()) {
            int detailId = Integer.parseInt(detailCode);
            ElementoOrdine detail = elementoOrdineDAO.getOrderDetailsById(detailId);

            if (detail != null) {
            	request.setAttribute("product", detail);
            	request.setAttribute("quantita", detail.getQuantita());
                request.getRequestDispatcher("/view/rimborsiInterface/richiestaRimborsoForm.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Dettagli prodotto non trovati.");
                request.getRequestDispatcher("/view/error/messaggioErrore.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Parametro non valido.");
            request.getRequestDispatcher("/view/error/messaggioErrore.jsp").forward(request, response);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UtenteRegistrato user = (UtenteRegistrato) session.getAttribute("user");

        if (user == null) {
            request.setAttribute("errorMessage", "Devi essere loggato per richiedere un rimborso.");
            request.getRequestDispatcher("view/error/messaggioErrore.jsp").forward(request, response);
            return;
        }

        String productCode = request.getParameter("productCode");
        String reason = request.getParameter("reason");
        String iban = request.getParameter("iban");

        if (productCode != null && reason != null && iban != null) {
            int productId = Integer.parseInt(productCode);

            ElementoOrdine product = elementoOrdineDAO.getOrderDetailsById(productId);

            if (product != null) {
                RefoundRequest refundRequest = new RefoundRequest();
                refundRequest.setIdOrdine(product.getIdOrdine());
                refundRequest.setIdProdotto(productId);
                refundRequest.setEmailCliente(user.getEmail());
                refundRequest.setMotivo(reason);
                refundRequest.setIban(iban);
                refundRequest.setStato(StatoRimborso.IN_LAVORAZIONE);

                try {
                    refoundRequestDAO.saveRichiestaRimborso(refundRequest);
                    request.setAttribute("message", "La tua richiesta di rimborso è stata inviata con successo.");
                    request.getRequestDispatcher("listaOrdiniUtente").forward(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage", "Errore nel salvataggio della richiesta di rimborso.");
                    request.getRequestDispatcher("/view/error/messaggioErrore.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Prodotto non trovato.");
                request.getRequestDispatcher("/view/error/messaggioErrore.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Parametri non validi.");
            request.getRequestDispatcher("/view/error/messaggioErrore.jsp").forward(request, response);
        }
    }
}
