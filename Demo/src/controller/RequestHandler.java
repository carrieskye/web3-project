package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.service.CountryService;

public abstract class RequestHandler {
	
	private CountryService service;
	
	public abstract String handleRequest (HttpServletRequest request, HttpServletResponse response);
	
	public void setService(CountryService service) {
		this.service = service;
	}
	
	public CountryService getService() {
		return service;
	}
	
	
}
