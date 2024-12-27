<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="entity.Product, entity.Review" %>
<%@ page import="java.util.List" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    List<Review> reviews = (List<Review>) request.getAttribute("reviews");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SoundShelf - Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
    <div>
        <jsp:include page="header.jsp" />
        <section>
            <h1>Benvenuto su SoundShelf</h1>
            <p>Scopri i migliori vinili e dischi musicali selezionati per te!</p>
        </section>

        <section class="products-section">
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
                                    <% for (entity.Artist artist : product.getArtists()) { %>
                                        <%= artist.getFirstName() %><%= product.getArtists().indexOf(artist) < product.getArtists().size() - 1 ? ", " : "" %>
                                    <% } %>
                                </p>
                                <p><strong>Data di Rilascio:</strong> <%= product.getReleaseDate() %></p>
                                <p><strong>Generi:</strong>
                                    <% for (entity.Genre genre : product.getGenres()) { %>
                                        <%= genre.getName() %><%= product.getGenres().indexOf(genre) < product.getGenres().size() - 1 ? ", " : "" %>
                                    <% } %>
                                </p>
                                <p><strong>Prezzo Scontato:</strong> €<%= product.getSalePrice() %></p>
                                <p><strong>Prezzo Originale:</strong> €<%= product.getOriginalPrice() %></p>
                                <p><strong>Disponibilità:</strong> <%= product.isAvailability() ? "Disponibile" : "Non Disponibile" %></p>
                                <form action="cart" method="post">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="productCode" value="<%= product.getProductCode() %>">
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
        <jsp:include page="footer.jsp" />
    </div>
</body>
</html>
