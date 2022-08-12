package model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class Project {
	private final Integer idProgetto;
    private final String obbiettivo;
    private final Date dataInizio;
    private final Integer durataMesi;
    private final Optional<String> descrizione;
    
	public Project(String obbiettivo, Date dataInizio, Integer durataMesi, Optional<String> descrizione) {
		this.idProgetto = 0;
		this.obbiettivo = obbiettivo;
		this.dataInizio = dataInizio;
		this.durataMesi = durataMesi;
		this.descrizione = descrizione;
	}

	public Integer getIdProgetto() {
		return this.idProgetto;
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
    
	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idProgetto).append(") ")
			.append(obbiettivo).append(" ")
			.append(dataInizio).append(" ")
			.append(durataMesi).append(" ")
			.append(descrizione).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Project)
                && ((Project) other).getIdProgetto().equals(this.getIdProgetto())
                && ((Project) other).getObbiettivo().equals(this.getObbiettivo())
                && ((Project) other).getDataInizio().equals(this.getDataInizio())
                && ((Project) other).getDurataMesi().equals(this.getDurataMesi())
                && ((Project) other).getDescrizione().equals(this.getDescrizione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProgetto, this.obbiettivo, this.dataInizio, this.durataMesi, this.descrizione);
    }
}
