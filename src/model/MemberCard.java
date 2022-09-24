package model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class MemberCard {
	private final Optional<Integer> idSocio;
    private final String codiceFiscale;
    private final Date dataAssociazione;
    
	public MemberCard(Optional<Integer> idSocio, String codiceFiscale, Date dataAssociazione) {
		this.idSocio = idSocio;
		this.codiceFiscale = codiceFiscale;
		this.dataAssociazione = dataAssociazione;
	}

	public Integer getIdSocio() {
		if (this.idSocio.isPresent())
			return this.idSocio.get();
		return -1;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public Date getDataAssociazione() {
		return this.dataAssociazione;
	}
    
	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idSocio).append(") ")
			.append(codiceFiscale).append(" ")
			.append(dataAssociazione).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof MemberCard)
                && ((MemberCard) other).getIdSocio().equals(this.getIdSocio())
                && ((MemberCard) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((MemberCard) other).getDataAssociazione().equals(this.getDataAssociazione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idSocio, this.codiceFiscale, this.dataAssociazione);
    }
}
