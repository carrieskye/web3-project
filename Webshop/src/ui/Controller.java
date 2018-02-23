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
		case "overview":
			destination = showPersons(request, response);
			break;
		case "productoverview":
			destination = showProducts(request, response);
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

	private String showPersons(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Person> persons = service.getPersons();
		request.setAttribute("persons", persons);
		return "overview.jsp";
	}

	private String showProducts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Product> products = service.getProducts();
		request.setAttribute("products", products);
		return "productOverview.jsp";
	}
}
