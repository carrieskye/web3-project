package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.Person;

public class PersonDbSql implements PersonDb {
	private Properties properties = new Properties();
	private String url = "jdbc:postgresql://databanken.ucll.be:51718/2TXVT?currentSchema=r0458882";

	public PersonDbSql() {
		properties.setProperty("user", Login.user);
		properties.setProperty("password", Login.password);
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public Person get(String userId) {
		Person person = null;
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM person WHERE userid = '" + userId + "'");
			while (result.next()) {
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				String email = result.getString("email");
				String password = result.getString("password");
				person = new Person(userId, email, password, firstName, lastName);
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
				Person person = new Person(userId, email, password, firstName, lastName);
				persons.add(person);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return persons;
	}

	public void add(Person person) {
		if (get(person.getUserid()) != null){
			throw new DbException("User already exists");
		}
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			statement.execute(
					"INSERT INTO person VALUES ('" + person.getUserid() + "', '" + person.getFirstName() + "', '"
							+ person.getLastName() + "', '" + person.getEmail() + "', '" + person.getPassword() + "')");
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public void update(Person person) {
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			statement.execute("UPDATE person SET firstname = '" + person.getFirstName() + "', lastname = '"
					+ person.getLastName() + "', email = " + person.getEmail() + "', password = " + person.getPassword()
					+ "WHERE userid = '" + person.getUserid() + "'");
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}

	}

	public void delete(String userId) {
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			statement.execute("DELETE FROM person WHERE userid = '" + userId + "'");
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

}
