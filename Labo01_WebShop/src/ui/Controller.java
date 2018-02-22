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

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShopService shopService = new ShopService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {
		case "index":
			indexPage(request, response);
			break;
		case "userOverview":
			userOverviewPage(request, response);
			break;
		case "productOverview":
			productOverviewPage(request, response);
			break;
		case "addProduct":
			addProductPage(request, response);
			break;
		case "signUp":
			signUpPage(request, response);
			break;
		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "newPerson":
			createNewPerson(request, response);
			break;
		case "newProduct":
			createNewProduct(request, response);
			break;
		default:
			break;
		}

	}

	private void indexPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher view;
		view = request.getRequestDispatcher("index.jsp");
		view.forward(request, response);
	}

	private void userOverviewPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher view;
		request.setAttribute("personOverview", shopService.getPersons());
		view = request.getRequestDispatcher("personoverview.jsp");
		view.forward(request, response);
	}

	private void productOverviewPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher view;
		request.setAttribute("productOverview", shopService.getProducts());
		view = request.getRequestDispatcher("productoverview.jsp");
		view.forward(request, response);
	}

	private void addProductPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher view;
		view = request.getRequestDispatcher("addproduct.jsp");
		view.forward(request, response);
	}

	private void signUpPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher view;
		view = request.getRequestDispatcher("signUp.jsp");
		view.forward(request, response);
	}

	private void createNewPerson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher view;
		Person person = new Person();
		List<String> result = new ArrayList<String>();
		result = getUserid(person, request, result);
		result = getFirstName(person, request, result);
		result = getLastName(person, request, result);
		result = getEmail(person, request, result);
		result = getPassword(person, request, result);
		if (!result.isEmpty()) {
			request.setAttribute("errormessage", result);
			view = request.getRequestDispatcher("signUp.jsp");
		} else {
			shopService.addPerson(person);
			view = request.getRequestDispatcher("index.jsp");
		}
		view.forward(request, response);
	}

	private void createNewProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher view;
		Product product = new Product();
		List<String> result = new ArrayList<String>();
		result = getName(product, request, result);
		result = getDescription(product, request, result);
		result = getPrice(product, request, result);
		if (!result.isEmpty()) {
			request.setAttribute("errormessage", result);
			view = request.getRequestDispatcher("addproduct.jsp");
			view.forward(request, response);
		} else {
			shopService.addProduct(product);
			productOverviewPage(request, response);
		}
	}

	private List<String> getUserid(Person person, HttpServletRequest request, List<String> result) {
		String userid = request.getParameter("userid");
		try {
			person.setUserid(userid);
			request.setAttribute("useridClass", "has-success");
			request.setAttribute("useridPreviousValue", userid);
		} catch (Exception exc) {
			request.setAttribute("useridClass", "has-error");
			result.add(exc.getMessage());
		}
		return result;
	}

	private List<String> getFirstName(Person person, HttpServletRequest request, List<String> result) {
		String firstName = request.getParameter("firstName");
		try {
			person.setFirstName(firstName);
			request.setAttribute("firstNameClass", "has-success");
			request.setAttribute("firstNamePreviousValue", firstName);
		} catch (Exception exc) {
			request.setAttribute("firstNameClass", "has-error");
			result.add(exc.getMessage());
		}
		return result;
	}

	private List<String> getLastName(Person person, HttpServletRequest request, List<String> result) {
		String lastName = request.getParameter("lastName");
		try {
			person.setLastName(lastName);
			request.setAttribute("lastNameClass", "has-success");
			request.setAttribute("lastNamePreviousValue", lastName);
		} catch (Exception exc) {
			request.setAttribute("lastNameClass", "has-error");
			result.add(exc.getMessage());
		}
		return result;
	}

	private List<String> getEmail(Person person, HttpServletRequest request, List<String> result) {
		String email = request.getParameter("email");
		try {
			person.setEmail(email);
			request.setAttribute("emailClass", "has-success");
			request.setAttribute("emailPreviousValue", email);
		} catch (Exception exc) {
			request.setAttribute("emailClass", "has-error");
			result.add(exc.getMessage());
		}
		return result;
	}

	private List<String> getPassword(Person person, HttpServletRequest request, List<String> result) {
		String password = request.getParameter("password");
		try {
			person.setPassword(password);
			request.setAttribute("passwordClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("passwordClass", "has-error");
			result.add(exc.getMessage());
		}
		return result;
	}

	private List<String> getName(Product product, HttpServletRequest request, List<String> result) {
		String name = request.getParameter("name");
		try {
			product.setName(name);
			request.setAttribute("nameClass", "has-success");
			request.setAttribute("namePreviousValue", name);
		} catch (Exception exc) {
			request.setAttribute("nameClass", "has-error");
			result.add(exc.getMessage());
		}
		return result;
	}

	private List<String> getDescription(Product product, HttpServletRequest request, List<String> result) {
		String description = request.getParameter("description");
		try {
			product.setDescription(description);
			request.setAttribute("descriptionClass", "has-success");
			request.setAttribute("descriptionPreviousValue", description);
		} catch (Exception exc) {
			request.setAttribute("descriptionClass", "has-error");
			result.add(exc.getMessage());
		}
		return result;
	}

	private List<String> getPrice(Product product, HttpServletRequest request, List<String> result) {
		String priceString = request.getParameter("price");
		double price = -1;
		if (!priceString.equals(null) && !priceString.isEmpty()) {
			price = Double.parseDouble(priceString);
		}
		try {
			product.setPrice(price);
			request.setAttribute("priceClass", "has-success");
			request.setAttribute("pricePreviousValue", priceString);
		} catch (Exception exc) {
			request.setAttribute("priceClass", "has-error");
			result.add(exc.getMessage());
		}
		return result;
	}

}
