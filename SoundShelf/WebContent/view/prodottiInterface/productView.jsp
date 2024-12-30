<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="prodotti.Product, recensione.Review"%>
<%@ page import="java.util.List"%>
<%
Product product = (Product) request.getAttribute("product");
List<Review> reviews = (List<Review>) request.getAttribute("reviews");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SoundShelf - <%=product.getName()%></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
	<div>
		<jsp:include page="../pagePieces/header.jsp" />

		<section class="product-view">
			<h1><%=product.getName()%></h1>
			<div class="product-image">
				<img
					src="${pageContext.request.contextPath}/img/<%= product.getImage() != null ? product.getImage() : "default.jpg" %>"
					alt="<%= product.getName() %>">
			</div>
			<div class="product-details">
				<p>
					<strong>Artisti:</strong>
					<%
					for (prodotti.Artist artist : product.getArtists()) {
					%>
					<%=artist.getFirstName()%><%=product.getArtists().indexOf(artist) < product.getArtists().size() - 1 ? ", " : ""%>
					<%
					}
					%>
				</p>
				<p>
					<strong>Data di Rilascio:</strong>
					<%=product.getReleaseDate()%></p>
				<p>
					<strong>Generi:</strong>
					<%
					for (prodotti.Genre genre : product.getGenres()) {
					%>
					<%=genre.getName()%><%=product.getGenres().indexOf(genre) < product.getGenres().size() - 1 ? ", " : ""%>
					<%
					}
					%>
				</p>
				<p>
					<strong>Prezzo Scontato:</strong> €<%=product.getSalePrice()%></p>
				<p>
					<strong>Prezzo Originale:</strong> €<%=product.getOriginalPrice()%></p>
				<p>
					<strong>Disponibilità:</strong>
					<%=product.getAvailability()%></p>

				<form action="${pageContext.request.contextPath}/carrelloControl"
					method="post">
					<input type="hidden" name="action" value="add"> <input
						type="hidden" name="productId"
						value="<%=product.getProductCode()%>">
					<button type="submit" class="button">Aggiungi al Carrello</button>
				</form>
			</div>
		</section>

		<section class="reviews-section">
			<h2>Recensioni</h2>
			<div class="reviews">
				<%
				if (reviews != null && !reviews.isEmpty()) {
				%>
				<%
				for (Review review : reviews) {
				%>
				<div class="review">
					<p>
						<strong>Email:</strong>
						<%=review.getEmailCliente()%></p>
					<p>
						<strong>Voto:</strong>
						<%=review.getVoto()%>/10
					</p>
					<p><%=review.getDescrizione()%></p>
					<p>
						<small><%=review.getDataRecensione()%></small>
					</p>
				</div>
				<%
				}
				%>
				<%
				} else {
				%>
				<p>Non ci sono recensioni per questo prodotto.</p>
				<%
				}
				%>
			</div>
		</section>

		<jsp:include page="../pagePieces/footer.jsp" />
	</div>
</body>
</html>
