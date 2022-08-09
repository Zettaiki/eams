package model;

import java.sql.Time;
import java.util.Objects;
import java.util.Optional;

public class Servizio {
	private final String idEvento;
    private final Time oraInizio;
    private final Time oraFine;
    private final String tipo;
    private final Optional<String> idProgetto;
    
	public Servizio(String idEvento, Time oraInizio, Time oraFine, String tipo, Optional<String> idProgetto) {
		this.idEvento = idEvento;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.tipo = tipo;
		this.idProgetto = Objects.requireNonNull(idProgetto);
	}
    
	public Servizio(String idEvento, Time oraInizio, Time oraFine, String tipo) {
		this(idEvento, oraInizio, oraFine, tipo, Optional.empty());
	}

	public String getIdEvento() {
		return this.idEvento;
	}

	public Time getOraInizio() {
		return oraInizio;
	}

	public Time getOraFine() {
		return oraFine;
	}
	
	public String getTipo() {
		return tipo;
	}

	public Optional<String> getIdProgetto() {
		return idProgetto;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idEvento).append(") ")
			.append(oraInizio).append(" ")
			.append(oraFine).append(" ")
			.append(tipo).append(" ")
			.append(idProgetto).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Servizio)
        		&& ((Servizio) other).getIdEvento().equals(this.getIdEvento())
                && ((Servizio) other).getOraInizio().equals(this.getOraInizio())
                && ((Servizio) other).getOraFine().equals(this.getOraFine())
                && ((Servizio) other).getTipo().equals(this.getTipo())
                && ((Servizio) other).getIdProgetto().equals(this.getIdProgetto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idEvento, this.oraInizio, this.oraFine, this.tipo, this.idProgetto);
    }
}
