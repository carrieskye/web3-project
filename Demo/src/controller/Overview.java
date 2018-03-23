package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.model.Country;

public class Overview extends RequestHandler{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		Country mostPopular = getService().getMostPopularCountry();
		request.setAttribute("popular", mostPopular);
		List<Country> countries = getService().getAll();
		request.setAttribute("countries", countries);
		return "countryOverview.jsp";
	}

}
