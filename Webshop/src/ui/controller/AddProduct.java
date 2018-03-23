package ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;

public class AddProduct extends RequestHandler{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		Product product = new Product();

		List<String> result = new ArrayList<String>();
		getName(product, request, result);
		getDescription(product, request, result);
		getPrice(product, request, result);

		String destination;
		if (result.size() > 0) {
			request.setAttribute("result", result);
			destination = "addProduct.jsp";
		} else {
			try {
				getService().addProduct(product);
				destination = OverviewProducts(request, response);
			} catch (Exception e) {
				result.add(e.getMessage());
				request.setAttribute("result", result);
				destination = "addProduct.jsp";
			}
		}
		return destination;
	}
	
	private void getName(Product product, HttpServletRequest request, List<String> result) {
		String name = request.getParameter("name");
		request.setAttribute("namePreviousValue", name);
		try {
			product.setName(name);
			request.setAttribute("nameClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("nameClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getDescription(Product product, HttpServletRequest request, List<String> result) {
		String description = request.getParameter("description");
		request.setAttribute("descriptionPreviousValue", description);
		try {
			product.setDescription(description);
			request.setAttribute("descriptionClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("descriptionClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getPrice(Product product, HttpServletRequest request, List<String> result) {
		String price = request.getParameter("price");
		request.setAttribute("pricePreviousValue", price);
		try {
			product.setPrice(price);
			request.setAttribute("priceClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("priceClass", "has-error");
			result.add(exc.getMessage());
		}
	}

}
