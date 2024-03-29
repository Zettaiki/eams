package model;

import java.sql.Time;
import java.util.Objects;
import java.util.Optional;

public class Service {
	private final String idEvento;
	private final String idServizio;
    private final Time oraInizio;
    private final Time oraFine;
    private final String tipo;
    private final Optional<Integer> idProgetto;
    
	public Service(String idServizio, String idEvento, Time oraInizio, Time oraFine, String tipo, Optional<Integer> idProgetto) {
		this.idEvento = idEvento;
		this.idServizio = idServizio;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.tipo = tipo;
		this.idProgetto = Objects.requireNonNull(idProgetto);
	}
    
	public Service(String idServizio, String idEvento, Time oraInizio, Time oraFine, String tipo) {
		this(idServizio, idEvento, oraInizio, oraFine, tipo, Optional.empty());
	}
	
	public String getIdServizio() {
		return this.idServizio;
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

	public Optional<Integer> getIdProgetto() {
		return idProgetto;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idEvento).append(" ")
			.append(idServizio).append(") ")
			.append(oraInizio).append(" ")
			.append(oraFine).append(" ")
			.append(tipo).append(" ")
			.append(idProgetto).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Service)
        		&& ((Service) other).getIdEvento().equals(this.getIdEvento())
        		&& ((Service) other).getIdServizio().equals(this.getIdServizio())
                && ((Service) other).getOraInizio().equals(this.getOraInizio())
                && ((Service) other).getOraFine().equals(this.getOraFine())
                && ((Service) other).getTipo().equals(this.getTipo())
                && ((Service) other).getIdProgetto().equals(this.getIdProgetto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idEvento, this.idServizio, this.oraInizio, this.oraFine, this.tipo, this.idProgetto);
    }
}
