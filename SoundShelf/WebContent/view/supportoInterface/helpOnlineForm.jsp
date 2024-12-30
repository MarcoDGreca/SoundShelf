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
        <form id="supportRequestForm" action="${pageContext.request.contextPath}/richiestaSupportoControl" method="post" onsubmit="return validateForm()">
            <label for="name">Nome:</label>
            <input type="text" id="name" name="name" required><br><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br><br>

            <label for="description">Descrizione:</label>
            <textarea id="description" name="description" required></textarea><br><br>

            <button type="submit">Invia Richiesta</button>
        </form>
    </div>

    <jsp:include page="../pagePieces/footer.jsp" />

    <script type="text/javascript">
        function validateForm() {
            var name = document.getElementById("name").value;
            var email = document.getElementById("email").value;
            var description = document.getElementById("description").value;
            var errorMessage = "";

            // Validazione nome
            if (name.trim() === "") {
                errorMessage += "Il campo nome è obbligatorio.\n";
            }

            // Validazione email
            var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
            if (!emailPattern.test(email)) {
                errorMessage += "L'email non è valida.\n";
            }

            // Validazione descrizione
            if (description.trim() === "") {
                errorMessage += "Il campo descrizione è obbligatorio.\n";
            }

            if (errorMessage) {
                alert("Errore:\n" + errorMessage);
                return false; // Impedisce l'invio del modulo
            }
            return true; // Se la validazione passa, invia il modulo
        }
    </script>

</body>
</html>