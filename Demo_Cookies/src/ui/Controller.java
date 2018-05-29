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

		if (action == null) {
			request.setAttribute("languages", dictionary.getSupportedLanguages());
		} else if (action.equals("setlanguage")) {
			String language = getLanguage(request, response);
			request.setAttribute("greeting", dictionary.getGreeting(language));
			destination = "welcome.jsp";
		} else if (action.equals("checklanguage")) {
			request.setAttribute("language", getLanguageFromCookie(request));
			destination = "language.jsp";
		}

		request.getRequestDispatcher(destination).forward(request, response);
	}

	private String getLanguageFromCookie(HttpServletRequest request) {
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("language")) {
				return cookie.getValue();
			}
		}
		return "nl";
	}

	private void rememberLanguage(HttpServletResponse response, String language) {
		response.addCookie(new Cookie("language", language));
	}

	private String getLanguage(HttpServletRequest request, HttpServletResponse response) {
		String language = request.getParameter("language");
		rememberLanguage(response, language);
		return language;
	}

}
