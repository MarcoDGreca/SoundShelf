package rimborsi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ordini.ElementoOrdine;
import ordini.ElementoOrdineDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;

@WebServlet("/gestisciRichiesteRimborsoControl")
public class GestisciRichiestaRimborsoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RefoundRequestDAO richiestaRimborsoDAO;
    private RimborsoDAO rimborsoDAO;
    private ElementoOrdineDAO elementoOrdineDAO;

    @Override
    public void init() throws ServletException {
        richiestaRimborsoDAO = new RefoundRequestDAO();
        rimborsoDAO = new RimborsoDAO();
        elementoOrdineDAO = new ElementoOrdineDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<RefoundRequest> richieste = richiestaRimborsoDAO.getAllRichiesteRimborso();
            request.setAttribute("richieste", richieste);
            request.getRequestDispatcher("/rimborsoInterface/catalogoRichiesteRimborso.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("message", "Errore nel recupero delle richieste di rimborso.");
            request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<RefoundRequest> richieste = richiestaRimborsoDAO.getAllRichiesteRimborso();

            for (RefoundRequest richiesta : richieste) {
                String newState = request.getParameter("newState_" + richiesta.getIdOrdine() + "_" + richiesta.getIdProdotto());
                if (newState != null) {
                    try {
                        StatoRimborso stato = StatoRimborso.fromString(newState);
                        richiesta.setStato(stato);
                        richiestaRimborsoDAO.updateRichiestaRimborso(richiesta);

                        if (stato == StatoRimborso.ACCETTATO) {
                            double importoRimborso = calcolaImportoRimborso(richiesta);
                            Rimborso rimborso = new Rimborso(0, importoRimborso, new Date(System.currentTimeMillis()), richiesta.getId());
                            rimborsoDAO.creaRimborso(rimborso);
                        }
                    } catch (IllegalArgumentException e) {
                        request.setAttribute("message", "Stato non valido: " + newState);
                        request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
                        return;
                    }
                }
            }

            response.sendRedirect("rimborso/gestisciRichiesteRimborsoControl?success=modifiche salvate");
        } catch (SQLException e) {
            request.setAttribute("message", "Errore durante il salvataggio delle modifiche.");
            request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "Errore inaspettato.");
            request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
        }
    }

    private double calcolaImportoRimborso(RefoundRequest richiesta) {
        double totalImporto = 0.0;
        List<ElementoOrdine> prodotti = elementoOrdineDAO.getOrderDetailsByOrderId(richiesta.getIdOrdine());
        for (ElementoOrdine prodotto : prodotti) {
            totalImporto += prodotto.getPrezzoUnitario() * prodotto.getQuantita();
        }
        return totalImporto;
    }

}
