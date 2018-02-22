package desktopUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

import domain.Person;

public class WebShopDbDemo {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Secret secret = new Secret();
		Properties properties = new Properties();
		String url = "jdbc:postgresql://gegevensbanken.khleuven.be:51617/2TX33?currentSchema=r0458882";
		properties.setProperty("user", secret.getUsername());
		properties.setProperty("password", secret.getPassword());
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection(url, properties);
		Statement statement = connection.createStatement();
		addPerson(statement);
		ResultSet result = statement.executeQuery("SELECT * FROM person");
		overview(result);
		statement.close();
		connection.close();

	}

	public static void overview(ResultSet result) throws ClassNotFoundException, SQLException {
		while (result.next()) {
			String userid = result.getString("userid");
			String firstname = result.getString("firstname");
			String lastname = result.getString("lastname");
			String email = result.getString("email");
			String password = result.getString("password");
			Person person = new Person(userid, email, password, firstname, lastname);
			System.out.println(person.toString());
		}
	}

	public static void addPerson(Statement statement) throws ClassNotFoundException, SQLException {
		String userid = JOptionPane.showInputDialog("User ID:");
		String firstname = JOptionPane.showInputDialog("Firstname:");
		String lastname = JOptionPane.showInputDialog("Lastname:");
		String email = JOptionPane.showInputDialog("Email:");
		String password = JOptionPane.showInputDialog("Password:");
		statement.executeUpdate("INSERT INTO person(userid, firstname, lastname, email, password) " + "VALUES ('"
				+ userid + "', '" + firstname + "', '" + lastname + "', '" + email + "', '" + password + "')");
	}

}
