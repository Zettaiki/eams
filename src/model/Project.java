package model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class Project {
	private final Optional<Integer> idProgetto;
    private final String obbiettivo;
    private final Date dataInizio;
    private final Integer durataMesi;
    private final Optional<String> descrizione;
    private final Date dataFine;
    
	public Project(Optional<Integer> idProgetto, String obbiettivo, Date dataInizio, Integer durataMesi, Optional<String> descrizione, Date dataFine) {
		this.idProgetto = idProgetto;
		this.obbiettivo = obbiettivo;
		this.dataInizio = dataInizio;
		this.durataMesi = durataMesi;
		this.descrizione = descrizione;
		this.dataFine = dataFine;
	}

	public Integer getIdProgetto() {
		if (this.idProgetto.isPresent())
			return this.idProgetto.get();
		return -1;
	}

	public String getObbiettivo() {
		return this.obbiettivo;
	}

	public Date getDataInizio() {
		return this.dataInizio;
	}
	
	public Integer getDurataMesi() {
		return this.durataMesi;
	}

	public Optional<String> getDescrizione() {
		return this.descrizione;
	}
	
	public Date getDataFine() {
		return this.dataFine;
	}
    
	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idProgetto).append(") ")
			.append(obbiettivo).append(" ")
			.append(dataInizio).append(" ")
			.append(durataMesi).append(" ")
			.append(descrizione).append(" ")
			.append(dataFine).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Project)
                && ((Project) other).getIdProgetto().equals(this.getIdProgetto())
                && ((Project) other).getObbiettivo().equals(this.getObbiettivo())
                && ((Project) other).getDataInizio().equals(this.getDataInizio())
                && ((Project) other).getDurataMesi().equals(this.getDurataMesi())
                && ((Project) other).getDescrizione().equals(this.getDescrizione())
                && ((Project) other).getDataInizio().equals(this.getDataFine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProgetto, this.obbiettivo, this.dataInizio, this.durataMesi, this.descrizione, this.dataFine);
    }
}
