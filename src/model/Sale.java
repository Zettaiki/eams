package model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class Sale {
	private final String idProdotto;
	private final String idServizio;
	private final String codiceFiscaleCliente;
	private final Integer quantità;
	private final Optional<BigDecimal> prezzoVendita;

	public Sale(String idProdotto, String idServizio, String codiceFiscaleCliente, Integer quantità, Optional<BigDecimal> prezzoVendita) {
		this.idProdotto = idProdotto;
		this.idServizio = idServizio;
		this.codiceFiscaleCliente = codiceFiscaleCliente;
		this.quantità = quantità;
		this.prezzoVendita = prezzoVendita;
	}

	public String getIdProdotto() {
		return this.idProdotto;
	}

	public String getIdServizio() {
		return this.idServizio;
	}

	public String getCodiceFiscaleCliente() {
		return this.codiceFiscaleCliente;
	}

	public Integer getQuantità() {
		return this.quantità;
	}

	public Optional<BigDecimal> getPrezzoVendita() {
		return this.prezzoVendita;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("(").append(idProdotto).append(" ")
				.append(idServizio).append(" ")
				.append(codiceFiscaleCliente).append(") ")
				.append(quantità)
				.append(prezzoVendita).toString();
	}

	@Override
	public boolean equals(final Object other) {
		return (other instanceof Sale)
				&& ((Sale) other).getIdProdotto().equals(this.getIdProdotto())
				&& ((Sale) other).getIdServizio().equals(this.getIdServizio())
				&& ((Sale) other).getCodiceFiscaleCliente().equals(this.getCodiceFiscaleCliente())
				&& ((Sale) other).getQuantità().equals(this.getQuantità())
				&& ((Sale) other).getPrezzoVendita().equals(this.getPrezzoVendita());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.idProdotto, this.idServizio, this.codiceFiscaleCliente, this.quantità, this.prezzoVendita);
	}
}
