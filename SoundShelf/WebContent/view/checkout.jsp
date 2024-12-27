<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="entity.Utente" %>
<%@ page import="entity.Cart" %>
<%@ page import="java.util.List" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    Utente user = (Utente) session.getAttribute("user");
    String savedAddress = (String) request.getAttribute("savedAddress");
    double totalPrice = (cart != null) ? cart.getTotalPrice() : 0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SoundShelf - Checkout</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
    <div>
        <jsp:include page="header.jsp" />
        
        <section class="checkout-section">
            <h1>Checkout</h1>
            <form action="checkout" method="post">
                <div>
                    <h3>Indirizzo di Spedizione</h3>
                    <% if (savedAddress != null) { %>
                        <p><strong>Indirizzo salvato:</strong> <%= savedAddress %></p>
                        <p>Se vuoi utilizzare un altro indirizzo, inseriscilo qui sotto.</p>
                    <% } else { %>
                        <p>Inserisci il tuo indirizzo di spedizione.</p>
                    <% } %>

                    <textarea name="shippingAddress" rows="4" cols="50"><%= (savedAddress != null ? "" : "") %></textarea>
                </div>

                <div>
                    <h3>Riepilogo Ordine</h3>
                    <p><strong>Totale:</strong> €<%= totalPrice %></p>
                    <button type="submit" class="button">Procedi con l'Acquisto</button>
                </div>
            </form>
        </section>

        <jsp:include page="footer.jsp" />
    </div>
</body>
</html>
