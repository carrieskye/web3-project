package ui.desktop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import domain.Person;

public class DesktopUI {

	public static void main(String[] args) throws SQLException {
		Properties properties = new Properties();
		String url = "jdbc:postgresql://databanken.ucll.be:51718/2TXVT";
		properties.setProperty("user", Login.user);
		properties.setProperty("password", Login.password);
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		Connection connection = DriverManager.getConnection(url, properties);

		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM r0458882.user");

		while (result.next()) {
			String userid = result.getString("userid");
			String firstName = result.getString("firstname");
			String lastName = result.getString("lastname");
			String email = result.getString("email");
			String password = result.getString("password");
			Person person = new Person(userid, email, password, firstName, lastName);
			System.out.println(person);
		}
		statement.close();
		connection.close();
	}
}
