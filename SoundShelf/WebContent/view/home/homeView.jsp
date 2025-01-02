<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="prodotti.Product" %>
<%@ page import="java.util.List" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SoundShelf - Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/SoundShelf/styles/style.css" type="text/css">
</head>
<body>
    <div>
        <jsp:include page="../pagePieces/header.jsp" />
        <section class="welcome-section">
            <h1>Benvenuto su SoundShelf</h1>
            <p>Scopri i migliori vinili e dischi musicali selezionati per te!</p>
            <div class="cta-buttons">
                <a href="#prodotti" class="button cta-button">Esplora Prodotti</a>
            </div>
        </section>

        <section id="prodotti" class="products-section">
            <h2>Prodotti Disponibili</h2>
            <div class="main-content grid">
                <% if (products != null && !products.isEmpty()) { %>
                    <% for (Product product : products) { %>
                        <div class="product grid-item">
                            <div class="product-image-container">
                                <img src="${pageContext.request.contextPath}/img/<%= product.getImage() != null ? product.getImage() : "default.jpg" %>" alt="<%= product.getName() %>">
                            </div>
                            <div class="product-details">
                                <h3><%= product.getName() %></h3>
                                <p><strong>Artisti:</strong>
                                    <% for (prodotti.Artist artist : product.getArtists()) { %>
                                        <%= artist.getFirstName() %><%= product.getArtists().indexOf(artist) < product.getArtists().size() - 1 ? ", " : "" %>
                                    <% } %>
                                </p>
                                <p><strong>Data di Rilascio:</strong> <%= product.getReleaseDate() %></p>
                                <p><strong>Generi:</strong>
                                    <% for (prodotti.Genre genre : product.getGenres()) { %>
                                        <%= genre.getName() %><%= product.getGenres().indexOf(genre) < product.getGenres().size() - 1 ? ", " : "" %>
                                    <% } %>
                                </p>
                                <p><strong>Prezzo Scontato:</strong>&euro;<%= product.getSalePrice() %></p>
                                <p><strong>Prezzo Originale:</strong>&euro;<%= product.getOriginalPrice() %></p>
                                <p><strong>Disponibilit&aacute;:</strong> <%= product.getAvailability() %></p>
                                <form action="${pageContext.request.contextPath}/prodottoControl" method="get">
                                    <input type="hidden" name="productId" value="<%= product.getProductCode() %>">
                                    <button type="submit" class="button">Dettagli prodotto</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/carrelloControl" method="post">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="productId" value="<%= product.getProductCode() %>">
                                    <button type="submit" class="button">Aggiungi al Carrello</button>
                                </form>
                            </div>
                        </div>
                    <% } %>
                <% } else { %>
                    <p>Non ci sono prodotti disponibili al momento.</p>
                <% } %>
            </div>
        </section>
        <jsp:include page="../pagePieces/footer.jsp" />
    </div>
</body>
</html>
