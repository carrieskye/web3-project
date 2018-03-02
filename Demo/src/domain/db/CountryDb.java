package domain.db;

import java.util.List;

import domain.model.Country;

public interface CountryDb {
	void add(Country country);

	void create(String name);

	List<Country> getAll();
}
