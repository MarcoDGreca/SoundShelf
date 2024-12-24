package entity;


public enum StatoRimborso {
	IN_LAVORAZIONE("In lavorazione"),
    ACCETTATO("Accettato"),
    RIFIUTATO("Rifiutato");

    private final String stato;

    StatoRimborso(String stato) {
        this.stato = stato;
    }

    public String getStato() {
        return this.stato;
    }

    public static StatoRimborso fromString(String stato) {
        for (StatoRimborso s : StatoRimborso.values()) {
            if (s.getStato().equalsIgnoreCase(stato)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Stato non valido: " + stato);
    }
}


