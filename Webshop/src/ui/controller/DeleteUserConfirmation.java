package ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;

public class DeleteUserConfirmation extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		Person person = getService().getPerson(request.getParameter("userid"));

		request.setAttribute("userid", person.getUserid());
		request.setAttribute("firstname", person.getFirstName());
		request.setAttribute("lastname", person.getLastName());

		String destination = "deleteUser.jsp";
		return destination;
	}

}
