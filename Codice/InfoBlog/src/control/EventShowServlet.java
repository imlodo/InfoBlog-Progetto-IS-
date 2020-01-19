package control;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.bean.Evento;
import model.manager.EventoManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class EventShowServlet
 */
@WebServlet("/EventShowServlet")
public class EventShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public EventShowServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EventoManagement DAOEvento=new EventoManagement(new DriverManagerConnectionPool());
		HttpSession ssn=request.getSession();

		synchronized (ssn)
		{
			try
			{
				if(ssn.getAttribute("Autore")!=null)
				{
					ArrayList<Evento> eventi=(ArrayList<Evento>) DAOEvento.doRetrieveAll((String)ssn.getAttribute("Autore"));
					if(eventi.size()>0)
					{
						request.setAttribute("eventi",eventi);
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("ShowEvent.jsp");
						requestDispatcher.forward(request,response);
					}
					else
					{
						request.setAttribute("NoEventi","Non hai organizzato eventi");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("ShowEvent.jsp");
						requestDispatcher.forward(request,response);
					}
				}
				else
					if(ssn.getAttribute("Moderatore")!=null)
					{
						request.setAttribute("Accesso negato", "Non hai accesso a questa funzionalità");
						response.sendRedirect("notfound.jsp");
					}
					else
						if((ssn.getAttribute("Utente")!=null | ssn.getAttribute("Utente")==null) && ssn.getAttribute("Moderatore")==null)
						{
							ArrayList<Evento> eventi=(ArrayList<Evento>) DAOEvento.doRetrieveAll("");
							if(eventi.size()>0)
							{
								request.setAttribute("eventi",eventi);
								RequestDispatcher requestDispatcher=request.getRequestDispatcher("ShowEvent.jsp");
								requestDispatcher.forward(request,response);
							}
							else
							{
								request.setAttribute("NoEventi","Non hai organizzato eventi");
								RequestDispatcher requestDispatcher=request.getRequestDispatcher("ShowEvent.jsp");
								requestDispatcher.forward(request,response);
							}
						}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}
