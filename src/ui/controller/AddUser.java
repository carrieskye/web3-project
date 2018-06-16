package ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DbException;
import domain.Person;

public class AddUser extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		if (request.getMethod().equals("GET")) {
			return "signUp.jsp";
		}
		Person person = new Person();

		List<String> result = new ArrayList<String>();
		getUserid(person, request, result);
		getFirstName(person, request, result);
		getLastName(person, request, result);
		getEmail(person, request, result);
		getPassword(person, request, result);

		String destination;
		if (result.size() > 0) {
			request.setAttribute("result", result);
			destination = "signUp.jsp";
		} else {
			try {
				getService().addPerson(person);
				throw new CustomRedirectException("index.jsp");
			} catch (DbException e) {
				result.add(e.getMessage());
				request.setAttribute("result", result);
				destination = "signUp.jsp";
			}
		}
		return destination;
	}

	private void getUserid(Person person, HttpServletRequest request, List<String> result) {
		String userid = request.getParameter("userid");
		request.setAttribute("useridPreviousValue", userid);
		try {
			person.setUserid(userid);
			request.setAttribute("useridClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("useridClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getFirstName(Person person, HttpServletRequest request, List<String> result) {
		String firstName = request.getParameter("firstName");
		request.setAttribute("firstNamePreviousValue", firstName);
		try {
			person.setFirstName(firstName);
			request.setAttribute("firstNameClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("firstNameClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getLastName(Person person, HttpServletRequest request, List<String> result) {
		String lastName = request.getParameter("lastName");
		request.setAttribute("lastNamePreviousValue", lastName);
		try {
			person.setLastName(lastName);
			request.setAttribute("lastNameClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("lastNameClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getEmail(Person person, HttpServletRequest request, List<String> result) {
		String email = request.getParameter("email");
		request.setAttribute("emailPreviousValue", email);
		try {
			person.setEmail(email);
			request.setAttribute("emailClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("emailClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getPassword(Person person, HttpServletRequest request, List<String> result) {
		String password = request.getParameter("password");
		request.setAttribute("passwordPreviousValue", password);
		try {
			person.setPasswordHashed(password);
			request.setAttribute("passwordClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("passwordClass", "has-error");
			result.add(exc.getMessage());
		}
	}

}
