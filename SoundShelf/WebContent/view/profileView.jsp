<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, entity.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Profilo Utente</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include page="header.jsp" />

    <div id="main" class="clear">
        <h2>Il Tuo Profilo</h2>

        <%
            // Recupera i dati dell'utente dalla sessione
            Utente user = (Utente) request.getAttribute("user");
            if (user != null) {
        %>
        <table class="profile-table">
            <tr>
                <th>Nome:</th>
                <td><%= user.getNome() %></td>
            </tr>
            <tr>
                <th>Cognome:</th>
                <td><%= user.getCognome() %></td>
            </tr>
            <tr>
                <th>Indirizzo:</th>
                <td><%= user.getIndirizzo() %></td>
            </tr>
            <tr>
                <th>Telefono:</th>
                <td><%= user.getTelefono() %></td>
            </tr>
        </table>

        <h3>Modifica Dati Personali</h3>
        <form action="${pageContext.request.contextPath}/profile" method="post">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" value="<%= user.getNome() %>" required><br><br>

            <label for="cognome">Cognome:</label>
            <input type="text" id="cognome" name="cognome" value="<%= user.getCognome() %>" required><br><br>

            <label for="indirizzo">Indirizzo:</label>
            <input type="text" id="indirizzo" name="indirizzo" value="<%= user.getIndirizzo() %>" required><br><br>

            <label for="telefono">Telefono:</label>
            <input type="text" id="telefono" name="telefono" value="<%= user.getTelefono() %>" required><br><br>

            <button type="submit">Salva Modifiche</button>
        </form>
        <% 
            } else {
        %>
        <p>Per visualizzare o modificare il tuo profilo, devi essere loggato.</p>
        <% 
            }
        %>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>
