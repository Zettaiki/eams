package model;

import java.sql.Time;
import java.util.Objects;

public class Participation {
	private final String codiceFiscaleVolontario;
    private final Time oraInizioServizio;
    private final String idEvento;
    
	public Participation(String codiceFiscaleVolontario, Time oraInizioServizio, String idEvento) {
		this.codiceFiscaleVolontario = codiceFiscaleVolontario;
		this.oraInizioServizio = oraInizioServizio;
		this.idEvento = idEvento;
	}

	public String getCodiceFiscaleVolontario() {
		return this.codiceFiscaleVolontario;
	}

	public Time getOraInizioServizio() {
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
        return (other instanceof Participation)
        		&& ((Participation) other).getCodiceFiscaleVolontario().equals(this.getCodiceFiscaleVolontario())
                && ((Participation) other).getOraInizioServizio().equals(this.getOraInizioServizio())
                && ((Participation) other).getIdEvento().equals(this.getIdEvento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscaleVolontario, this.oraInizioServizio, this.idEvento);
    }
}
