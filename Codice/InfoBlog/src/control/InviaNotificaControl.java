package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Notifica;
import model.manager.NotificaManagement;
import storage.DriverManagerConnectionPool;
import utils.Utils;

/**
 * Servlet implementation class NotificaControl
 */
@WebServlet("/InviaNotificaControl")
public class InviaNotificaControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InviaNotificaControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Controllo se l'utente è loggato ed è un autore
		String emailSession = Utils.checkLogin(request.getSession(), request.getCookies());
		if(emailSession == null || emailSession.subSequence(0, 1).equals("u"))
		{
			// invocata servlet senza essersi autenticato
			// reindirizzo alla pagina not_found
			request.setAttribute("errore", "permessoNegato");
			response.sendRedirect("notfound.jsp");
			return;
		}
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		NotificaManagement notMan=new NotificaManagement(dm);

		try {
			Notifica notifica=new Notifica();
			notifica.setContenuto(request.getParameter("contenuto"));
			notifica.setModeratoreEmail(request.getParameter("emailModeratore"));
			notifica.setAutoreEmail(request.getParameter("emailAutore"));

			notMan.doSave(notifica);
			response.sendRedirect("homepage.jsp");
		}catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("errore", "errorDB");
			response.sendRedirect("notfound.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}