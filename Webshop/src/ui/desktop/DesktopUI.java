package ui.desktop;

import javax.swing.JOptionPane;

import db.PersonDb;
import db.PersonDbSql;
import domain.Person;

public class DesktopUI {

	public static void main(String[] args) {
		PersonDb db = new PersonDbSql();
		db.add(addPerson());
		for (Person person : db.getAll()) {
			System.out.println(person);
		}
	}

	private static Person addPerson() {
		Person person = new Person();
		person.setUserid(JOptionPane.showInputDialog("User ID:"));
		person.setFirstName(JOptionPane.showInputDialog("First name:"));
		person.setLastName(JOptionPane.showInputDialog("Last name:"));
		person.setEmail(JOptionPane.showInputDialog("Email:"));
		person.setPassword(JOptionPane.showInputDialog("Password:"));
		return person;
	}
}
