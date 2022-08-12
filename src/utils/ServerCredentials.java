package utils;

public enum ServerCredentials {
	USERNAME("root"), PASSWORD(""), DBNAME("eams");
	
	final String string;
	
	ServerCredentials(String string) {
		this.string = string;
	}
	
	@Override
	public String toString() {
        return this.string;
	}
}
