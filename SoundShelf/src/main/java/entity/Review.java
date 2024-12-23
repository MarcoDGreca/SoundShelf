package entity;

import java.sql.Date;

public class Review {

    private int codiceRecensione;
    private int codiceEvento;
    private String emailCliente;
    private int votazione;
    private String testo;
    private Date dataRecensione;

    public Review() {
    }

    public Review(int codiceRecensione, int codiceEvento, String emailCliente, int votazione, String testo, Date dataRecensione) {
        this.codiceRecensione = codiceRecensione;
        this.codiceEvento = codiceEvento;
        this.emailCliente = emailCliente;
        this.votazione = votazione;
        this.testo = testo;
        this.dataRecensione = dataRecensione;
    }

    public int getCodiceRecensione() {
        return codiceRecensione;
    }

    public void setCodiceRecensione(int codiceRecensione) {
        this.codiceRecensione = codiceRecensione;
    }

    public int getCodiceEvento() {
        return codiceEvento;
    }

    public void setCodiceEvento(int codiceEvento) {
        this.codiceEvento = codiceEvento;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public int getVotazione() {
        return votazione;
    }

    public void setVotazione(int votazione) {
        if(votazione >= 0 && votazione <= 10)
            this.votazione = votazione;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Date getDataRecensione() {
        return dataRecensione;
    }

    public void setDataRecensione(Date dataRecensione) {
        this.dataRecensione = dataRecensione;
    }
}