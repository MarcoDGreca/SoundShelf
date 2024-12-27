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
            <a href="home.jsp" class="logo">SoundShelf</a>
            <ul class="nav-links">
                <li><a href="home.jsp">Home</a></li>
                <li><a href="cart.jsp">Carrello</a></li>
                <li><a href="support.jsp">Supporto</a></li>
                <li><a href="refund.jsp">Rimborso</a></li>
                <li><a href="orders.jsp">Ordini</a></li>
                <% if (isAuthenticated && "admin".equals(userType)) { %>
                    <li class="admin-section">
                        <span>Amministrazione</span>
                        <ul>
                            <li><a href="manageRefunds.jsp">Gestisci Rimborsi</a></li>
                            <li><a href="manageOrders.jsp">Gestisci Ordini</a></li>
                            <li><a href="manageUsers.jsp">Gestisci Utenti</a></li>
                            <li><a href="manageSupport.jsp">Gestisci Supporto</a></li>
                            <li><a href="manageProducts.jsp">Gestisci Prodotti</a></li>
                        </ul>
                    </li>
                <% } %>
            </ul>
            <div class="auth-buttons">
                <% if (isAuthenticated) { %>
                    <a href="profile.jsp" class="button">Profilo</a>
                    <a href="logout.jsp" class="button">LogOut</a>
                <% } else { %>
                    <a href="login.jsp" class="button">Login</a>
                    <a href="register.jsp" class="button">Registrati</a>
                <% } %>
            </div>
        </div>
    </nav>
</header>

