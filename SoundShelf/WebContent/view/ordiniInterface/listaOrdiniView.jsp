<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, ordini.*, prodotti.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Storico Ordini</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include page="../pagePieces/header.jsp" />

    <div id="main" class="clear">
        <h2>Il Tuo Storico Ordini</h2>

        <% 
            List<Order> orders = (List<Order>) request.getAttribute("orders");
            if (orders != null && !orders.isEmpty()) {
        %>
            <table class="order-table">
                <thead>
                    <tr>
                        <th>Numero Ordine</th>
                        <th>Data Ordine</th>
                        <th>Data Consegna</th>
                        <th>Indirizzo Spedizione</th>
                        <th>Prezzo Totale</th>
                        <th>Stato Ordine</th>
                        <th>Dettagli Ordine</th>
                        <th>Conferma Ricezione</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        for (Order order : orders) { 
                            List<OrderDetail> orderDetails = (List<OrderDetail>) request.getAttribute("orderDetail_" + order.getNumeroOrdine());
                    %>
                    <tr>
                        <td><%= order.getNumeroOrdine() %></td>
                        <td><%= order.getDataOrdine() %></td>
                        <td><%= order.getDataConsegna() != null ? order.getDataConsegna() : "In attesa" %></td>
                        <td><%= order.getIndirizzoSpedizione() %></td>
                        <td>€ <%= order.getPrezzoTotale() %></td>
                        <td><%= order.getStato() != null ? order.getStato().toString() : "In elaborazione" %></td>
                        <td>
                            <table class="order-detail-table">
                                <thead>
                                    <tr>
                                        <th>Prodotto</th>
                                        <th>Quantità</th>
                                        <th>Prezzo Unitario</th>
                                        <th>Totale</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% 
                                        for (OrderDetail orderDetail : orderDetails) {
                                            Product product = (Product) request.getAttribute("product_" + orderDetail.getCodiceProdotto());
                                    %>
                                    <tr>
                                        <td><%= product != null ? product.getName() : "N/A" %></td>
                                        <td><%= orderDetail.getQuantita() %></td>
                                        <td>€ <%= product != null ? product.getSalePrice() : "N/A" %></td>
                                        <td>€ <%= orderDetail.getQuantita() * (product != null ? product.getSalePrice() : 0) %></td>
                                    </tr>
                                    <% 
                                        }
                                    %>
                                </tbody>
                            </table>
                        </td>
                        <td>
                            <% if (order.getStato() != StatoOrdine.COMPLETATO) { %>
                                <form action="ordini/OrdineRicevutoControl" method="post">
                                    <input type="hidden" name="ordineId" value="<%= order.getNumeroOrdine() %>" />
                                    <input type="hidden" name="confermaRicezione" value="si" />
                                    <button type="submit">Conferma Ricezione</button>
                                </form>
                            <% } else { %>
                                <span>Ricevuto</span>
                            <% } %>
                        </td>
                    </tr>
                    <% 
                        }
                    %>
                </tbody>
            </table>
        <% 
            } else {
        %>
            <h3>Non hai effettuato ordini finora.</h3>
        <% 
            }
        %>
    </div>

    <jsp:include page="../pagePieces/footer.jsp" />

</body>
</html>
