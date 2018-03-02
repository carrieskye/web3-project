package domain.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.model.Country;

public class CountryDbInMemory implements CountryDb{
	private Map<String, Country> countries = new HashMap<String, Country>();
	
	public CountryDbInMemory () {
	}
	
	public void add(Country country){
		countries.put(country.getName(), country);
	}
	
	public void create(String name){
		Country country = new Country(name);
		countries.put(name, country);
	}
	
	public List<Country> getAll(){
		return new ArrayList<Country>(countries.values());
	}

	
}
