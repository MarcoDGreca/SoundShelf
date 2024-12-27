<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, entity.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Modifica Prodotto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
    <script>
        function confirmEdit() {
            return confirm("Sei sicuro di voler modificare questo prodotto?");
        }
    </script>
</head>
<body>

    <jsp:include page="../header.jsp" />

    <div id="main" class="clear">
        <h2>Modifica Prodotto</h2>

        <%
            Product product = (Product) request.getAttribute("product");
        %>

        <form action="${pageContext.request.contextPath}/ModificaProdottoControl" method="post" onsubmit="return confirmEdit();">
            <input type="hidden" name="productCode" value="<%= product.getProductCode() %>">
            
            <label for="name">Nome Prodotto:</label>
            <input type="text" id="name" name="name" value="<%= product.getName() %>" required><br>

            <label for="description">Descrizione:</label>
            <textarea id="description" name="description" required><%= product.getDescription() %></textarea><br>

            <label for="salePrice">Prezzo di Vendita (€):</label>
            <input type="number" step="0.01" id="salePrice" name="salePrice" value="<%= product.getSalePrice() %>" required><br>

            <label for="originalPrice">Prezzo Originale (€):</label>
            <input type="number" step="0.01" id="originalPrice" name="originalPrice" value="<%= product.getOriginalPrice() %>" required><br>

            <label for="availability">Disponibilità:</label>
            <select id="availability" name="availability" required>
                <option value="true" <%= product.isAvailability() ? "selected" : "" %>>Disponibile</option>
                <option value="false" <%= !product.isAvailability() ? "selected" : "" %>>Non disponibile</option>
            </select><br>

            <label for="releaseDate">Data di Rilascio:</label>
            <input type="date" id="releaseDate" name="releaseDate" value="<%= product.getReleaseDate() %>" required><br>

            <label for="image">Immagine URL:</label>
            <input type="text" id="image" name="image" value="<%= product.getImage() %>" required><br>

            <label for="artists">Artisti (separati da virgola):</label>
            <input type="text" id="artists" name="artists" value="<%= product.getArtists() != null ? String.join(", ", product.getArtists().stream().map(a -> a.getFirstName()).toArray(String[]::new)) : "" %>" required><br>

            <label for="genres">Generi (separati da virgola):</label>
            <input type="text" id="genres" name="genres" value="<%= product.getGenres() != null ? String.join(", ", product.getGenres().stream().map(g -> g.getName()).toArray(String[]::new)) : "" %>" required><br>

            <button type="submit">Salva Modifiche</button>
        </form>

    </div>

    <jsp:include page="../footer.jsp" />

</body>
</html>
