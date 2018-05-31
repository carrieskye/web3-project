package ui.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOut extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		userLoggedIn = false;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("color") && cookie.getValue().equals("pink")) {
				request.setAttribute("pinkRegistered", true);
			}
		}
		request.setAttribute("color", null);
		return "index.jsp";
	}

}
