<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, entity.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Aggiungi Recensione</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include page="header.jsp" />

    <div id="main" class="clear">
    <% 
        List<Product> purchasedProducts = (List<Product>) request.getAttribute("purchasedProducts");
        if (purchasedProducts != null && !purchasedProducts.isEmpty()) {
    %>
        <h2>Aggiungi una Recensione</h2>
        <form action="addReview" method="post">
            <label for="productId">Seleziona un prodotto:</label>
            <select name="productId" required>
                <option value="">-- Seleziona --</option>
                <% 
                    for (Product product : purchasedProducts) { 
                %>
                    <option value="<%= product.getProductCode() %>"><%= product.getName() %></option>
                <% 
                    } 
                %>
            </select>

            <label for="rating">Voto:</label>
            <select name="rating" required>
                <option value="">-- Seleziona --</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>

            <label for="comment">Commento:</label>
            <textarea name="comment" rows="4" required></textarea>

            <button type="submit">Invia Recensione</button>
        </form>
    <% 
        } else {
    %>
        <h3>Non hai acquistato ancora nessun prodotto per lasciare una recensione.</h3>
    <% 
        } 
    %>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>
