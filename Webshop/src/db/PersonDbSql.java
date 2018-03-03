package db;

import java.util.List;
import java.util.Properties;

import domain.Person;

public class PersonDbSql implements PersonDb {
	private Properties properties = new Properties();
	//private String url = "jdbc:postgresql://databanken.ucll.be:51718/2TXVT";

	public PersonDbSql() {
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
	public Person get(String personId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Person person) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(Person person) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String personId) {
		// TODO Auto-generated method stub

	}

}
