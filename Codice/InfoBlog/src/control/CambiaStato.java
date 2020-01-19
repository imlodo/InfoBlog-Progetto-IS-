package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import model.bean.Messagio;
import model.manager.MessaggioManagment;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class CambiaStato
 */
@WebServlet("/CambiaStato")
public class CambiaStato extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CambiaStato() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("Moderatore")==null) 
		{
			Gson g = new Gson();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			MessaggioManagment DAOMessaggio=new MessaggioManagment(new DriverManagerConnectionPool());
			Messagio mex=new Messagio();
			int codiceMessaggio;
			
			try 
			{
				codiceMessaggio=Integer.parseInt(request.getParameter("id"));
			}
			catch (NumberFormatException e)
			{
				request.setAttribute("errore", "informazioni errate");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
				requestDispatcher.forward(request, response);
				return;
			}

			if(request.getSession().getAttribute("Utente")!=null)
				mex.setTipologia("risposta");
			else
				mex.setTipologia("messaggio");
			mex.setIdMessaggio(codiceMessaggio);
			try 
			{
				DAOMessaggio.doUpdate(mex);
				request.setAttribute("cambiamento", "successo");
				response.getWriter().write(g.toJson("cambiamentoEffettuato"));
			} 
			catch (SQLException e) 
			{
				request.setAttribute("errore", "Si è verificato un problema SQL");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
		}
		else
		{
			request.setAttribute("errore", "Accesso negato");
			response.sendRedirect("notfound.jsp");
		}
	}
}
