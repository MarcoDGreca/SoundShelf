<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Errore</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include page="../pagePieces/header.jsp" />

    <div id="main" class="clear">
        <h2>Errore</h2>
        <p><%= request.getAttribute("errorMessage") %></p>
        <a href="${pageContext.request.contextPath}/productList" class="btn-return">Torna alla lista dei prodotti</a>
    </div>

    <jsp:include page="../pagePieces/footer.jsp" />

</body>
</html>