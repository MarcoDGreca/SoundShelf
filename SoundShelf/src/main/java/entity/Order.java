package entity;

import java.sql.Date;

public class Order {
    private int codiceOrdine;
    private String emailCliente;
    private double prezzoTotale;
    private Date dataAcquisto;
    private Stato stato;

    public Order() {}

    public Order(int codiceOrdine, String emailCliente, double prezzoTotale, Date dataAcquisto, Stato stato) {
        this.codiceOrdine = codiceOrdine;
        this.emailCliente = emailCliente;
        this.prezzoTotale = prezzoTotale;
        this.dataAcquisto = dataAcquisto;
        this.stato = stato;
    }

    public int getCodiceOrdine() {
        return codiceOrdine;
    }

    public void setCodiceOrdine(int codiceOrdine) {
        this.codiceOrdine = codiceOrdine;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(double prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

    public Date getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(Date dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }
}
