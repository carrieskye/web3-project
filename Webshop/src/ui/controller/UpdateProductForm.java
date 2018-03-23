package ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;

public class UpdateProductForm extends RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		Product product = getService().getProduct(Integer.parseInt(request.getParameter("productId")));

		request.setAttribute("productId", product.getProductId());
		request.setAttribute("namePreviousValue", product.getName());
		request.setAttribute("descriptionPreviousValue", product.getDescription());
		request.setAttribute("pricePreviousValue", product.getPrice());

		String destination = "updateProduct.jsp";
		return destination;
	}

}
