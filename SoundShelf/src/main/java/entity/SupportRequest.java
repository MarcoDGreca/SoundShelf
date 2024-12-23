package entity;

import java.sql.Date;

public class SupportRequest {

    private String name;
    private String email;
    private String description;
    private Date dataInvio;
    private String orarioInvio;
    private StatoSupporto stato;

    public SupportRequest(String name, String email, String description, Date dataInvio, String orarioInvio, StatoSupporto stato) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.orarioInvio = orarioInvio;
        this.dataInvio = dataInvio;
        this.stato = stato;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatoSupporto getStato() {
        return stato;
    }

    public void setStato(StatoSupporto stato) {
        this.stato = stato;
    }

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getOrarioInvio() {
		return orarioInvio;
	}

	public void setOrarioInvio(String orarioInvio) {
		this.orarioInvio = orarioInvio;
	}
}