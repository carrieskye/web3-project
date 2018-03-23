package ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUser extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		String userid = request.getParameter("userid");
		getService().deletePerson(userid);
		return OverviewUsers(request, response);
	}

}
