package model;

import java.util.Date;
import java.util.Objects;

public class Sale {
	private final String codiceFiscaleCliente;
    private final Integer idProdotto;
    private final Date data;
    private final Integer quantità;
    private final String idEvento;
    private final String idServizio;
    
	public Sale(String codiceFiscaleCliente, Integer idProdotto, Date data, Integer quantità,
			String idEvento, String idServizio) {
		this.codiceFiscaleCliente = codiceFiscaleCliente;
		this.idProdotto = idProdotto;
		this.data = data;
		this.quantità = quantità;
		this.idEvento = idEvento;
		this.idServizio = idServizio;
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
	
	public Integer getQuantità() {
		return quantità;
	}
	
	public String getIdEvento() {
		return idEvento;
	}
	
	public String getIdServizio() {
		return idServizio;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscaleCliente).append(" ")
			.append(idProdotto).append(" ")
			.append(data).append(") ")
			.append(quantità).append(" ")
			.append(idEvento).append(" ")
			.append(idServizio).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Sale)
        		&& ((Sale) other).getCodiceFiscaleCliente().equals(this.getCodiceFiscaleCliente())
                && ((Sale) other).getIdProdotto().equals(this.getIdProdotto())                
                && ((Sale) other).getData().equals(this.getData())
                && ((Sale) other).getQuantità().equals(this.getQuantità())
                && ((Sale) other).getIdEvento().equals(this.getIdEvento())
                && ((Sale) other).getIdServizio().equals(this.getIdServizio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscaleCliente, this.idProdotto, this.data, this.quantità,
        		this.idEvento, this.idServizio);
    }
}
