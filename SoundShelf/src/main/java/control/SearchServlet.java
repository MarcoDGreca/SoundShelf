package control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import model.Event;
import model.EventDAO;
import util.InputSanitizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EventDAO eventDAO;

    @Override
    public void init() throws ServletException {
        eventDAO = new EventDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = InputSanitizer.sanitize(request.getParameter("nome"));
        String tipo = InputSanitizer.sanitize(request.getParameter("tipo"));
        String dataInizioStr = InputSanitizer.sanitize(request.getParameter("dataInizio"));
        String dataFineStr = InputSanitizer.sanitize(request.getParameter("dataFine"));
        String disponibilitaStr = InputSanitizer.sanitize(request.getParameter("disponibilita"));
        String prezzoMaxStr = InputSanitizer.sanitize(request.getParameter("prezzoMax"));

        Date dataInizio = (dataInizioStr != null && !dataInizioStr.isEmpty()) ? Date.valueOf(dataInizioStr) : null;
        Date dataFine = (dataFineStr != null && !dataFineStr.isEmpty()) ? Date.valueOf(dataFineStr) : null;
        Integer disponibilita = (disponibilitaStr != null && !disponibilitaStr.isEmpty()) ? Integer.valueOf(disponibilitaStr) : null;
        Double prezzoMax = (prezzoMaxStr != null && !prezzoMaxStr.isEmpty()) ? Double.valueOf(prezzoMaxStr) : null;

        List<Event> events = eventDAO.searchEvents(nome, tipo, dataInizio, dataFine, disponibilita, prezzoMax);
        Set<Event> uniqueEvents = new HashSet<>(events);

        JSONArray jsonArray = new JSONArray();
        for (Event event : uniqueEvents) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("codiceEvento", event.getCodiceEvento());
                jsonObject.put("nome", event.getNome());
                jsonObject.put("luogo", event.getLuogo());
                jsonObject.put("dataEvento", event.getDataEvento().toString());
                jsonObject.put("orario", event.getOrario());
                jsonObject.put("tipo", event.getTipo());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonArray.toString());
    }
}

