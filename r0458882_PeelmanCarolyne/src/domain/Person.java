package domain;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
	private String userid;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String salt;

	public Person(String userid, String email, String password, String firstName, String lastName, String salt) {
		setUserid(userid);
		setEmail(email);
		setFirstName(firstName);
		setLastName(lastName);
		setSalt(salt);
		setPassword(password);
	}

	public Person(String userid, String email, String password, String firstName, String lastName) {
		setUserid(userid);
		setEmail(email);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
	}

	public Person() {
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		if (userid.isEmpty()) {
			throw new IllegalArgumentException("No userid given");
		}
		this.userid = userid;
	}

	public void setEmail(String email) {
		if (email.isEmpty()) {
			throw new IllegalArgumentException("No email given");
		}
		String USERID_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern p = Pattern.compile(USERID_PATTERN);
		Matcher m = p.matcher(email);
		if (!m.matches()) {
			throw new IllegalArgumentException("Email not valid");
		}
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean isCorrectPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (password.isEmpty()) {
			throw new IllegalArgumentException("No password given");
		}
		return hashPassword(password).equals(getPassword());
	}

	public void setPassword(String password) {
		if (password.isEmpty()) {
			throw new IllegalArgumentException("No password given");
		}
		this.password = password;
	}

	public void setPasswordHashed(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (password.isEmpty()) {
			throw new IllegalArgumentException("No password given");
		}
		this.password = hashPassword(password);
	}

	private String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest hash = MessageDigest.getInstance("SHA-512");
		hash.reset();
		SecureRandom random = new SecureRandom();

		if (getSalt() == null) {
			byte[] seed = random.generateSeed(20);
			String salt = new BigInteger(1, seed).toString(16);
			setSalt(salt);
			hash.update(salt.getBytes("UTF-8"));
		}

		else {
			hash.update(getSalt().getBytes("UTF-8"));
		}

		hash.update(password.getBytes("UTF-8"));
		String hashedPassword = new BigInteger(1, hash.digest()).toString(16);
		return hashedPassword;
	}

	public String getSalt() {
		return this.salt;
	}

	private void setSalt(String salt) {
		this.salt = salt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName.isEmpty()) {
			throw new IllegalArgumentException("No firstname given");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName.isEmpty()) {
			throw new IllegalArgumentException("No last name given");
		}
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return getFirstName() + " " + getLastName() + ": " + getUserid() + ", " + getEmail();
	}
}
