<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="utente.UtenteRegistrato" %>
<%@ page import="ordini.Cart" %>
<%@ page import="java.util.List" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    UtenteRegistrato user = (UtenteRegistrato) session.getAttribute("user");
    String savedAddress = (String) request.getAttribute("savedAddress");
    double totalPrice = (cart != null) ? cart.getTotalPrice() : 0;
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>SoundShelf - Checkout</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <script src="${pageContext.request.contextPath}/scripts/validation.js"></script>
</head>
<body>
    <div>
        <jsp:include page="../pagePieces/header.jsp" />
        
        <section class="checkout-container">
            <h2>Checkout</h2>
            <form action="${pageContext.request.contextPath}/acquistoControl" method="post" class="checkout-form">
                <div class="form-group">
                    <h3>Indirizzo di Spedizione</h3>
                    <% if (savedAddress != null) { %>
                        <p><strong>Indirizzo salvato:</strong> <%= savedAddress %></p>
                        <p>Se vuoi utilizzare un altro indirizzo, inseriscilo qui sotto.</p>
                    <% } else { %>
                        <p>Inserisci il tuo indirizzo di spedizione.</p>
                    <% } %>
                    <textarea name="shippingAddress" rows="4" cols="50"><%= (savedAddress != null ? savedAddress : "") %></textarea>
                </div>

                <div class="form-group">
                    <h3>Riepilogo Ordine</h3>
                    <p><strong>Totale:</strong> €<%= totalPrice %></p>
                    <div class="form-actions">
                        <button type="submit" class="btn-checkout">Procedi con l'Acquisto</button>
                    </div>
                </div>
            </form>

            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/carrelloControl">
                    <button type="button" class="btn-return">Annulla e Torna al Carrello</button>
                </a>
            </div>
        </section>

        <jsp:include page="../pagePieces/footer.jsp" />
    </div>
</body>
</html>
