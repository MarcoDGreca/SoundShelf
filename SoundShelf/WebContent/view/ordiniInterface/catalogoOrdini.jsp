<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, ordini.*, control.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Catalogo Ordini</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include page="../pagePieces/header.jsp" />

    <div id="main" class="clear">
        <h2>Catalogo Ordini</h2>

        <%
            List<Order> ordini = (List<Order>) request.getAttribute("ordini");
            Map<Integer, List<OrderDetail>> ordineDetailsMap = (Map<Integer, List<OrderDetail>>) request.getAttribute("ordineDetailsMap");
            String messaggio = (String) request.getAttribute("messaggio"); // Messaggio di conferma
            if (messaggio != null) {
        %>
            <div class="messaggio">
                <%= messaggio %>
            </div>
        <%
            }

            if (ordini != null && !ordini.isEmpty()) {
        %>

        <div class="order-list">
            <% 
                for (Order ordine : ordini) { 
                    List<OrderDetail> dettagliOrdine = ordineDetailsMap.get(ordine.getNumeroOrdine());
            %>
                <div class="order-card">
                    <h3>Ordine Numero: <%= ordine.getNumeroOrdine() %></h3>
                    <p><strong>Data Ordine:</strong> <%= ordine.getDataOrdine() %></p>
                    <p><strong>Cliente:</strong> <%= ordine.getEmailCliente() %></p>
                    <p><strong>Stato Ordine:</strong> <%= ordine.getStato() %></p>
                    <p><strong>Indirizzo di Spedizione:</strong> <%= ordine.getIndirizzoSpedizione() %></p>
                    <p><strong>Totale Ordine:</strong> <%= ordine.getPrezzoTotale() %> €</p>
                    <p><strong>Data Consegna:</strong> <%= ordine.getDataConsegna() %></p>

                    <h4>Dettagli Ordine:</h4>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Prodotto</th>
                                <th>Quantità</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                for (OrderDetail dettaglio : dettagliOrdine) { 
                            %>
                                <tr>
                                    <td><%= dettaglio.getCodiceProdotto() %></td>
                                    <td><%= dettaglio.getQuantita() %></td>
                                </tr>
                            <% 
                                } 
                            %>
                        </tbody>
                    </table>

                    <form action="${pageContext.request.contextPath}/ordini/aggiornaStatoOrdineControl" method="post" style="display:inline;">
                        <input type="hidden" name="numeroOrdine" value="<%= ordine.getNumeroOrdine() %>" />
                        <label for="nuovoStato">Cambia Stato:</label>
                        <select name="nuovoStato">
                            <% for (StatoOrdine stato : StatoOrdine.values()) { %>
                                <option value="<%= stato.name() %>" <%= stato.name().equals(ordine.getStato()) ? "selected" : "" %>><%= stato.getStato() %></option>
                            <% } %>
                        </select>
                        <button type="submit" class="btn-cambia-stato">Aggiorna Stato</button>
                    </form>

                    <form action="${pageContext.request.contextPath}/ordini/PagamentoNonRicevutoControl" method="post" style="display:inline;">
                        <input type="hidden" name="ordineId" value="<%= ordine.getNumeroOrdine() %>" />
                        <button type="submit" class="btn-annulla-pagamento">Annulla per pagamento non ricevuto</button>
                    </form>
                </div>
            <% 
                } 
            %>
        </div>

        <% 
            } else {
        %>
        <p>Nessun ordine trovato.</p>
        <% 
            }
        %>
    </div>

    <jsp:include page="../pagePieces/footer.jsp" />

</body>
</html>
