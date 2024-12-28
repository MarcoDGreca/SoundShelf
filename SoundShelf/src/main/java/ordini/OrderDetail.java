package ordini;

public class OrderDetail {
    private int codiceOrdine;
    private int codiceProdotto;
    private int quantita;

    public OrderDetail() {}

    public OrderDetail(int codiceOrdine, int codiceProdotto, int quantita) {
        this.codiceOrdine = codiceOrdine;
        this.codiceProdotto = codiceProdotto;
        this.quantita = quantita;
    }

    public int getCodiceOrdine() {
        return codiceOrdine;
    }

    public void setCodiceOrdine(int codiceOrdine) {
        this.codiceOrdine = codiceOrdine;
    }

    public int getCodiceProdotto() {
        return codiceProdotto;
    }

    public void setCodiceProdotto(int codiceProdotto) {
        this.codiceProdotto = codiceProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    
}
