package domain.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.model.Country;

public class CountryDbSql implements CountryDb {
	private Properties properties = new Properties();
	private String url = "jdbc:postgresql://gegevensbanken.khleuven.be:51314/webontwerp";

	public CountryDbSql() {
		properties.setProperty("user", "daily.build");
		properties.setProperty("password", "irooZiNgae0daiba");
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public void add(Country country) {
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			statement.execute("INSERT INTO test_u0082726.country VALUES ('" + country.getName() + "', '"
					+ country.getCapital() + "', '" + String.valueOf(country.getNumberInhabitants()) + "', '"
					+ String.valueOf(country.getVotes()) + ")");
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}

	}

	public void create(String name) {
		// TODO Auto-generated method stub

	}

	public List<Country> getAll() {
		List<Country> countries = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM test_u0082726.country");
			while (result.next()) {
				String name = result.getString("name");
				String capital = result.getString("capital");
				int inhabitants = result.getInt("inhabitants");
				int votes = result.getInt("votes");
				Country country = new Country(name, inhabitants, capital, votes);
				countries.add(country);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return countries;
	}

	public Country getMostPopularCountry() {
		Country mostPopularCountry = null;
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM test_u0082726.country");
			while (result.next()) {
				String name = result.getString("name");
				String capital = result.getString("capital");
				int inhabitants = result.getInt("inhabitants");
				int votes = result.getInt("votes");
				Country country = new Country(name, inhabitants, capital, votes);
				if (mostPopularCountry == null) {
					mostPopularCountry = country;
				} else {
					if (country.getVotes() > mostPopularCountry.getVotes()) {
						mostPopularCountry = country;
					}
				}
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return mostPopularCountry;
	}

}
