package controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.service.CountryService;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CountryService service;

	private ControllerFactory controllerFactory = new ControllerFactory();

	public Controller() {
		super();
	}
	
	public void init() throws ServletException {
   		super.init();
   		ServletContext context = getServletContext();
   		
   		Properties properties = new Properties();
   		properties.setProperty("user", context.getInitParameter("user"));
   		properties.setProperty("password", context.getInitParameter("password") );
   		properties.setProperty("ssl", context.getInitParameter("ssl"));
   		properties.setProperty("sslfactory", context.getInitParameter("sslfactory"));
   		properties.setProperty("url", context.getInitParameter("url"));
   		properties.setProperty("currentSchema", context.getInitParameter("currentSchema"));
   		
   		service = new CountryService(properties);
   }


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String destination = "index.jsp";
        if (action != null) {
        		RequestHandler handler;
        		handler = controllerFactory.getController(action, service);
			destination = handler.handleRequest(request, response);
        }
        RequestDispatcher view = request.getRequestDispatcher(destination);
        view.forward(request, response);
	}
		

}
