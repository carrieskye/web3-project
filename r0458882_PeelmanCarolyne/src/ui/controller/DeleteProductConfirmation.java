package ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;

public class DeleteProductConfirmation extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		Product product = getService().getProduct(Integer.parseInt(request.getParameter("productId")));

		request.setAttribute("productId", product.getProductId());
		request.setAttribute("name", product.getName());

		String destination = "deleteProduct.jsp";
		return destination;
	}

}
