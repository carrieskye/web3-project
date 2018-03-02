package domain.service;

import java.util.List;

import domain.db.CountryDb;
import domain.db.CountryDbSql;
import domain.model.Country;

public class CountryService {
	private CountryDb db = new CountryDbSql();

	public void addCountry(Country country) {
		db.add(country);
	}

	public void create(String name) {
		db.create(name);
	}

	public List<Country> getAll() {
		return db.getAll();
	}

	public Country getMostPopularCountry() {
		int highestVotes = -1;
		Country mostPopular = null;
		List<Country> countryList = db.getAll();
		for (int i = 0; i < countryList.size(); i++) {
			if (countryList.get(i).getVotes() > highestVotes) {
				mostPopular = countryList.get(i);
				highestVotes = mostPopular.getVotes();
			}
		}
		return mostPopular;
	}
}
