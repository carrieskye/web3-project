package ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOut extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		userLoggedIn = false;

		HttpSession session = request.getSession();
		Object color = session.getAttribute("color");
		if (color != null) {
			if (color.equals("pink")){
				request.setAttribute("pinkRegistered", true);
			}
		}
		request.setAttribute("color", null);
		session.invalidate();
		throw new CustomRedirectException("index.jsp");
	}

}
