package ui.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import domain.model.Country;

public class CountryDbDesktopApplication {

	public static void main(String[] args) throws SQLException {
		Properties properties = new Properties();
		String url = "jdbc:postgresql://gegevensbanken.khleuven.be:51314/webontwerp";
		properties.setProperty("user", "daily.build");
		properties.setProperty("password", "irooZiNgae0daiba");
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		Connection connection = DriverManager.getConnection(url, properties);

		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM test_u0082726.country");

		while (result.next()) {
			String name = result.getString("name");
			String capital = result.getString("capital");
			int inhabitants = result.getInt("inhabitants");
			int votes = result.getInt("votes");
			Country country = new Country(name, inhabitants, capital, votes);
			System.out.println(country);
		}
		statement.close();
		connection.close();
	}

}
