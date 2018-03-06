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
	private String url = "jdbc:postgresql://databanken.ucll.be:51718/2TXVT?currentSchema=r0458882";

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

	public Product get(int id) {
		Product product = null;
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM product WHERE productid = " + id);
			while (result.next()) {
				String name = result.getString("name");
				String description = result.getString("description");
				double price = result.getDouble("price");
				product = new Product(id, name, description, price);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return product;
	}

	public List<Product> getAll() {
		List<Product> products = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM product");
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

	public void add(Product product) {
		if (product == null) {
			throw new DbException("No product given");
		}
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT COUNT(*) AS number FROM product");
			result.next();
			int productId = result.getInt("number");
			statement.execute("INSERT INTO product VALUES (" + productId + ", '" + product.getName() + "', '"
					+ product.getDescription() + "', " + product.getPrice() + ")");
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public void update(Product product) {
		if (product == null) {
			throw new DbException("No product given");
		}
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			statement.execute(
					"UPDATE product SET name = '" + product.getName() + "', description = '" + product.getDescription()
							+ "', price = " + product.getPrice() + "WHERE productid = " + product.getProductId());
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public void delete(int id) {
		if (id < 0) {
			throw new DbException("No valid id given");
		}
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			statement.execute("DELETE FROM product WHERE productid = " + id);
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

}
