<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Invia Richiesta Supporto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include page="../pagePieces/header.jsp" />

    <div id="main" class="clear">
        <form id="supportRequestForm" action="${pageContext.request.contextPath}/richiestaSupporto" method="post">
            <label for="name">Nome:</label>
            <input type="text" id="name" name="name" required><br><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br><br>

            <label for="description">Descrizione:</label>
            <textarea id="description" name="description" required></textarea><br><br>

            <button type="button" onclick="confirmSubmit()">Invia Richiesta</button>
        </form>
    </div>

    <jsp:include page="../pagePieces/footer.jsp" />

    <script type="text/javascript">
        function confirmSubmit() {
            var message = "Sei sicuro di voler inviare la tua richiesta di supporto?";
            if (confirm(message)) {
                document.getElementById("supportRequestForm").submit();
            } else {
                return false;
            }
        }
    </script>

</body>
</html>
