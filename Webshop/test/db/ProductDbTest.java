package db;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import domain.Product;

public class ProductDbTest {
	private ProductDb db = new ProductDbSql();
	Product product1;
	Product product2;
	Product product3;
	private int records;

	@Before
	public void generateTestData() {
		records = db.getAll().size();
		product1 = new Product(records, "Banana", "Yellow fruit", 0.2);
		product2 = new Product(records + 1, "Strawberry", "Red fruit with black spots", 0.1);
		product3 = new Product(records + 2, "Kiwi", "Green fruit with brown peel", 0.1);

		db.add(product1);
		db.add(product2);
		db.add(product3);
	}

	@Test
	public void get_returns_product_when_correct_id_given() {
		assertEquals(db.get(records), product1);
	}

	@Test
	public void get_returns_null_when_no_corresponding_product_exists() {
		assertEquals(db.get(records + 3), null);
	}

	@Test
	public void getAll_shows_list_of_all_products() {
		assertTrue(db.getAll().contains(product1));
		assertTrue(db.getAll().contains(product2));
		assertTrue(db.getAll().contains(product3));
	}

	@Test
	public void add_adds_product_with_correct_values() {
		Product product = new Product(records + 3, "Coconut", "White fruit with milk", 3.0);
		db.add(product);
		assertEquals(db.get(records + 3), product);
		assertTrue(db.getAll().contains(product));
	}

	@Test(expected = DbException.class)
	public void add_throws_exception_for_empty_product() {
		db.add(null);
	}

	@After
	public void clearTestData() {
		int newRecords = db.getAll().size() - records;
		for (int i = 0; i < newRecords; i++) {
			db.delete(records + i);
		}
	}

}
