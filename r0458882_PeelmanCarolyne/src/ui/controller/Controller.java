package ui.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Person;
import service.ShopService;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShopService service;

	private ControllerFactory controllerFactory = new ControllerFactory();

	public Controller() {
		super();
	}

	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();

		Properties properties = new Properties();
		properties.setProperty("user", context.getInitParameter("user"));
		properties.setProperty("password", context.getInitParameter("password"));
		properties.setProperty("ssl", context.getInitParameter("ssl"));
		properties.setProperty("sslfactory", context.getInitParameter("sslfactory"));
		properties.setProperty("url", context.getInitParameter("url"));

		service = new ShopService(properties);
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
		String action = request.getParameter("action");
		String destination = "index.jsp";
		request.setAttribute("userid", getUseridFromSession(request));

		if (action != null) {
			RequestHandler handler;
			handler = controllerFactory.getController(action, service);
			if (handler.isUserLoggedIn()) {
				request.setAttribute("color", getColorFromSession(request));
			}
			try {
				destination = handler.handleRequest(request, response);
			} catch (CustomRedirectException e) {
					response.sendRedirect(e.getLocation());
					return;
			}
		}
		RequestDispatcher view = request.getRequestDispatcher(destination);
		view.forward(request, response);
	}

	private String getColorFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object color = session.getAttribute("color");
		if (color != null) {
			return (String) color;
		} else {
			return null;
		}
	}

	private String getUseridFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		if (user != null) {
			return ((Person) user).getUserid();
		} else {
			return null;
		}
	}

}
