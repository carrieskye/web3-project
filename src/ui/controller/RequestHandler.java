package ui.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;
import domain.Product;
import service.ShopService;

public abstract class RequestHandler {

	private ShopService service;
	protected boolean userLoggedIn;

	public abstract String handleRequest(HttpServletRequest request, HttpServletResponse response);

	public void setService(ShopService service) {
		this.service = service;
	}

	public ShopService getService() {
		return service;
	}

	protected String OverviewProducts(HttpServletRequest request, HttpServletResponse response){
		List<Product> products = service.getProducts();
		request.setAttribute("products", products);
		return "productOverview.jsp";
	}
	
	protected String OverviewUsers(HttpServletRequest request, HttpServletResponse response){
		List<Person> persons = service.getPersons();
		request.setAttribute("persons", persons);
		return "userOverview.jsp";
	}
	
	public boolean isUserLoggedIn() {
		return this.userLoggedIn;
	}

}
