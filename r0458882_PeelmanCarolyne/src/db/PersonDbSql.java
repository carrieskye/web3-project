package db;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.Person;

public class PersonDbSql implements PersonDb {
	private Properties properties = new Properties();
	private String url;

	public PersonDbSql(Properties properties) {
		try {
			Class.forName("org.postgresql.Driver");
			this.properties = properties;
			this.url = properties.getProperty("url");
		} catch (ClassNotFoundException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public Person get(String userId) {
		Person person = null;
		String sql = "SELECT * FROM person WHERE userid = ?";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, userId);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				String email = result.getString("email");
				String password = result.getString("password");
				String salt = result.getString("salt");
				person = new Person(userId, email, password, firstName, lastName, salt);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return person;
	}

	public List<Person> getAll() {
		List<Person> persons = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM person");
			while (result.next()) {
				String userId = result.getString("userid");
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				String email = result.getString("email");
				String password = result.getString("password");
				String salt = result.getString("salt");
				Person person = new Person(userId, email, password, firstName, lastName, salt);
				persons.add(person);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return persons;
	}
	
	public List<Person> getAllByLastname() {
		List<Person> persons = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM person ORDER BY lastname");
			while (result.next()) {
				String userId = result.getString("userid");
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				String email = result.getString("email");
				String password = result.getString("password");
				String salt = result.getString("salt");
				Person person = new Person(userId, email, password, firstName, lastName, salt);
				persons.add(person);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return persons;
	}

	public void add(Person person) {
		if (get(person.getUserid()) != null) {
			throw new DbException("User already exists");
		}
		String sql = "INSERT INTO person (userid, firstname, lastname, email, password, salt) VALUES (?,?,?,?,?,?)";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, person.getUserid());
			statement.setString(2, person.getFirstName());
			statement.setString(3, person.getLastName());
			statement.setString(4, person.getEmail());
			statement.setString(5, person.getPassword());
			statement.setString(6, person.getSalt());
			statement.execute();

		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}

	}

	public void update(Person person) {
		String sql = "UPDATE person SET firstname = ?, lastname = ?, email = ?, password = ?, salt = ? WHERE userid = ?";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, person.getFirstName());
			statement.setString(2, person.getLastName());
			statement.setString(3, person.getEmail());
			statement.setString(4, person.getPassword());
			statement.setString(5, person.getSalt());
			statement.setString(6, person.getUserid());
			statement.execute();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}

	}

	public void delete(String userId) {
		String sql = "DELETE FROM person WHERE userid = ?";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, userId);
			statement.execute();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public String hashedPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest hash = MessageDigest.getInstance("SHA-512");
		hash.reset();
		hash.update(password.getBytes("UTF-8"));
		String hashedPassword = new BigInteger(1, hash.digest()).toString(16);
		return hashedPassword;
	}

}
