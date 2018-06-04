package ui.controller;

public class CustomRedirectException extends RuntimeException{
	private static final long serialVersionUID = -5644480293980483354L;

	private String location;
	
	public CustomRedirectException(String location) {
		super("redirect");
		setLocation(location);
	}

	public String getLocation() {
		return location;
	}

	private void setLocation(String location) {
		this.location = location;
	}
	
}
