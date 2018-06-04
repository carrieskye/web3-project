package ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;

public class DeleteProduct extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		if (request.getMethod().equals("GET")) {
			Product product = getService().getProduct(Integer.parseInt(request.getParameter("productId")));

			request.setAttribute("productId", product.getProductId());
			request.setAttribute("name", product.getName());

			String destination = "deleteProduct.jsp";
			return destination;
		}
		int productId = Integer.parseInt(request.getParameter("productId"));
		getService().deleteProduct(productId);
		throw new CustomRedirectException("Controller?action=OverviewProducts");
	}

}
