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
	</div>>
    <jsp:include page="../pagePieces/footer.jsp" />

</body>
</html>
