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
        <div style="color: green;">Operazione <%= success %> completata con successo.</div>
    <%
        }
        if (error != null) {
    %>
        <div style="color: red;">Errore: <%= error %></div>
    <%
        }
    %>

    <table border="1">
        <thead>
            <tr>
                <th>Codice Prodotto</th>
                <th>Motivo</th>
                <th>IBAN</th>
                <th>Stato</th>
                <th>Azioni</th>
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
                                <form action="manageRefund" method="post">
                                    <input type="hidden" name="productCode" value="<%= richiesta.getProductCode() %>">
                                    <button type="submit" name="action" value="accept">Accetta</button>
                                    <button type="submit" name="action" value="reject">Rifiuta</button>
                                </form>
                            </td>
                        </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
    <jsp:include page="../pagePieces/footer.jsp" />
</body>
</html>
