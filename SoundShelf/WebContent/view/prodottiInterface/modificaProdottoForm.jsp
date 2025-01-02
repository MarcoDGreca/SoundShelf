<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, prodotti.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Modifica Prodotto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/scripts/validation.js"></script>
    <script>
        function confirmEdit() {
            return confirm("Sei sicuro di voler modificare questo prodotto?");
        }
    </script>
</head>
<body>

    <jsp:include page="../pagePieces/header.jsp" />

    <jsp:include page="../pagePieces/header.jsp" />

    <div id="main" class="clear">
        <h2>Modifica Prodotto</h2>

        <% 
            Product product = (Product) request.getAttribute("product");
            if (product != null) { 
        %>
        <form action="<%= request.getContextPath() %>/ModificaProdottoControl" method="post" onsubmit="return confirmEdit();">
            <input type="hidden" name="productCode" value="<%= product.getProductCode() %>" />

            <label for="name">Nome Prodotto:</label>
            <input type="text" id="name" name="name" value="<%= product.getName() %>" required /><br/>

            <label for="description">Descrizione:</label>
            <textarea id="description" name="description" required><%= product.getDescription() %></textarea><br/>

            <label for="salePrice">Prezzo di Vendita (€):</label>
            <input type="number" step="0.01" id="salePrice" name="salePrice" value="<%= product.getSalePrice() %>" required /><br/>

            <label for="originalPrice">Prezzo Originale (€):</label>
            <input type="number" step="0.01" id="originalPrice" name="originalPrice" value="<%= product.getOriginalPrice() %>" required /><br/>

            <label for="availability">Disponibilità:</label>
            <input type="number" id="availability" name="availability" value="<%= product.getAvailability() %>" required /><br/>

            <label for="releaseDate">Data di Rilascio:</label>
            <input type="date" id="releaseDate" name="releaseDate" value="<%= product.getReleaseDate() %>" required /><br/>

            <label for="image">Immagine URL:</label>
            <input type="text" id="image" name="image" value="<%= product.getImage() %>" required /><br/>
            
            <label for="image">Formato:</label>
            <input type="text" id="supportedDevice" name="supportedDevice" value="<%= product.getSupportedDevice() %>" required /><br/>
            
            <button type="submit">Salva Modifiche</button>
        </form>
        <% 
            } else { 
        %>
        <p>Errore: Prodotto non trovato.</p>
        <% 
            } 
        %>
    </div>

    <jsp:include page="../pagePieces/footer.jsp" />

</body>
</html>
