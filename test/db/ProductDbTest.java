package db;

import static org.junit.Assert.*;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import domain.Product;

public class ProductDbTest {
	private ProductDb db = new ProductDbSql(getProperties());
	Product product1;
	Product product2;
	Product product3;
	private int records;

	@Before
	public void generateTestData() {
		records = db.getAll().size();
		product1 = new Product(records, "Banana", "Yellow fruit", 0.2, 500);
		product2 = new Product(records + 1, "Strawberry", "Red fruit with black spots", 0.1, 200);
		product3 = new Product(records + 2, "Kiwi", "Green fruit with brown peel", 0.1, 50);

		db.add(product1);
		db.add(product2);
		db.add(product3);
	}

	@After
	public void clearTestData() {
		int newRecords = db.getAll().size() - records;
		for (int i = newRecords - 1; i >= 0; i--) {
			db.delete(records + i);
		}
	}

	@Test
	public void getReturnsProductWhenCorrectIdGiven() {
		assertEquals(db.get(records), product1);
	}

	@Test
	public void getReturnsNullWhenNoCorrespondingProductExists() {
		assertEquals(db.get(records + 3), null);
	}

	@Test
	public void getAllShowsListOfAllProducts() {
		assertTrue(db.getAll().contains(product1));
		assertTrue(db.getAll().contains(product2));
		assertTrue(db.getAll().contains(product3));
	}

	@Test
	public void addAddsProductWithCorrectValues() {
		Product product = new Product(records + 3, "Coconut", "White fruit with milk", 3.0, 20);
		db.add(product);
		assertEquals(db.get(records + 3), product);
		assertTrue(db.getAll().contains(product));
	}

	@Test(expected = DbException.class)
	public void addThrowsExceptionForEmptyProduct() {
		db.add(null);
	}

	private static Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("url", "jdbc:postgresql://databanken.ucll.be:51718/2TXVT?currentSchema=r0458882_test");
		properties.setProperty("user", Login.user);
		properties.setProperty("password", Login.password);
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		return properties;
	}
}
