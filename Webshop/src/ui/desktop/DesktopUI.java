package ui.desktop;

import java.util.Properties;
import javax.swing.JOptionPane;
import domain.Person;
import service.ShopService;

public class DesktopUI {

	public static void main(String[] args) {
		ShopService service = new ShopService(getProperties());
		service.addPerson(newPerson());
		for (Person person : service.getPersons()) {
			System.out.println(person);
		}
	}

	private static Person newPerson() {
		Person person = new Person();
		person.setUserid(JOptionPane.showInputDialog("User ID:"));
		person.setFirstName(JOptionPane.showInputDialog("First name:"));
		person.setLastName(JOptionPane.showInputDialog("Last name:"));
		person.setEmail(JOptionPane.showInputDialog("Email:"));
		person.setPassword(JOptionPane.showInputDialog("Password:"));
		return person;
	}
	
	private static Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("url", "jdbc:postgresql://databanken.ucll.be:51718/2TXVT");
		properties.setProperty("user",Login.user);
		properties.setProperty("password", Login.password);
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		return properties;
	}
	
}
