package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Employee {
	private final String codiceFiscale;
	private final String sedeCittà;
    private final Date dataAssunzione;
    private final BigDecimal salario;
    
	public Employee(String codiceFiscale, String sedeCittà, Date dataAssunzione, BigDecimal salario) {
		this.codiceFiscale = codiceFiscale;
		this.sedeCittà = sedeCittà;
		this.dataAssunzione = dataAssunzione;
		this.salario = salario;
	}
	
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public String getSedeCittà() {
		return this.sedeCittà;
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
			.append(sedeCittà).append(" ")
			.append(dataAssunzione).append(" ")
			.append(salario).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Employee)
                && ((Employee) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Employee) other).getSedeCittà().equals(this.getSedeCittà())
                && ((Employee) other).getDataAssunzione().equals(this.getDataAssunzione())
                && ((Employee) other).getSalario().equals(this.getSalario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscale, this.sedeCittà, this.dataAssunzione, this.salario);
    }
}
