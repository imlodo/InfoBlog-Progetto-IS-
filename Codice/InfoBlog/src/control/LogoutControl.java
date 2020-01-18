package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutControl
 */
@WebServlet("/LogoutControl")
public class LogoutControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String homePage = "homepage.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		///invalidate the session if exists
		HttpSession session = request.getSession(false);
		synchronized(session) 
		{
			//response.setContentType("text/html");
			Cookie[] cookies = request.getCookies();
			if(cookies != null)
			{
				for(Cookie cookie : cookies)
				{
					if(cookie.getName().equals("JSESSIONID"))
					{
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
					if(cookie.getName().equals("Utente"))
					{
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
					if(cookie.getName().equals("Autore"))
					{
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
					if(cookie.getName().equals("Moderatore"))
					{
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
					if(cookie.getName().equals("Username"))
					{
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
//			System.out.println("User="+session.getAttribute("Username"));
			if(session != null)
			{
				session.invalidate();
			}
			//no encoding because we have invalidated the session
			response.sendRedirect(homePage);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
