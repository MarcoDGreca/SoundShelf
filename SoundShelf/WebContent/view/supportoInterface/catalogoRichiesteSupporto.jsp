<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, supporto.*"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <h3>Richiesta: <%= richiesta.getName() %></h3>
        <p><strong>Email:</strong> <%= richiesta.getEmail() %></p>
        <p><strong>Descrizione:</strong> <%= richiesta.getDescription() %></p>
        <p><strong>Data Invio:</strong> <%= richiesta.getDataInvio() %></p>
        <p><strong>Orario Invio:</strong> <%= richiesta.getOrarioInvio() %></p>
        <p><strong>Stato:</strong> <%= richiesta.getStato() %></p>
        <p><strong>Informazioni Aggiuntive:</strong> <%= richiesta.getInformazioniAggiuntive() %></p>

        <form action="informazioniControl" method="post">
            <input type="hidden" name="name" value="<%= richiesta.getName() %>">
            <label for="informazioniAggiuntive">Nuove informazioni aggiuntive:</label>
            <textarea name="informazioniAggiuntive" required></textarea>
            <button type="submit">Invia informazioni aggiuntive</button>
        </form>

        <form action="gestisciRichiestaSupporto" method="post">
            <input type="hidden" name="name" value="<%= richiesta.getName() %>">
            <label for="nuovoStato">Nuovo Stato:</label>
            <select name="nuovoStato">
                <option value="PENDING">In Attesa</option>
                <option value="IN_PROGRESS">In Corso</option>
                <option value="COMPLETED">Completato</option>
                <option value="REJECTED">Rifiutato</option>
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
