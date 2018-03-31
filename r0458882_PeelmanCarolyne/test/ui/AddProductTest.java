package ui;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import db.DbException;

public class AddProductTest {
	private WebDriver driver;
	private static Properties properties = new Properties();
	private static String url = "jdbc:postgresql://databanken.ucll.be:51718/2TXVT?currentSchema=r0458882_test";

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/Applications/App_downloads/chromedriver");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/r0458882_PeelmanCarolyne/addProduct.jsp");
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

	@After
	public void clean() {
		driver.quit();
	}

	@AfterClass
	public static void teardown() {
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery(
					"SELECT productid FROM product WHERE name = 'Rose' AND description = 'Thorny plant' AND price = 2.25 AND stock = 500");
			result.next();
			int id = result.getInt("productid");
			statement.execute("DELETE FROM product WHERE productid = " + id);
			statement.execute("UPDATE product SET productid = productid - 1 WHERE productid > " + id);
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	private void fillOutField(String name, String value) {
		WebElement field = driver.findElement(By.id(name));
		field.clear();
		field.sendKeys(value);
	}

	private void submitForm(String name, String description, double price, int stock) {
		fillOutField("name", name);
		fillOutField("description", description);
		fillOutField("price", String.valueOf(price));
		fillOutField("stock", String.valueOf(stock));

		WebElement button = driver.findElement(By.id("addProduct"));
		button.click();
	}

	@Test
	public void testAddProductCorrect() {
		submitForm("Rose", "Thorny plant", 2.25, 500);

		String title = driver.getTitle();
		assertEquals("Product Overview", title);

		ArrayList<WebElement> listItems = (ArrayList<WebElement>) driver.findElements(By.cssSelector("table tr"));
		boolean found = false;
		for (WebElement listItem : listItems) {
			if (listItem.getText().contains("Rose") && listItem.getText().contains("Thorny plant")) {
				found = true;
			}
		}
		assertEquals(true, found);
	}

	@Test
	public void testAddProductNameEmpty() {
		submitForm("", "Big tree", 10.75, 50);

		String title = driver.getTitle();
		assertEquals("Add Product", title);

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No name given", errorMsg.getText());

		WebElement fieldName = driver.findElement(By.id("name"));
		assertEquals("", fieldName.getAttribute("value"));

		WebElement fieldDescription = driver.findElement(By.id("description"));
		assertEquals("Big tree", fieldDescription.getAttribute("value"));

		WebElement fieldPrice = driver.findElement(By.id("price"));
		assertEquals("10.75", fieldPrice.getAttribute("value"));
		
		WebElement fieldStock = driver.findElement(By.id("stock"));
		assertEquals("50", fieldStock.getAttribute("value"));
	}

	@Test
	public void testAddProductDescriptionEmpty() {
		submitForm("Rosemary", "", 1.5, 20);

		String title = driver.getTitle();
		assertEquals("Add Product", title);

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No description given", errorMsg.getText());

		WebElement fieldName = driver.findElement(By.id("name"));
		assertEquals("Rosemary", fieldName.getAttribute("value"));

		WebElement fieldDescription = driver.findElement(By.id("description"));
		assertEquals("", fieldDescription.getAttribute("value"));

		WebElement fieldPrice = driver.findElement(By.id("price"));
		assertEquals("1.5", fieldPrice.getAttribute("value"));
		
		WebElement fieldStock = driver.findElement(By.id("stock"));
		assertEquals("20", fieldStock.getAttribute("value"));
	}

	@Test
	public void testAddProductPriceNegative() {
		submitForm("Apple tree", "Apple growing tree", -1, 10);

		String title = driver.getTitle();
		assertEquals("Add Product", title);

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("Give a valid price", errorMsg.getText());

		WebElement fieldName = driver.findElement(By.id("name"));
		assertEquals("Apple tree", fieldName.getAttribute("value"));

		WebElement fieldDescription = driver.findElement(By.id("description"));
		assertEquals("Apple growing tree", fieldDescription.getAttribute("value"));

		WebElement fieldPrice = driver.findElement(By.id("price"));
		assertEquals("-1.0", fieldPrice.getAttribute("value"));
		
		WebElement fieldStock = driver.findElement(By.id("stock"));
		assertEquals("10", fieldStock.getAttribute("value"));
	}
	
	@Test
	public void testAddProductStockNegative() {
		submitForm("Apple tree", "Apple growing tree", 20, -1);

		String title = driver.getTitle();
		assertEquals("Add Product", title);

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("Give a valid stock", errorMsg.getText());

		WebElement fieldName = driver.findElement(By.id("name"));
		assertEquals("Apple tree", fieldName.getAttribute("value"));

		WebElement fieldDescription = driver.findElement(By.id("description"));
		assertEquals("Apple growing tree", fieldDescription.getAttribute("value"));
		
		WebElement fieldPrice = driver.findElement(By.id("price"));
		assertEquals("20.0", fieldPrice.getAttribute("value"));
		
		WebElement fieldStock = driver.findElement(By.id("stock"));
		assertEquals("-1", fieldStock.getAttribute("value"));
	}
}