package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import domain.Person;

public class PersonDbSQL implements PersonDb {
	private Secret secret;
	private Properties properties;
	private String url;
	private Connection connection;
	private Statement statement;

	public PersonDbSQL() {
		Person administrator = new Person("admin", "admin@ucll.be", "t", "Ad", "Ministrator");
		add(administrator);
		try {
			setDbConnection();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Person get(String personId) {
		Person person = new Person();
		ResultSet result;
		try {
			result = statement.executeQuery("SELECT * FROM person WHERE personId = '" + personId + "'");
			person = readColumns(person, result);
		} catch (SQLException e) {
			e.getMessage();
		}
		return person;
	}

	@Override
	public List<Person> getAll() {
		Person person = new Person();
		List<Person> all = null;
		ResultSet result;
		try {
			result = statement.executeQuery("SELECT * FROM person");
			while (result.next()) {
				person = readColumns(person, result);
				all.add(person);
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return all;
	}

	@Override
	public void add(Person person) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Person person) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String personId) {
		// TODO Auto-generated method stub

	}

	private Person readColumns(Person person, ResultSet result) throws SQLException {
		person.setUserid(result.getString("userid"));
		person.setFirstName(result.getString("firstname"));
		person.setLastName(result.getString("lastname"));
		person.setEmail(result.getString("email"));
		person.setPassword(result.getString("password"));
		return person;
	}

	public void setDbConnection() throws ClassNotFoundException, SQLException {
		secret = new Secret();
		properties = new Properties();
		url = "jdbc:postgresql://gegevensbanken.khleuven.be:51617/2TX33?currentSchema=r0458882";
		properties.setProperty("user", secret.getUsername());
		properties.setProperty("password", secret.getPassword());
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection(url, properties);
		statement = connection.createStatement();
	}

	public void closeDbConnection() throws SQLException {
		statement.close();
		connection.close();
	}

}
