package ui.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
   		properties.setProperty("password", context.getInitParameter("password") );
   		properties.setProperty("ssl", context.getInitParameter("ssl"));
   		properties.setProperty("sslfactory", context.getInitParameter("sslfactory"));
   		properties.setProperty("url", context.getInitParameter("url"));
   		
   		service = new ShopService(properties);
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
        request.setAttribute("color", getColorFromCookie(request));
        request.setAttribute("userid", getUseridFromCookie(request));
        
        if (action != null) {
        		RequestHandler handler;
        		handler = controllerFactory.getController(action, service);
			destination = handler.handleRequest(request, response);
        }
        RequestDispatcher view = request.getRequestDispatcher(destination);
        view.forward(request, response);
	}
	
	private String getColorFromCookie(HttpServletRequest request) {
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("color")) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	private String getUseridFromCookie(HttpServletRequest request) {
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("userid")) {
				return cookie.getValue();
			}
		}
		return null;
	}
		

}
