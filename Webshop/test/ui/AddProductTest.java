package ui;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddProductTest {
	private WebDriver driver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/Applications/App_downloads/chromedriver");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/Webshop/Controller?action=productForm");
	}

	@After
	public void clean() {
		driver.quit();
	}

	private void fillOutField(String name, String value) {
		WebElement field = driver.findElement(By.id(name));
		field.clear();
		field.sendKeys(value);
	}

	private void submitForm(String name, String description, double price) {
		fillOutField("name", name);
		fillOutField("description", description);
		fillOutField("price", String.valueOf(price));

		WebElement button = driver.findElement(By.id("addProduct"));
		button.click();
	}

	@Test
	public void testAddProductCorrect() {
		submitForm("Rose", "Thorny plant", 2.25);

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
		submitForm("", "Big tree", 10.75);

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
	}

	@Test
	public void testAddProductDescriptionEmpty() {
		submitForm("Rosemary", "", 1.5);

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
	}

	@Test
	public void testAddProductPriceNegative() {
		submitForm("Apple tree", "Apple growing tree", -1);

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
	}
	
}