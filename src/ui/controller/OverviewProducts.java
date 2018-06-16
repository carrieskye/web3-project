package ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OverviewProducts extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		return OverviewProducts(request, response);
	}

}
