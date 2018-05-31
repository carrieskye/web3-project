package ui.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;

public class LogIn extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		String destination = "index.jsp";

		List<String> result = new ArrayList<String>();
		Person person = findPerson(request, result);
		if (person != null && checkPassword(person, request, result)) {
			if (request.getParameter("remember") != null) {
				response.addCookie(new Cookie("userid", person.getUserid()));
			} else {
				response.addCookie(new Cookie("userid", null));
			}
			request.setAttribute("userid", person.getUserid());

			String color = request.getParameter("background") == null ? "blue" : "pink";
			response.addCookie(new Cookie("color", color));
			request.setAttribute("color", color);
			userLoggedIn = true;
			request.setAttribute("userLoggedIn", true);
		} else {
			request.setAttribute("result", result);
		}
		return destination;
	}

	private Person findPerson(HttpServletRequest request, List<String> result) {
		String userid = request.getParameter("userid");
		for (Person person : getService().getPersons()) {
			if (person.getUserid().equals(userid)) {
				request.setAttribute("useridLoginClass", "has-success");
				return person;
			}
		}
		request.setAttribute("useridLoginClass", "has-error");
		result.add("Userid does not exist");
		return null;
	}

	private boolean checkPassword(Person person, HttpServletRequest request, List<String> result) {
		String password = request.getParameter("password");
		try {
			if (person.isCorrectPassword(password)) {
				request.setAttribute("passwordClass", "has-success");
				return true;
			} else {
				request.setAttribute("passwordClass", "has-error");
				result.add("Please enter a valid password");
				request.setAttribute("userid", person.getUserid());
				return false;
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}

}
