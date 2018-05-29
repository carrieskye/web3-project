package ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Dictionary;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Dictionary dictionary = new Dictionary();

	public Controller() {
		super();
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
		String language = getLanguage(request, response);

		String action = request.getParameter("action");
		String destination;
		
		if (action == null) {
			destination = showIndexPage(request);
		} else if (action.equals("checklanguage")) {
			destination = showLanguage(request, language);
		} else if (action.equals("setlanguage")) {
			destination = showGreeting(request, language);
		} else {
			destination = showIndexPage(request);
		}
		
		request.getRequestDispatcher(destination).forward(request, response);
	}
	
	private String getLanguage(HttpServletRequest request,
			HttpServletResponse response) {
		String language = request.getParameter("language");

		if (language == null) {
			language = getLanguageFromCookie(request);
			if (language == null) {
				rememberLanguage(response, "nl");
			}
		} else {
			rememberLanguage(response, language);
		}
		return language;
	}

	private String getLanguageFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("language")) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	private void rememberLanguage(HttpServletResponse response, String language) {
		Cookie cookie = new Cookie("language", language);
		response.addCookie(cookie);
	}

	private String showLanguage(HttpServletRequest request, String language) {
		request.setAttribute("language", language);
		return "language.jsp";
	}

	private String showGreeting(HttpServletRequest request, String language) {
		String greeting = dictionary.getGreeting(language);
		if (greeting == null) {
			greeting = "Hallo!";
		}
		request.setAttribute("greeting", greeting);
		return "welcome.jsp";
	}

	private String showIndexPage(HttpServletRequest request) {
		request.setAttribute("languages", dictionary.getSupportedLanguages());
		return "index.jsp";
	}

}
