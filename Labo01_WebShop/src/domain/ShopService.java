package domain;

import java.util.List;

import db.PersonDb;
import db.PersonDbInMemory;
import db.ProductDb;
import db.ProductDbInMemory;

public class ShopService {
	private PersonDb personDb;
	private ProductDb productDb;

	public ShopService() {
		personDb = new PersonDbInMemory();
		productDb = new ProductDbInMemory();
		addProducts();
	}
	
	public void addProducts(){
		addProduct(new Product(12, "Banaan", "Geel en krom", 0.5));
		addProduct(new Product(12, "Tomaat", "Rood en rond", 0.4));
		addProduct(new Product(12, "Wortel", "Oranje en recht", 0.3));
	}

	public Person getPerson(String personId) {
		return getPersonDb().get(personId);
	}

	public List<Person> getPersons() {
		return getPersonDb().getAll();
	}

	public void addPerson(Person person) {
		getPersonDb().add(person);
	}

	public void updatePersons(Person person) {
		getPersonDb().update(person);
	}

	public void deletePerson(String id) {
		getPersonDb().delete(id);
	}

	public Product getProduct(int productId) {
		return getProductDb().get(productId);
	}

	public List<Product> getProducts() {
		return getProductDb().getAll();
	}

	public void addProduct(Product product) {
		getProductDb().add(product);
	}

	public void updateProducts(Product product) {
		getProductDb().update(product);
	}

	public void deleteProduct(int productId) {
		getProductDb().delete(productId);
	}

	private PersonDb getPersonDb() {
		return personDb;
	}

	private ProductDb getProductDb() {
		return productDb;
	}
}
