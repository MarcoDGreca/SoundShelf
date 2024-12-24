package entity;

public class RefoundRequest {

    private int productCode;
    private String reason;
    private String iban;
    private StatoRimborso stato;

    public RefoundRequest(int productCode, String reason, String iban, StatoRimborso stato) {
        this.productCode = productCode;
        this.reason = reason;
        this.iban = iban;
        this.stato = stato;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
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
}
