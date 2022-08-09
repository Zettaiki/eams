package model;

import java.util.Objects;
import java.util.Optional;

public class Category {
	private final String nome;
	private final Optional<String> descrizione;

	public Category(String nome, Optional<String> descrizione) {
		this.nome = nome;
		this.descrizione = descrizione;
	}

	public Category(String nome) {
		this(nome, Optional.empty());
	}

	public String getNome() {
		return this.nome;
	}

	public Optional<String> getDescrizione() {
		return this.descrizione;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("(").append(nome).append(") ")
				.append(descrizione).toString();
	}

	@Override
	public boolean equals(final Object other) {
		return (other instanceof Category) && ((Category) other).getNome().equals(this.getNome())
				&& ((Category) other).getDescrizione().equals(this.getDescrizione());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.nome, this.descrizione);
	}
}
