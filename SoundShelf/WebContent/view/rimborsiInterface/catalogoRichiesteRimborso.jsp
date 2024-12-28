<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, rimborsi.*"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione Richieste di Rimborso</title>
</head>
<body>
    <jsp:include page="../pagePieces/header.jsp" />
    <h1>Gestione Richieste di Rimborso</h1>

    <%
        String success = request.getParameter("success");
        String error = request.getParameter("error");

        if (success != null) {
    %>
        <div style="color: green;">Operazione completata con successo: <%= success %>.</div>
    <%
        }
        if (error != null) {
    %>
        <div style="color: red;">Errore: <%= error %>.</div>
    <%
        }
    %>

    <form action="rimborso/gestisciRichiesteRimborsoControl" method="post">
        <table border="1">
            <thead>
                <tr>
                    <th>Codice Prodotto</th>
                    <th>Motivo</th>
                    <th>IBAN</th>
                    <th>Stato</th>
                    <th>Nuovo Stato</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<RefoundRequest> richieste = (List<RefoundRequest>) request.getAttribute("richieste");
                    if (richieste != null) {
                        for (RefoundRequest richiesta : richieste) {
                %>
                            <tr>
                                <td><%= richiesta.getProductCode() %></td>
                                <td><%= richiesta.getReason() %></td>
                                <td><%= richiesta.getIban() %></td>
                                <td><%= richiesta.getStato() %></td>
                                <td>
                                    <select name="newState_<%= richiesta.getProductCode() %>">
                                        <option value="IN_LAVORAZIONE" <%= richiesta.getStato().equals(StatoRimborso.IN_LAVORAZIONE) ? "selected" : "" %>>In lavorazione</option>
                                        <option value="ACCETTATO" <%= richiesta.getStato().equals(StatoRimborso.ACCETTATO) ? "selected" : "" %>>Accettato</option>
                                        <option value="RIFIUTATO" <%= richiesta.getStato().equals(StatoRimborso.RIFIUTATO) ? "selected" : "" %>>Rifiutato</option>
                                    </select>
                                </td>
                            </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
        <button type="submit">Salva Modifiche</button>
    </form>
    <jsp:include page="../pagePieces/footer.jsp" />
</body>
</html>
