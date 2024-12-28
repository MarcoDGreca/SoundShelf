<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, prodotti.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Catalogo Prodotti</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include page="../pagePieces/header.jsp" />

    <div id="main" class="clear">
        <h2>Catalogo Prodotti</h2>

        <div class="product-actions">
            <a href="${pageContext.request.contextPath}/prodottiInterface/inserisciProdottoForm.jsp" class="btn-add">Aggiungi Nuovo Prodotto</a>
        </div>

        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products != null && !products.isEmpty()) {
        %>

        <div class="product-list">
            <% for (Product product : products) { %>
                <div class="product-card">
                    <img src="<%= product.getImage() %>" alt="<%= product.getName() %>" class="product-image">
                    <h3><%= product.getName() %></h3>
                    <p><strong>Prezzo:</strong> €<%= product.getSalePrice() %></p>
                    <p><strong>Disponibilità:</strong> <%= product.isAvailability() ? "Disponibile" : "Non disponibile" %></p>
                    <p><strong>Data di rilascio:</strong> <%= product.getReleaseDate() %></p>
                    <p><strong>Descrizione:</strong> <%= product.getDescription() %></p>

                    <% 
                        List<Artist> artists = product.getArtists();
                        if (artists != null && !artists.isEmpty()) { 
                    %>
                        <p><strong>Artisti:</strong>
                            <% for (Artist artist : artists) { %>
                                <%= artist.getFirstName() %><% if (artists.indexOf(artist) != artists.size() - 1) { %>, <% } %>
                            <% } %>
                        </p>
                    <% } %>

                    <% 
                        List<Genre> genres = product.getGenres();
                        if (genres != null && !genres.isEmpty()) { 
                    %>
                        <p><strong>Generi:</strong>
                            <% for (Genre genre : genres) { %>
                                <%= genre.getName() %><% if (genres.indexOf(genre) != genres.size() - 1) { %>, <% } %>
                            <% } %>
                        </p>
                    <% } %>

                    <a href="${pageContext.request.contextPath}/prodotti/prodottoControl?productCode=<%= product.getProductCode() %>" class="btn-details">Dettagli</a>
                    <a href="${pageContext.request.contextPath}/prodotti/ModificaProdottoControl?productCode=<%= product.getProductCode() %>" class="btn-edit">Modifica Prodotto</a>
                    <form action="${pageContext.request.contextPath}/prodotti/rimuoviProdottoControl" method="post" style="display:inline;">
                        <input type="hidden" name="productCode" value="<%= product.getProductCode() %>" />
                        <button type="submit" class="btn-delete" onclick="return confirm('Sei sicuro di voler rimuovere questo prodotto?')">Rimuovi Prodotto</button>
                    </form>
                </div>
            <% } %>
        </div>

        <% 
            } else {
        %>
        <p>Nessun prodotto disponibile.</p>
        <% 
            }
        %>
    </div>

    <jsp:include page="../pagePieces/footer.jsp" />

</body>
</html>
