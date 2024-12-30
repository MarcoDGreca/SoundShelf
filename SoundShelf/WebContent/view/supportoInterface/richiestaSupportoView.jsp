<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, supporto.*"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
    <title>Le tue Richieste di Supporto</title>
</head>
<body>
	<jsp:include page="../pagePieces/header.jsp" />
    <h1>Le tue Richieste di Supporto</h1>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
            out.println("<p>" + message + "</p>");
        }

        List<SupportRequest> richieste = (List<SupportRequest>) request.getAttribute("richieste");
        if (richieste != null && !richieste.isEmpty()) {
    %>
    <table border="1">
        <thead>
            <tr>
                <th>Descrizione</th>
                <th>Data Invio</th>
                <th>Stato</th>
                <th>Informazioni Aggiuntive</th>
                <th>Risposta Utente</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (SupportRequest richiesta : richieste) {
            %>
            <tr>
                <td><%= richiesta.getDescription() %></td>
                <td><%= richiesta.getDataInvio() %></td>
                <td><%= richiesta.getStato() %></td>
                <td><%= richiesta.getInformazioniAggiuntive() != null ? richiesta.getInformazioniAggiuntive() : "Nessuna" %></td>
                <td>
                    <% 
                    if (richiesta.getInformazioniAggiuntive() != null && richiesta.getInformazioniAggiuntive().length() > 0) {
                    %>
                    <form action="${pageContext.request.contextPath}/informazioniControl" method="post">
                        <input type="hidden" name="idRichiesta" value="<%= richiesta.getId() %>">
                        <label for="risposta">La tua risposta:</label>
                        <textarea name="risposta" required></textarea>
                        <button type="submit">Invia Risposta</button>
                    </form>
                    <% 
                    }
                    %>
                </td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
    <%
        } else {
            out.println("<p>Non hai inviato richieste di supporto.</p>");
        }
    %>

    <form action="${pageContext.request.contextPath}/view/supportoInterface/helpOnlineForm.jsp" method="get">
        <button type="submit">Invia una Nuova Richiesta di Supporto</button>
    </form>
    <jsp:include page="../pagePieces/footer.jsp" />
</body>
</html>
