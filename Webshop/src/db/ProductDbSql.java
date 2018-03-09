package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
		String sql = "SELECT * FROM product WHERE productid = ?";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
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
		String sql = "INSERT INTO product (productid, name, description, price) VALUES (?,?,?,?)";
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();
				PreparedStatement prepStatement = connection.prepareStatement(sql);) {
			ResultSet result = statement.executeQuery("SELECT MAX(productid) AS max FROM product");
			result.next();
			int productId = result.getInt("max") + 1;

			prepStatement.setInt(1, productId);
			prepStatement.setString(2, product.getName());
			prepStatement.setString(3, product.getDescription());
			prepStatement.setDouble(4, product.getPrice());

			prepStatement.execute();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public void update(Product product) {
		if (product == null) {
			throw new DbException("No product given");
		}
		String sql = "UPDATE product SET name = ?, description = ?, price = ? WHERE productid = ?";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, product.getName());
			statement.setString(2, product.getDescription());
			statement.setDouble(3, product.getPrice());
			statement.setInt(4, product.getProductId());
			statement.execute();
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
			statement.execute("UPDATE product SET productid = productid - 1 WHERE productid > " + id);
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

}
