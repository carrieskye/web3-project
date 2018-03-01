package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;
import domain.Product;
import domain.ShopService;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShopService service = new ShopService();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String destination = "";
		String action = request.getParameter("action");

		switch (action) {
		case "signUp":
			destination = "signUp.jsp";
			break;
		case "addPerson":
			destination = addPerson(request, response);
			break;
		case "userOverview":
			destination = showPersons(request, response);
			break;
		case "productOverview":
			destination = showProducts(request, response);
			break;
		case "productForm":
			destination = "addProduct.jsp";
			break;
		case "addProduct":
			destination = addProduct(request, response);
			break;
		case "updateForm":
			destination = updateProductForm(request, response);
			break;
		case "updateProduct":
			destination = updateProduct(request, response);
			break;
		case "deleteProductConfirmation":
			destination = deleteProductConfirmation(request, response);
			break;
		case "deleteProduct":
			destination = deleteProduct(request, response);
			break;
		default:
			break;
		}

		RequestDispatcher view = request.getRequestDispatcher(destination);
		view.forward(request, response);
	}

	private String addPerson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Person person = new Person();

		List<String> result = new ArrayList<String>();
		getUserid(person, request, result);
		getFirstName(person, request, result);
		getLastName(person, request, result);
		getEmail(person, request, result);
		getPassword(person, request, result);

		String destination;
		if (result.size() > 0) {
			request.setAttribute("result", result);
			destination = "signUp.jsp";
		} else {
			try {
				service.addPerson(person);
				destination = "index.jsp";
				// destination = showPersons(request, response);
			} catch (Exception e) {
				result.add(e.getMessage());
				request.setAttribute("result", result);
				destination = "signUp.jsp";
			}
		}
		return destination;
	}

	private String showPersons(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Person> persons = service.getPersons();
		request.setAttribute("persons", persons);
		return "userOverview.jsp";
	}

	private String showProducts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Product> products = service.getProducts();
		request.setAttribute("products", products);
		return "productOverview.jsp";
	}

	private String addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
				service.addProduct(product);
				destination = showProducts(request, response);
			} catch (Exception e) {
				result.add(e.getMessage());
				request.setAttribute("result", result);
				destination = "addProduct.jsp";
			}
		}
		return destination;
	}

	private String updateProductForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Product product = service.getProduct(Integer.parseInt(request.getParameter("productId")));

		request.setAttribute("productId", product.getProductId());
		request.setAttribute("namePreviousValue", product.getName());
		request.setAttribute("descriptionPreviousValue", product.getDescription());
		request.setAttribute("pricePreviousValue", product.getPrice());

		String destination = "updateProduct.jsp";
		return destination;
	}

	private String updateProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("productId"));
		Product product = service.getProduct(productId);

		List<String> result = new ArrayList<String>();

		getName(product, request, result);
		getDescription(product, request, result);
		getPrice(product, request, result);

		String destination;
		if (result.size() > 0) {
			request.setAttribute("result", result);
			request.setAttribute("productId", productId);
			destination = "updateProduct.jsp";
		} else {
			try {
				service.updateProduct(product);
				destination = showProducts(request, response);
			} catch (Exception e) {
				result.add(e.getMessage());
				request.setAttribute("result", result);
				request.setAttribute("productId", productId);
				destination = "updateProduct.jsp";
			}
		}
		return destination;
	}

	private String deleteProductConfirmation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Product product = service.getProduct(Integer.parseInt(request.getParameter("productId")));

		request.setAttribute("productId", product.getProductId());
		request.setAttribute("name", product.getName());

		String destination = "deleteProduct.jsp";
		return destination;
	}

	private String deleteProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("productId"));
		service.deleteProduct(productId);
		return showProducts(request, response);
	}

	private void getUserid(Person person, HttpServletRequest request, List<String> result) {
		String userid = request.getParameter("userid");
		request.setAttribute("useridPreviousValue", userid);
		try {
			person.setUserid(userid);
			request.setAttribute("useridClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("useridClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getFirstName(Person person, HttpServletRequest request, List<String> result) {
		String firstName = request.getParameter("firstName");
		request.setAttribute("firstNamePreviousValue", firstName);
		try {
			person.setFirstName(firstName);
			request.setAttribute("firstNameClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("firstNameClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getLastName(Person person, HttpServletRequest request, List<String> result) {
		String lastName = request.getParameter("lastName");
		request.setAttribute("lastNamePreviousValue", lastName);
		try {
			person.setLastName(lastName);
			request.setAttribute("lastNameClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("lastNameClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getEmail(Person person, HttpServletRequest request, List<String> result) {
		String email = request.getParameter("email");
		request.setAttribute("emailPreviousValue", email);
		try {
			person.setEmail(email);
			request.setAttribute("emailClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("emailClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getPassword(Person person, HttpServletRequest request, List<String> result) {
		String password = request.getParameter("password");
		request.setAttribute("passwordPreviousValue", password);
		try {
			person.setPassword(password);
			request.setAttribute("passwordClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("passwordClass", "has-error");
			result.add(exc.getMessage());
		}
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
