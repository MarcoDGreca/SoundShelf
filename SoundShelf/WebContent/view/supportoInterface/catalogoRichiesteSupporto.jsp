<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, supporto.*"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
    <title>Gestione Richieste di Supporto</title>
</head>
<body>
	<jsp:include page="../pagePieces/header.jsp" />
    <h1>Gestione Richieste di Supporto</h1>

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

    <%
        List<SupportRequest> richieste = (List<SupportRequest>) request.getAttribute("richieste");
        if (richieste != null) {
            for (SupportRequest richiesta : richieste) {
    %>
        <h3>Richiesta: <%= richiesta.getId() %></h3>
        <p><strong>Descrizione:</strong> <%= richiesta.getDescription() %></p>
        <p><strong>Data Invio:</strong> <%= richiesta.getDataInvio() %></p>
        <p><strong>Orario Invio:</strong> <%= richiesta.getOrarioInvio() %></p>
        <p><strong>Stato:</strong> <%= richiesta.getStato() %></p>
        <p><strong>Informazioni Aggiuntive:</strong> <%= richiesta.getInformazioniAggiuntive() %></p>

        <form action="${pageContext.request.contextPath}/supporto/informazioniControl" method="post">
            <input type="hidden" name="idRichiesta" value="<%= richiesta.getId() %>">
            <label for="informazioniAggiuntive">Richiedi informazioni aggiuntive:</label>
            <textarea name="informazioniAggiuntive" required></textarea>
            <button type="submit">Invia informazioni aggiuntive</button>
        </form>

        <form action="${pageContext.request.contextPath}/supporto/gestisciRichiestaSupporto" method="post">
            <input type="hidden" name="idRichiesta" value="<%= richiesta.getId() %>">
            <label for="nuovoStato">Nuovo Stato:</label>
            <select name="nuovoStato">
                <option value="IN_LAVORAZIONE">In Lavorazione</option>
                <option value="CHIUSA">Chiusa</option>
            </select>
            <button type="submit" name="action" value="aggiornaStato">Aggiorna Stato</button>
        </form>
    <%
            }
        }
    %>
    <jsp:include page="../pagePieces/footer.jsp" />
</body>
</html>
