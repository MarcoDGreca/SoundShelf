package ordini;

public enum StatoOrdine {
    IN_LAVORAZIONE("In lavorazione"),
    COMPLETATO("Completato"),
    ANNULLATO("Annullato");

    private final String stato;

    StatoOrdine(String stato) {
        this.stato = stato;
    }

    public String getStato() {
        return this.stato;
    }

    public static StatoOrdine fromString(String stato) {
        for (StatoOrdine s : StatoOrdine.values()) {
            if (s.getStato().equalsIgnoreCase(stato)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Stato non valido: " + stato);
    }
}
