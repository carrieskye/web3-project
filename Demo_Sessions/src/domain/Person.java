package domain;

public class Person {
	private String name;
	private String email;
	private String password;

	public Person() {

	}

	public Person(String name, String email, String password) {
		setName(name);
		setEmail(email);
		setPassword(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new DomainException("Name may not be empty");
		}
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new DomainException("Email may not be empty");
		}
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password == null || password.isEmpty()) {
			throw new DomainException("Password may not be empty");
		}
		this.password = password;
	}

}
