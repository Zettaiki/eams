package model;

import java.util.Objects;

public class Organizzazione {
	private final String codiceFiscaleDipendente;
    private final String idEvento;
    
	public Organizzazione(String codiceFiscaleDipendente, String idEvento) {
		this.codiceFiscaleDipendente = codiceFiscaleDipendente;
		this.idEvento = idEvento;
	}

	public String getCodiceFiscaleDipendente() {
		return this.codiceFiscaleDipendente;
	}

	public String getIdEvento() {
		return this.idEvento;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscaleDipendente).append(") ")
			.append(idEvento).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Partecipazione)
        		&& ((Partecipazione) other).getCodiceFiscaleVolontario().equals(this.getCodiceFiscaleDipendente())
                && ((Partecipazione) other).getIdEvento().equals(this.getIdEvento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscaleDipendente, this.idEvento);
    }
}
