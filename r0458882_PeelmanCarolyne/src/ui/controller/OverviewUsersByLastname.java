package ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;

public class OverviewUsersByLastname extends RequestHandler{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		List<Person> persons = getService().getPersonsByLastname();
		request.setAttribute("persons", persons);
		return "userOverview.jsp";
	}

}
