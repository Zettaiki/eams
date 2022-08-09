package model;

import java.sql.Timestamp;
import java.util.Objects;

public class Partecipazione {
	private final String codiceFiscaleVolontario;
    private final Timestamp oraInizioServizio;
    private final String idEvento;
    
	public Partecipazione(String codiceFiscaleVolontario, Timestamp oraInizioServizio, String idEvento) {
		this.codiceFiscaleVolontario = codiceFiscaleVolontario;
		this.oraInizioServizio = oraInizioServizio;
		this.idEvento = idEvento;
	}

	public String getCodiceFiscaleVolontario() {
		return this.codiceFiscaleVolontario;
	}

	public Timestamp getOraInizioServizio() {
		return this.oraInizioServizio;
	}

	public String getIdEvento() {
		return this.idEvento;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscaleVolontario).append(") ")
			.append(oraInizioServizio).append(" ")
			.append(idEvento).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Partecipazione)
        		&& ((Partecipazione) other).getCodiceFiscaleVolontario().equals(this.getCodiceFiscaleVolontario())
                && ((Partecipazione) other).getOraInizioServizio().equals(this.getOraInizioServizio())
                && ((Partecipazione) other).getIdEvento().equals(this.getIdEvento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscaleVolontario, this.oraInizioServizio, this.idEvento);
    }
}
