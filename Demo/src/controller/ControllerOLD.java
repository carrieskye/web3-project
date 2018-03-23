package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.model.Country;
import domain.service.CountryService;

@WebServlet("/ControllerOLD")
public class ControllerOLD extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CountryService service;

	public ControllerOLD() {
		super();
	}

	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		
		Properties properties = new Properties();
		properties.setProperty("user", context.getInitParameter("user"));
		properties.setProperty("password", context.getInitParameter("password"));
		properties.setProperty("ssl", context.getInitParameter("ssl"));
		properties.setProperty("sslfactory", context.getInitParameter("sslfactory"));
		properties.setProperty("url", context.getInitParameter("url"));
		properties.setProperty("currentSchema", context.getInitParameter("currentSchema"));
		
		service = new CountryService(properties);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String destination = showCountries(request, response);

		RequestDispatcher view = request.getRequestDispatcher(destination);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Country country = new Country();

		List<String> result = new ArrayList<String>();
		getName(country, request, result);
		getCapital(country, request, result);
		getNumberOfInhabitants(country, request, result);
		getNumberOfVotes(country, request, result);

		String destination;
		if (result.size() > 0) {
			request.setAttribute("result", result);
			destination = "countryForm.jsp";
		} else {
			service.addCountry(country);
			destination = showCountries(request, response);
		}

		RequestDispatcher view = request.getRequestDispatcher(destination);
		view.forward(request, response);
	}

	private void getName(Country country, HttpServletRequest request, List<String> result) {
		String name = request.getParameter("name");
		request.setAttribute("namePreviousValue", name);
		try {
			country.setName(name);
			request.setAttribute("nameClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("nameClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getCapital(Country country, HttpServletRequest request, List<String> result) {
		String capital = request.getParameter("capital");
		request.setAttribute("capitalPreviousValue", capital);
		try {
			country.setCapital(capital);
			request.setAttribute("capitalClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("capitalClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getNumberOfVotes(Country country, HttpServletRequest request, List<String> result) {
		String votes = request.getParameter("votes");
		request.setAttribute("votesPreviousValue", votes);
		try {
			int numberOfVotes = Integer.parseInt(votes);
			country.setVotes(numberOfVotes);
			request.setAttribute("votesClass", "has-success");
		} catch (NumberFormatException exc) {
			request.setAttribute("votesClass", "has-error");
			result.add("Please enter a valid number of votes!");
		} catch (Exception exc) {
			request.setAttribute("votesClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getNumberOfInhabitants(Country country, HttpServletRequest request, List<String> result) {
		String inhabitants = request.getParameter("inhabitants");
		request.setAttribute("inhabitantsPreviousValue", inhabitants);
		try {
			int numberOfInhabitants = Integer.parseInt(inhabitants);
			country.setNumberInhabitants(numberOfInhabitants);
			request.setAttribute("inhabitantsClass", "has-success");
		} catch (NumberFormatException exc) {
			request.setAttribute("inhabitantsClass", "has-error");
			result.add("Please enter a valid number of inhabitants!");
		} catch (Exception exc) {
			request.setAttribute("inhabitantsClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private String showCountries(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Country mostPopular = service.getMostPopularCountry();
		request.setAttribute("popular", mostPopular);
		List<Country> countries = service.getAll();
		request.setAttribute("countries", countries);
		return "countryOverview.jsp";
	}

}
