package model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class Product {
	private final String idProdotto;
    private final String categoria;
    private final String nome;
    private final BigDecimal prezzo;
    private final Integer quantit‡Immagazzinata;
    private final String provenienza;
    private final Optional<String> descrizione;
    
    public Product(final String idProdotto, final String categoria, final String nome, final BigDecimal prezzo, 
    		final Integer quantit‡Immagazzinata, final String provenienza, final Optional<String> descrizione) {
    	this.idProdotto = idProdotto;
    	this.categoria = categoria;
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantit‡Immagazzinata = quantit‡Immagazzinata;
        this.provenienza = provenienza;
        this.descrizione = Objects.requireNonNull(descrizione);
    }
    
    public Product(final String idProdotto, final String categoria, final String nome, final BigDecimal prezzo, 
    		final Integer quantit‡Immagazzinata, final String provenienza) {
        this(idProdotto, categoria, nome, prezzo, quantit‡Immagazzinata, provenienza, Optional.empty());
    }   
	
	public String getIdProdotto() {
		return idProdotto;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getPrezzo() {
		return prezzo;
	}

	public Integer getQuantit‡Immagazzinata() {
		return quantit‡Immagazzinata;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public Optional<String> getDescrizione() {
		return descrizione;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("(").append(this.idProdotto).append(") ")
				.append(this.categoria).append(" ")
				.append(this.nome).append(" ")
				.append(this.prezzo).append(" ")
				.append(this.quantit‡Immagazzinata).append(" ")
				.append(this.provenienza).append(" ")
				.append(this.descrizione).toString();
	}

	@Override
    public boolean equals(final Object other) {
        return (other instanceof Product)
                && ((Product) other).getIdProdotto().equals(this.getIdProdotto())
                && ((Product) other).getCategoria().equals(this.getCategoria())
                && ((Product) other).getNome().equals(this.getNome())
                && ((Product) other).getPrezzo().equals(this.getPrezzo())
                && ((Product) other).getQuantit‡Immagazzinata().equals(this.getQuantit‡Immagazzinata())
                && ((Product) other).getProvenienza().equals(this.getProvenienza())
                && ((Product) other).getDescrizione().equals(this.getDescrizione());
    }

	@Override
    public int hashCode() {
        return Objects.hash(this.idProdotto, this.categoria, this.nome, this.prezzo, this.quantit‡Immagazzinata,
        		this.provenienza, this.descrizione);
    }
}
