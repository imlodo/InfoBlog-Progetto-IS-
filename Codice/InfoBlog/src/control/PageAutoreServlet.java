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

import model.bean.Articolo;
import model.bean.Autore;
import model.bean.Commento;
import model.bean.Rating;
import model.manager.ArticoloManagement;
import model.manager.AutoreManagement;
import model.manager.CommentoManagment;
import model.manager.RatingManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class PageAutoreServlet
 */
@WebServlet("/PageAutoreServlet")
public class PageAutoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PageAutoreServlet() {
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
			request.setAttribute("errore", "Accesso negato");
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
			requestDispatcher.forward(request, response);
			return;
		}
		else
			if(request.getSession().getAttribute("Utente")!=null || request.getSession().getAttribute("Autore")!=null || request.getSession().getAttribute("Utente")==null) 
			{
				String email=request.getParameter("email");
				if(!utils.Utils.checkEmail(email))
				{
					request.setAttribute("errore", "Errore, email errata");
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
					requestDispatcher.forward(request, response);
				}
				AutoreManagement DAOAutore=new AutoreManagement(new DriverManagerConnectionPool());
				ArticoloManagement DAOArticolo=new ArticoloManagement(new DriverManagerConnectionPool());
				ArrayList<Articolo> articoli=null;
				Autore autore;
				CommentoManagment DAOCommento=new CommentoManagment(new DriverManagerConnectionPool());
				ArrayList<Commento> commenti;
				ArrayList<ArrayList<Commento>> commentiArticoli= new ArrayList<ArrayList<Commento>>();
				ArrayList<Rating> rating;
				ArrayList<ArrayList<Rating>> ratingCollettivo=new ArrayList<ArrayList<Rating>>();
				RatingManagement DAORating=new RatingManagement(new DriverManagerConnectionPool());

				try
				{
					autore=DAOAutore.doRetrieveByKey(email);
					if(autore==null)
					{
						request.setAttribute("errore", "Autore non presente");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
						requestDispatcher.forward(request, response);
						return;
					}
					else
					{
						request.setAttribute("autore",autore);
						
						articoli=(ArrayList<Articolo>) DAOArticolo.doRetrieveAll("a:"+autore.getEmail());
						if(articoli!=null && articoli.size()>0)
						{
							request.setAttribute("articoli",articoli);
							for(int i=0;i<articoli.size();i++)
							{
								commenti=(ArrayList<Commento>) DAOCommento.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
								commentiArticoli.add(commenti);
								rating=(ArrayList<Rating>) DAORating.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
								ratingCollettivo.add(rating);
							}
							request.setAttribute("commenti",commentiArticoli);
							request.setAttribute("rating",ratingCollettivo);
						}
						else
							request.setAttribute("Vuoto","Non ci sono articoli");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("PaginaAutore.jsp");
						requestDispatcher.forward(request, response);
					}
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}

	}

}
