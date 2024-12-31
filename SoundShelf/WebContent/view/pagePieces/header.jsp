<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="utente.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    session = request.getSession(false);
    boolean isAuthenticated = session != null && session.getAttribute("user") != null;
    UtenteRegistrato utente = (UtenteRegistrato) session.getAttribute("user");
%>
<header>
    <div class="nav-container">
        <a href="${pageContext.request.contextPath}/home" class="logo">SoundShelf</a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/carrelloControl">Carrello</a></li>
            <li><a href="${pageContext.request.contextPath}/view/prodottiInterface/ricercaProdottiView.jsp">Cerca</a></li>
            <li><a href="${pageContext.request.contextPath}/richiestaSupportoControl">Supporto</a></li>
            <li><a href="${pageContext.request.contextPath}/richiestaRimborsoControl">Rimborso</a></li>
            <li><a href="${pageContext.request.contextPath}/listaOrdiniUtente">Ordini</a></li>

            <% if (utente != null && utente.getRuolo().equals(Ruolo.GESTORESITO)) { %>
                <li class="admin-section">
                    <a href="#" class="admin-toggle">Amministrazione</a>
                    <ul class="admin-dropdown">
                        <li><a href="${pageContext.request.contextPath}/gestisciRichiesteRimborsoControl">Gestisci Catalogo Richieste Rimborsi</a></li>
                        <li><a href="${pageContext.request.contextPath}/gestisciCatalogoOrdiniControl">Gestisci Catalogo Ordini</a></li>
                        <li><a href="${pageContext.request.contextPath}/gestisciCatalogoUtentiControl">Gestisci Catalogo Utenti</a></li>
                        <li><a href="${pageContext.request.contextPath}/gestisciRichiestaSupportoControl">Gestisci Catalogo Richieste Supporto</a></li>
                        <li><a href="${pageContext.request.contextPath}/gestisciCatalogoProdottiControl">Gestisci Catalogo Prodotti</a></li>
                        <li><a href="${pageContext.request.contextPath}/gestisciRecensioniControl">Gestisci Catalogo Recensioni</a></li>
                    </ul>
                </li>
            <% } %>
        </ul>
        <div class="auth-buttons">
            <% if (isAuthenticated) { %>
                <div class="user-menu">
                    <a href="${pageContext.request.contextPath}/profileControl" class="button">Profilo</a>
                    <a href="${pageContext.request.contextPath}/logout" class="button">LogOut</a>
                </div>
            <% } else { %>
                <div class="user-menu">
                    <a href="${pageContext.request.contextPath}/view/utenteInterface/loginForm.jsp" class="button">Login</a>
                    <a href="${pageContext.request.contextPath}/view/utenteInterface/registerForm.jsp" class="button">Registrati</a>
                </div>
            <% } %>
        </div>
    </div>
</header>
