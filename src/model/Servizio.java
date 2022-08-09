package model;

import java.sql.Time;
import java.util.Objects;
import java.util.Optional;

public class Servizio {
	private final String idEvento;
    private final String tipo;
    private final Time oraInizio;
    private final Time oraFine;
    private final Optional<String> idProgetto;
    
	public Servizio(String idEvento, String tipo, Time oraInizio, Time oraFine, Optional<String> idProgetto) {
		this.idEvento = idEvento;
		this.tipo = tipo;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.idProgetto = Objects.requireNonNull(idProgetto);
	}
    
	public Servizio(String idEvento, String tipo, Time oraInizio, Time oraFine) {
		this(idEvento, tipo, oraInizio, oraFine, Optional.empty());
	}

	public String getIdEvento() {
		return this.idEvento;
	}
	
	public String getTipo() {
		return tipo;
	}

	public Time getOraInizio() {
		return oraInizio;
	}

	public Time getOraFine() {
		return oraFine;
	}

	public Optional<String> getIdProgetto() {
		return idProgetto;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idEvento).append(") ")
			.append(tipo).append(" ")
			.append(oraInizio).append(" ")
			.append(oraFine).append(" ")
			.append(idProgetto).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Servizio)
        		&& ((Servizio) other).getIdEvento().equals(this.getIdEvento())
                && ((Servizio) other).getTipo().equals(this.getTipo())
                && ((Servizio) other).getOraInizio().equals(this.getOraInizio())
                && ((Servizio) other).getOraFine().equals(this.getOraFine())
                && ((Servizio) other).getIdProgetto().equals(this.getIdProgetto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idEvento, this.tipo, this.oraInizio, this.oraFine, this.idProgetto);
    }
}
