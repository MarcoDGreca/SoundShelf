<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, ordini.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Aggiungi Nuovo Prodotto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
        // Funzione di conferma per l'aggiunta del prodotto
        function confirmAddProduct() {
            return confirm('Sei sicuro di voler aggiungere questo nuovo prodotto?');
        }
    </script>
</head>
<body>

    <jsp:include page="../pagePieces/header.jsp" />

    <div id="main" class="clear">
        <h2>Aggiungi Nuovo Prodotto</h2>

        <form action="${pageContext.request.contextPath}/InserisciNuovoProdottoControl" method="post" onsubmit="return confirmAddProduct()">
            <label for="productCode">Codice Prodotto:</label>
            <input type="number" id="productCode" name="productCode" required><br>

            <label for="name">Nome Prodotto:</label>
            <input type="text" id="name" name="name" required><br>

            <label for="description">Descrizione:</label>
            <textarea id="description" name="description" required></textarea><br>

            <label for="salePrice">Prezzo di Vendita (€):</label>
            <input type="number" step="0.01" id="salePrice" name="salePrice" required><br>

            <label for="originalPrice">Prezzo Originale (€):</label>
            <input type="number" step="0.01" id="originalPrice" name="originalPrice" required><br>

            <label for="availability">Disponibilità:</label>
            <select id="availability" name="availability" required>
                <option value="true">Disponibile</option>
                <option value="false">Non disponibile</option>
            </select><br>

            <label for="releaseDate">Data di Rilascio:</label>
            <input type="date" id="releaseDate" name="releaseDate" required><br>

            <label for="image">Immagine URL:</label>
            <input type="text" id="image" name="image" required><br>

            <label for="artists">Artisti (separati da virgola):</label>
            <input type="text" id="artists" name="artists" required><br>

            <label for="genres">Generi (separati da virgola):</label>
            <input type="text" id="genres" name="genres" required><br>

            <button type="submit">Aggiungi Prodotto</button>
        </form>

    </div>

    <jsp:include page="../pagePieces/footer.jsp" />

</body>
</html>
