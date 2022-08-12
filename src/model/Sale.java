package model;

import java.util.Objects;

public class Sale {
	private final String idProdotto;
	private final String idServizio;
	private final String codiceFiscaleCliente;
    private final Integer quantità;
    
	public Sale(String idProdotto, String idServizio, String codiceFiscaleCliente, Integer quantità) {
		this.idProdotto = idProdotto;
		this.idServizio = idServizio;
		this.codiceFiscaleCliente = codiceFiscaleCliente;
		this.quantità = quantità;
	}
	
	public String getIdProdotto() {
		return idProdotto;
	}
	
	public String getIdServizio() {
		return idServizio;
	}
	
	public String getCodiceFiscaleCliente() {
		return codiceFiscaleCliente;
	}
	
	public Integer getQuantità() {
		return quantità;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idProdotto).append(" ")
			.append(idServizio).append(" ")
			.append(codiceFiscaleCliente).append(") ")
			.append(quantità).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Sale)
                && ((Sale) other).getIdProdotto().equals(this.getIdProdotto())
                && ((Sale) other).getIdServizio().equals(this.getIdServizio())
                && ((Sale) other).getCodiceFiscaleCliente().equals(this.getCodiceFiscaleCliente())
                && ((Sale) other).getQuantità().equals(this.getQuantità());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProdotto, this.idServizio, this.codiceFiscaleCliente, this.quantità);
    }
}
