<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="ordini.*, prodotti.*"%>
<%@ page import="java.util.List"%>
<%
    List<Order> orders = (List<Order>) request.getAttribute("ordini");
    ElementoOrdineDAO orderDetailDAO = new ElementoOrdineDAO();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Le tue richieste di rimborso</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <script>
        function confirmRefund() {
            return confirm("Sei sicuro di voler richiedere un rimborso per questo prodotto?");
        }
    </script>
</head>
<body>
    <div>
        <jsp:include page="../pagePieces/header.jsp" />
        <%
        if (orders != null && !orders.isEmpty()) {
        %>
        <section class="orders-section">
            <h2>I Tuoi Ordini</h2>
            <div class="main-content">
                <%
                for (Order order : orders) {
                %>
                <div class="order">
                    <h3>Ordine <%= order.getNumeroOrdine() %></h3>
                    <table class="order-table">
                        <tr>
                            <th>Codice Ordine</th>
                            <td><%= order.getNumeroOrdine() %></td>
                        </tr>
                        <tr>
                            <th>Data</th>
                            <td><%= order.getDataOrdine() %></td>
                        </tr>
                        <tr>
                            <th>Totale</th>
                            <td>&euro;<%= order.getPrezzoTotale() %></td>
                        </tr>
                        <tr>
                            <th>Stato</th>
                            <td><%= order.getStato().getStato() %></td>
                        </tr>
                    </table>
                    <h4>Dettagli Ordine</h4>
                    <table class="order-table">
                        <tr>
                            <th>Prodotto</th>
                            <th>Quantità</th>
                            <th>Prezzo</th>
                            <th>Azioni</th>
                        </tr>
                        <%
                        List<ElementoOrdine> orderDetails = orderDetailDAO.getOrderDetailsByOrderId(order.getNumeroOrdine());
                        for (ElementoOrdine detail : orderDetails) {
                            Product product = (Product) request.getAttribute("prodotto_" + detail.getIdProdotto());
                        %>
                        <tr>
                            <td><%= product != null ? product.getName() : "Prodotto non disponibile" %></td>
                            <td><%= detail.getQuantita() %></td>
                            <td>&euro;<%= product != null ? product.getSalePrice() * detail.getQuantita() : 0 %></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/inviaRichiestaRimborsoControl" method="get" onsubmit="return confirmRefund();">
                                    <input type="hidden" name="detailCode" value="<%= detail.getId() %>">
                                    <button type="submit" class="refund-button">Richiedi Rimborso</button>
                                </form>
                            </td>
                        </tr>
                        <%
                        }
                        %>
                    </table>
                    <%
                    if (!"COMPLETATO".equals(order.getStato().getStato())) {
                    %>
                    <form action="${pageContext.request.contextPath}/OrdineRicevutoControl" method="post" onsubmit="return confirm('Sei sicuro di aver ricevuto questo ordine?');">
                        <input type="hidden" name="ordineId" value="<%= order.getNumeroOrdine() %>">
                        <input type="hidden" name="confermaRicezione" value="si">
                        <button type="submit" class="confirm-receipt-button">Conferma Ricezione</button>
                    </form>
                    <%
                    }
                    %>
                </div>
                <%
                }
                %>
            </div>
        </section>
        <%
        } else {
        %>
        <p>Non ci sono ordini disponibili.</p>
        <%
        }
        %>
        <jsp:include page="../pagePieces/footer.jsp" />
    </div>
</body>
</html>
