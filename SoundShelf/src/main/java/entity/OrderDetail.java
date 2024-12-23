package entity;

public class OrderDetail {
    private int codiceOrdine;
    private int codiceBiglietto;
    private int quantita;

    public OrderDetail() {}

    public OrderDetail(int codiceOrdine, int codiceBiglietto, int quantita) {
        this.codiceOrdine = codiceOrdine;
        this.codiceBiglietto = codiceBiglietto;
        this.quantita = quantita;
    }

    public int getCodiceOrdine() {
        return codiceOrdine;
    }

    public void setCodiceOrdine(int codiceOrdine) {
        this.codiceOrdine = codiceOrdine;
    }

    public int getCodiceBiglietto() {
        return codiceBiglietto;
    }

    public void setCodiceBiglietto(int codiceBiglietto) {
        this.codiceBiglietto = codiceBiglietto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    
}
