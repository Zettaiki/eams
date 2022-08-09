package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Employee {
	private final String codiceFiscale;
	private final String sedeCitt�;
    private final Date dataAssunzione;
    private final BigDecimal salario;
    
	public Employee(String codiceFiscale, String sedeCitt�, Date dataAssunzione, BigDecimal salario) {
		this.codiceFiscale = codiceFiscale;
		this.sedeCitt� = sedeCitt�;
		this.dataAssunzione = dataAssunzione;
		this.salario = salario;
	}
	
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public String getSedeCitt�() {
		return this.sedeCitt�;
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
			.append(sedeCitt�).append(" ")
			.append(dataAssunzione).append(" ")
			.append(salario).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Employee)
                && ((Employee) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Employee) other).getSedeCitt�().equals(this.getSedeCitt�())
                && ((Employee) other).getDataAssunzione().equals(this.getDataAssunzione())
                && ((Employee) other).getSalario().equals(this.getSalario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscale, this.sedeCitt�, this.dataAssunzione, this.salario);
    }
}
