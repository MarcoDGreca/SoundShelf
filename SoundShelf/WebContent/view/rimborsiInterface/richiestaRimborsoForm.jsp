<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, ordini.*, rimborsi.*" %>
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
        <h2>Dettaglio Rimborso</h2>

        <% 
            ElementoOrdine product = (ElementoOrdine) request.getAttribute("product");
            if (product != null) {
        %>

            <form action="${pageContext.request.contextPath}/inviaRichiestaRimborsoControl" method="post" onsubmit="return confirmRefund();">
                <label for="productCode">Prodotto:</label>
                <input type="text" id="productCode" name="productCode" value="<%= product.getIdProdotto() %>" readonly /><br><br>

                <label for="productPrice">Prezzo Prodotto:</label>
                <input type="text" id="productPrice" name="productPrice" value="<%= product.getPrezzoUnitario() %>€" readonly /><br><br>

                <label for="reason">Motivo del rimborso:</label>
                <textarea id="reason" name="reason" required></textarea><br><br>

                <label for="iban">IBAN:</label>
                <input type="text" id="iban" name="iban" required><br><br>

                <button type="submit">Invia Richiesta</button>
            </form>
        <% 
            } else { 
        %>
            <p>Dettagli non trovati per il prodotto selezionato.</p>
        <% 
            }
        %>

        <br>
        <% if (request.getAttribute("message") != null) { %>
            <div class="success-message"><%= request.getAttribute("message") %></div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message"><%= request.getAttribute("error") %></div>
        <% } %>
    </div>
    
    <script>
        function confirmRefund() {
            return confirm("Sei sicuro di voler richiedere il rimborso per questo prodotto?");
        }
    </script>

    <jsp:include page="../pagePieces/footer.jsp" />

</body>
</html>
