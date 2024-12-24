package entity;

public enum StatoSupporto {
	IN_LAVORAZIONE("In lavorazione"),
    CHIUSA("Chiuso");

    private final String stato;

    StatoSupporto(String stato) {
        this.stato = stato;
    }

    public String getStato() {
        return this.stato;
    }

    public static StatoSupporto fromString(String stato) {
        for (StatoSupporto s : StatoSupporto.values()) {
            if (s.getStato().equalsIgnoreCase(stato)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Stato non valido: " + stato);
    }
}
