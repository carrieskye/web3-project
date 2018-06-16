package ui.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Home extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("userLoggedIn", userLoggedIn);
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("color") && cookie.getValue().equals("pink")) {
				request.setAttribute("pinkRegistered", true);
			}
		}
		return "index.jsp";
	}

}
