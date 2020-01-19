package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Autore;
import model.bean.Moderatore;
import model.bean.Utente;
import model.manager.AutoreManagement;
import model.manager.ModeratoreManagement;
import model.manager.UserManagement;
import storage.DriverManagerConnectionPool;
import utils.Utils;

/**
 * Servlet implementation class VisualizzaDatiPersonaliControl
 */
@WebServlet("/VisualizzaDatiPersonaliControl")
public class VisualizzaDatiPersonaliControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisualizzaDatiPersonaliControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		String email = Utils.checkLogin(session, cookies);
		if(email == null)
		{
			// invocata servlet senza essersi autenticato
			// reindirizzo alla pagina not_found
			response.sendRedirect("notfound.jsp");
			return;
		}
		
		DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
		//Verifico chi è loggato
		try
		{
			Utente user = null;
			Autore autore = null;
			Moderatore mod = null;
			if(email.substring(0, 1).equals("u"))
			{	
				UserManagement manager = new UserManagement(pool);
				user = manager.doRetrieveByKey(email.substring(1));
				request.setAttribute("email", email);
				request.setAttribute("nome", user.getNome());
				request.setAttribute("cognome", user.getCognome());
				request.setAttribute("username", user.getUsername());
				request.setAttribute("password", user.getPassword());
			}
			if(email.substring(0, 1).equals("a"))
			{
				AutoreManagement manager = new AutoreManagement(pool);
				autore = manager.doRetrieveByKey(email.substring(1));
				request.setAttribute("email", email);
				request.setAttribute("nome", autore.getNome());
				request.setAttribute("cognome", autore.getCognome());
				request.setAttribute("username", autore.getUsername());
				request.setAttribute("password", autore.getPassword());
			}
			if(email.substring(0, 1).equals("m"))
			{
				ModeratoreManagement manager = new ModeratoreManagement(pool);
				mod = manager.doRetrieveByKey(email.substring(1));
				request.setAttribute("email", email);
				request.setAttribute("nome", mod.getNome());
				request.setAttribute("cognome", mod.getCognome());
				request.setAttribute("username", mod.getUsername());
				request.setAttribute("password", mod.getPassword());
				request.setAttribute("categoria", mod.getCategoria_moderazione());
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
			dispatcher.forward(request, response);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
