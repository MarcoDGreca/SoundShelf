<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, ordini.*, rimborsi.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Richiesta Rimborso</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include page="../pagePieces/header.jsp" />

    <div id="main" class="clear">
        <h2 class="page-title">Dettaglio Rimborso</h2>

        <% 
            ElementoOrdine product = (ElementoOrdine) request.getAttribute("product");
            int quantita = (int) request.getAttribute("quantita");
            double totale = quantita * product.getPrezzoUnitario();
            if (product != null) {
        %>

            <form action="${pageContext.request.contextPath}/inviaRichiestaRimborsoControl" method="post" onsubmit="return confirmRefund();" class="refund-form">
                <label for="productCode" class="form-label">Codice dei prodotti:</label>
                <input type="text" id="productCode" name="productCode" value="<%= product.getIdProdotto() %>" readonly class="form-input" /><br><br>

                <label for="productPrice" class="form-label">Prezzo totale dell'ordine:</label>
                <input type="text" id="productPrice" name="productPrice" value="<%= totale %>€" readonly class="form-input" /><br><br>

                <label for="reason" class="form-label">Inserisci il motivo del rimborso:</label>
                <textarea id="reason" name="reason" required class="form-textarea"></textarea><br><br>

                <label for="iban" class="form-label">Inserisci il tuo IBAN:</label>
                <input type="text" id="iban" name="iban" required class="form-input"><br><br>

                <button type="submit" class="refund-button">Invia Richiesta</button>
            </form>
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
