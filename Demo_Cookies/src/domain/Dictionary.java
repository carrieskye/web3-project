package domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Dictionary {
	private Set<Language> supportedlanguages = new HashSet<Language>();
	private Map<String, String> greetings = new HashMap<String, String>();

	public Dictionary() {
		super();
		greetings.put("en", "Hi there!");
		greetings.put("fr", "Bonjour!");
		greetings.put("pt", "Olá!");
		greetings.put("es", "¡Hola!");
		greetings.put("nl", "Hallo!");
		
		supportedlanguages.add(new Language("en", "English"));
		supportedlanguages.add(new Language("fr", "Français"));
		supportedlanguages.add(new Language("pt", "Português"));
		supportedlanguages.add(new Language("es", "Español"));
		supportedlanguages.add(new Language("nl", "Nederlands"));
	}
	
	public Set<Language> getSupportedLanguages(){
		return supportedlanguages;
	}
	
	public String getGreeting(String language){
		String greeting = greetings.get(language);
		if(greeting == null){
			greeting = "Hallo";
		}
		return greeting;
	}
}
