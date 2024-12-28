<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, rimborsi.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Richiesta Rimborso</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include page="../pagePieces/header.jsp" />

    <div id="main" class="clear">
        <h2>Le Tue Richieste di Rimborso</h2>

        <%
            List<RefoundRequest> refoundRequests = (List<RefoundRequest>) request.getAttribute("refoundRequests");
            if (refoundRequests != null && !refoundRequests.isEmpty()) {
        %>
            <table class="refound-table">
                <thead>
                    <tr>
                        <th>Codice Ordine</th>
                        <th>Codice Prodotto</th>
                        <th>Motivo</th>
                        <th>IBAN</th>
                        <th>Stato</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (RefoundRequest refoundRequest : refoundRequests) {
                    %>
                    <tr>
                        <td><%= refoundRequest.getIdOrdine() %></td>
                        <td><%= refoundRequest.getIdProdotto() %></td>
                        <td><%= refoundRequest.getMotivo() %></td>
                        <td><%= refoundRequest.getIban() %></td>
                        <td><%= refoundRequest.getStato().getStato() %></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        <%
            } else {
        %>
            <p>Non hai ancora inviato richieste di rimborso.</p>
        <% 
            }
        %>

        <h3>Nuova Richiesta di Rimborso</h3>
        <form action="${pageContext.request.contextPath}/rimborso/richiestaRimborsoControl" method="post">
            <label for="orderCode">Codice Ordine:</label>
            <input type="number" id="orderCode" name="orderCode" required><br><br>

            <label for="productCode">Codice Prodotto:</label>
            <input type="number" id="productCode" name="productCode" required><br><br>

            <label for="reason">Motivo:</label>
            <textarea id="reason" name="reason" required></textarea><br><br>

            <label for="iban">IBAN:</label>
            <input type="text" id="iban" name="iban" required><br><br>

            <button type="submit">Invia Richiesta</button>
        </form>

        <br>
        <% if (request.getAttribute("message") != null) { %>
            <div class="success-message"><%= request.getAttribute("message") %></div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message"><%= request.getAttribute("error") %></div>
        <% } %>
    </div>

    <jsp:include page="../pagePieces/footer.jsp" />

</body>
</html>
