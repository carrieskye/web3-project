package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import domain.Product;

public class ProductDbSql implements ProductDb {
	private Properties properties = new Properties();
	private String url = "jdbc:postgresql://databanken.ucll.be:51718/2TXVT";

	public ProductDbSql() {
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

	@Override
	public Product get(int id) {
		Product product = null;
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM r0458882.product WHERE productid = " + id);
			while (result.next()) {
				int productId = result.getInt("productid");
				String name = result.getString("name");
				String description = result.getString("description");
				double price = result.getDouble("price");
				product = new Product(productId, name, description, price);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return product;
	}

	@Override
	public List<Product> getAll() {
		List<Product> products = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM r0458882.product");
			while (result.next()) {
				int productId = result.getInt("productid");
				String name = result.getString("name");
				String description = result.getString("description");
				double price = result.getDouble("price");
				Product product = new Product(productId, name, description, price);
				products.add(product);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return products;
	}

	@Override
	public void add(Product product) {
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT COUNT(*) AS number FROM r0458882.product");
			result.next();
			int productId = result.getInt("number");
			statement.execute("INSERT INTO r0458882.product VALUES (" + productId + ", '" + product.getName() + "', '"
					+ product.getDescription() + "', " + product.getPrice() + ")");
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	@Override
	public void update(Product product) {
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			statement.execute("UPDATE r0458882.product SET name = '" + product.getName() + "', description = '"
					+ product.getDescription() + "', price = " + product.getPrice() + "WHERE productid = "
					+ product.getProductId());
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(int id) {
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			statement.execute("DELETE FROM r0458882.product WHERE productid = " + id);
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

}
