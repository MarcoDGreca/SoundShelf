package rimborsi;

public class RefoundRequest {

    private int id;
    private String reason;
    private String iban;
    private StatoRimborso stato;
    private int orderCode;
    private int productCode;

    public RefoundRequest(int id, String reason, String iban, StatoRimborso stato, int orderCode, int productCode) {
        this.id = id;
        this.reason = reason;
        this.iban = iban;
        this.stato = stato;
        this.orderCode = orderCode;
        this.productCode = productCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public StatoRimborso getStato() {
        return stato;
    }

    public void setStato(StatoRimborso stato) {
        this.stato = stato;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }
}
