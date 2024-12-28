<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    session = request.getSession(false);
    boolean isAuthenticated = session != null && session.getAttribute("user") != null;
    String userType = isAuthenticated ? (String) session.getAttribute("userType") : null;
%>
<header>
    <nav>
        <div class="nav-container">
            <a href="home" class="logo">SoundShelf</a>
            <ul class="nav-links">
                <li><a href="control/home">Home</a></li>
                <li><a href="ordini/carrelloControl">Carrello</a></li>
                <li><a href="/prodottiInterface/ricercaProdottiView.jsp">Cerca</a></li>
                <li><a href="supporto/richiestaSupportoControl">Supporto</a></li>
                <li><a href="rimborso/richiestaRimborsoControl">Rimborso</a></li>
                <li><a href="ordini/listaOrdiniUtente">Ordini</a></li>
                <% if (isAuthenticated && "admin".equals(userType)) { %>
                    <li class="admin-section">
                        <span>Amministrazione</span>
                        <ul>
                            <li><a href="rimborso/gestisciRichiesteRimborsoControl">Gestisci Catalogo Richieste Rimborsi</a></li>
                            <li><a href="ordini/gestisciCatalogoOrdiniControl">Gestisci Catalogo Ordini</a></li>
                            <li><a href="utenti/gestisciCatalogoUtentiControl">Gestisci Catalogo Utenti</a></li>
                            <li><a href="supporto/gestisciRichiestaSupportoControl">Gestisci Catalogo Richieste Supporto</a></li>
                            <li><a href="prodotti/gestisciCatalogoProdottiControl">Gestisci Catalogo Prodotti</a></li>
                            <li><a href="recensione/gestisciCatalogoRecensioniControl">Gestisci Catalogo Recensioni</a></li>
                        </ul>
                    </li>
                <% } %>
            </ul>
            <div class="auth-buttons">
                <% if (isAuthenticated) { %>
                    <a href="utente/profileControl" class="button">Profilo</a>
                    <a href="utente/logout" class="button">LogOut</a>
                <% } else { %>
                    <a href="utenteInterface/loginForm.jsp" class="button">Login</a>
                    <a href="utenteInterface/registerForm.jsp" class="button">Registrati</a>
                <% } %>
            </div>
        </div>
    </nav>
</header>

