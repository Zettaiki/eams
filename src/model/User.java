package model;

import java.util.Objects;

public class User {
	private final String codiceFiscaleDipendente;
	private final String username;
	private final String password;
    
	public User(String codiceFiscaleDipendente, String username, String password) {
		this.codiceFiscaleDipendente = codiceFiscaleDipendente;
		this.username = username;
		this.password = password;
	}
	
	public String getCodiceFiscaleDipendente() {
		return this.codiceFiscaleDipendente;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
    
	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscaleDipendente).append(") ")
			.append(username).append(" ")
			.append(password).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof User)
                && ((User) other).getCodiceFiscaleDipendente().equals(this.getCodiceFiscaleDipendente())
                && ((User) other).getUsername().equals(this.getUsername())
                && ((User) other).getPassword().equals(this.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscaleDipendente, this.username, this.password);
    }
}
