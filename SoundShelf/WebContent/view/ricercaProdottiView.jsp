<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ricerca Prodotti</title>
    <link rel="stylesheet" href="styles.css"> 
</head>
<body>

<div class="container">
    <h1>Ricerca Prodotti</h1>
    <form class="search-form" id="searchForm" onsubmit="searchProducts(event)">
        <input type="text" id="productName" placeholder="Nome prodotto" />
        <input type="text" id="productArtist" placeholder="Artista" />
        <input type="text" id="productGenre" placeholder="Genere" />
        <button type="submit">Cerca</button>
    </form>

    <div id="errorMessage" style="color: red; display: none;"></div>
    <div id="results" class="results"></div>
</div>

<script src="scripts/searchProducts.js"></script> 

</body>
</html>
