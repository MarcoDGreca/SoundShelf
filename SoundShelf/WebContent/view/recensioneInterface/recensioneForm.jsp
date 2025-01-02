<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, recensione.*, prodotti.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Aggiungi Recensione</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
    <script>
        function confirmSubmission() {
            return confirm("Sei sicuro di voler inviare questa recensione?");
        }
    </script>
</head>
<body>

    <jsp:include page="../pagePieces/header.jsp" />

    <div id="main" class="clear">
    <% 
        Product purchasedProduct = (Product) request.getAttribute("purchasedProduct");
        if (purchasedProduct != null) {
    %>
        <h2>Aggiungi una Recensione</h2>
        <form action="${pageContext.request.contextPath}/addReview" method="post" onsubmit="return confirmSubmission();">
            <fieldset>
                <legend>Recensione Prodotto</legend>
                <div class="form-group">
                    <label for="productName">Prodotto:</label>
                    <span id="productName"><%= purchasedProduct.getName() %></span>
                </div>
                <div class="form-group">
                    <label for="rating">Voto:</label>
                    <select name="rating" id="rating" required>
                        <option value="">-- Seleziona --</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="comment">Commento:</label>
                    <textarea name="comment" id="comment" rows="4" required></textarea>
                </div>
                <input type="hidden" name="productId" value="<%= purchasedProduct.getProductCode() %>" />
                <div class="form-group">
                    <button type="submit">Invia Recensione</button>
                </div>
            </fieldset>
        </form>
    <% 
        } else {
    %>
        <h3>Non hai acquistato ancora nessun prodotto per lasciare una recensione.</h3>
    <% 
        } 
    %>
    </div>

    <jsp:include page="../pagePieces/footer.jsp" />

</body>
</html>
