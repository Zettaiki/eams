package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Dipendente {
	private final String codiceFiscale;
	private final String sedeCittą;
    private final Date dataAssunzione;
    private final BigDecimal salario;
    
	public Dipendente(String codiceFiscale, String sedeCittą, Date dataAssunzione, BigDecimal salario) {
		this.codiceFiscale = codiceFiscale;
		this.sedeCittą = sedeCittą;
		this.dataAssunzione = dataAssunzione;
		this.salario = salario;
	}
	
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public String getSedeCittą() {
		return this.sedeCittą;
	}

	public Date getDataAssunzione() {
		return this.dataAssunzione;
	}
	
	public BigDecimal getSalario() {
		return this.salario;
	}
    
	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscale).append(") ")
			.append(sedeCittą).append(" ")
			.append(dataAssunzione).append(" ")
			.append(salario).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Dipendente)
                && ((Dipendente) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Dipendente) other).getSedeCittą().equals(this.getSedeCittą())
                && ((Dipendente) other).getDataAssunzione().equals(this.getDataAssunzione())
                && ((Dipendente) other).getSalario().equals(this.getSalario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscale, this.sedeCittą, this.dataAssunzione, this.salario);
    }
}
