package ui.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import domain.Person;

public class LogIn extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		String destination = "index.jsp";

		List<String> result = new ArrayList<String>();
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String color = request.getParameter("background") == null ? "blue" : "pink";
		try {
			Person user = getService().getUserIfAuthenticated(userid, password);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			request.setAttribute("user", user.getFirstName());
			session.setAttribute("color", color);
			request.setAttribute("color", color);
			userLoggedIn = true;
			request.setAttribute("userLoggedIn", true);
			throw new CustomRedirectException("index.jsp");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			result.add("No valid userid/password");
			request.setAttribute("result", result);
		}
		return destination;
	}

}
