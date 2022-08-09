package model;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Sale {
	private final String codiceFiscaleCliente;
    private final Integer idProdotto;
    private final Date data;
    private final Integer quantit�;
    private final String idEvento;
    private final Time oraInizioServizio;
    
	public Sale(String codiceFiscaleCliente, Integer idProdotto, Date data, Integer quantit�,
			String idEvento, Time oraInizioServizio) {
		this.codiceFiscaleCliente = codiceFiscaleCliente;
		this.idProdotto = idProdotto;
		this.data = data;
		this.quantit� = quantit�;
		this.idEvento = idEvento;
		this.oraInizioServizio = oraInizioServizio;
	}
	
	public String getCodiceFiscaleCliente() {
		return codiceFiscaleCliente;
	}

	public Integer getIdProdotto() {
		return idProdotto;
	}

	public Date getData() {
		return data;
	}
	
	public Integer getQuantit�() {
		return quantit�;
	}
	
	public String getIdEvento() {
		return idEvento;
	}
	
	public Time getOraInizioServizio() {
		return oraInizioServizio;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscaleCliente).append(") ")
			.append(idProdotto).append(" ")
			.append(data).append(" ")
			.append(quantit�).append(" ")
			.append(idEvento).append(" ")
			.append(oraInizioServizio).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Sale)
        		&& ((Sale) other).getCodiceFiscaleCliente().equals(this.getCodiceFiscaleCliente())
                && ((Sale) other).getIdProdotto().equals(this.getIdProdotto())                
                && ((Sale) other).getData().equals(this.getData())
                && ((Sale) other).getQuantit�().equals(this.getQuantit�())
                && ((Sale) other).getIdEvento().equals(this.getIdEvento())
                && ((Sale) other).getOraInizioServizio().equals(this.getOraInizioServizio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscaleCliente, this.idProdotto, this.data, this.quantit�,
        		this.idEvento, this.oraInizioServizio);
    }
}
