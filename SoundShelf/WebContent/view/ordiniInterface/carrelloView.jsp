<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, prodotti.*, ordini.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Carrello</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include page="../pagePieces/header.jsp" />

    <div id="main" class="clear">
    <% 
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if(cart != null && !cart.getItems().isEmpty()) { 
    %>

    <section class="cart-section">
        <h2>Il tuo Carrello</h2>
        <table class="cart-table">
            <tr>
                <th>Descrizione</th>
                <th>Quantità</th>
                <th>Prezzo totale</th>
                <th>Rimuovi</th>
            </tr>
            <% 
                for(CartItem item : cart.getItems()) {
                    Product product = item.getProduct();
            %>
            <tr>
                <td><%= product.getName() %></td>
                <td> 
                    <form action="cart" method="get">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="productId" value="<%= product.getProductCode() %>">
                        <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1">
                        <button type="submit">Aggiorna</button>
                    </form>
                </td>
                <td>&euro;<%= String.format("%.2f", item.getTotalPrice()) %></td>
                <td>
                    <form action="cart" method="get">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="productId" value="<%= product.getProductCode() %>">
                        <button type="submit" class="remove-button">Rimuovi</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
        
        <div class="cart-total">
            Totale provvisorio: &euro;<%= String.format("%.2f", cart.getTotalPrice()) %>
        </div>

        <div class="cart-checkout">
            <a <% if(request.getSession().getAttribute("user") != null) { %>
                    href="checkout.jsp" <% } else { %> href="login.jsp" <% } %> >
                <button>Acquista</button>
            </a>
        </div>

        <div class="cart-clear">
            <form action="cart" method="get">
                <input type="hidden" name="action" value="clear">
                <button type="submit" class="clear-button">Svuota Carrello</button>
            </form>
        </div>
    </section>

    <% } else { %> 
        <h2>Il tuo carrello è vuoto</h2>
    <% } %>
    <br><br>

    </div>

    <jsp:include page="../pagePieces/footer.jsp" />

</body>
</html>
