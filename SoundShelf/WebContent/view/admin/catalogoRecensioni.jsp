<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, entity.*, control.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Catalogo Recensioni</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include page="../header.jsp" />

    <div id="main" class="clear">
        <h2>Catalogo Recensioni</h2>

        <%
            List<Review> recensioni = (List<Review>) request.getAttribute("recensioni");
            if (recensioni != null && !recensioni.isEmpty()) {
        %>

        <div class="review-list">
            <% for (Review recensione : recensioni) { %>
                <div class="review-card">
                    <h3>Prodotto: <%= recensione.getCodiceProdotto() %> - Votazione: <%= recensione.getVotazione() %>/10</h3>
                    <p><strong>Email Cliente:</strong> <%= recensione.getEmailCliente() %></p>
                    <p><strong>Data Recensione:</strong> <%= recensione.getDataRecensione() %></p>
                    <p><strong>Testo:</strong> <%= recensione.getTesto() %></p>

                    <form action="${pageContext.request.contextPath}/deleteReview" method="post" style="display:inline;">
                        <input type="hidden" name="reviewId" value="<%= recensione.getCodiceRecensione() %>" />
                        <button type="submit" class="btn-delete" onclick="return confirm('Sei sicuro di voler eliminare questa recensione?')">Elimina Recensione</button>
                    </form>
                </div>
            <% } %>
        </div>

        <% 
            } else {
        %>
        <p>Nessuna recensione disponibile.</p>
        <% 
            }
        %>
    </div>

    <jsp:include page="../footer.jsp" />

</body>
</html>
