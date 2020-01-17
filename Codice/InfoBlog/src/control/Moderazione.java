package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Articolo;
import model.manager.ArticoloManagement;
import model.manager.ModeratoreManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class Moderazione
 */
@WebServlet("/Moderazione")
public class Moderazione extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Moderazione() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(request.getSession().getAttribute("Moderatore")!=null)
		{
			String emailModeratore=(String)request.getSession().getAttribute("Moderatore");
			String azione=request.getParameter("azione");
			String idArticolo=request.getParameter("idArticolo");
			ArticoloManagement DAOArticolo=new ArticoloManagement(new DriverManagerConnectionPool());
			ModeratoreManagement DAOModeratore=new ModeratoreManagement(new DriverManagerConnectionPool());
			Articolo article;
			String url="pannelloNotifiche.jsp";

			if(idArticolo!=null && azione!=null)
			{
				try
				{
					article=DAOArticolo.doRetrieveByKey(idArticolo);
					if(article!=null)
					{
						if(azione.equals("accetta"))
						{
							article.setModeratore(DAOModeratore.doRetrieveByKey(emailModeratore));
							article.setStato("Pubblicato");
							DAOArticolo.doUpdate(article);
							request.setAttribute("compilazioneIniziale","La moderazione ha avuto esito positivo.Il suo articolo è stato pubblicato");
						}
						else
							if(azione.equals("rifiuta"))
							{
								article.setModeratore(DAOModeratore.doRetrieveByKey(emailModeratore));
								article.setStato("respinto");
								DAOArticolo.doUpdate(article);
								request.setAttribute("compilazioneIniziale","Articolo rifiutato:");
							}
							else
							{
								request.setAttribute("Errore","azione non valida");
								RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
								requestDispatcher.forward(request, response);
								return;
							}
						request.setAttribute("emailAutore", article.getAutore().getEmail());
						RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
						requestDispatcher.forward(request, response);
					}
					else
					{
						request.setAttribute("Errore","Si è verificato un errore");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
						requestDispatcher.forward(request, response);
					}
				}
				catch (NumberFormatException e) 
				{
					request.setAttribute("Errore","Formato non valido");
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
					requestDispatcher.forward(request, response);

				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			request.setAttribute("Errore","Accesso negato");
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
			requestDispatcher.forward(request, response);
		}
	}
}
