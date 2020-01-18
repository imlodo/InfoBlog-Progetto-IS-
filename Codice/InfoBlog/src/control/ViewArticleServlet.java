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
import model.bean.Commento;
import model.bean.Rating;
import model.manager.ArticoloManagement;
import model.manager.CommentoManagment;
import model.manager.RatingManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class ViewArticleServlet
 */
@WebServlet("/ViewArticleServlet")
public class ViewArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewArticleServlet() {
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
		String id=request.getParameter("id");
		ArticoloManagement DAOArticolo=new ArticoloManagement(new DriverManagerConnectionPool());
		Articolo articolo=null;
		String url="SingleArticle.jsp";
		String titolo=request.getParameter("Titolo");
		CommentoManagment DAOCommento=new CommentoManagment(new DriverManagerConnectionPool());
		ArrayList<Commento> commenti;
		ArrayList<Rating> ratingArticolo;
		Rating rating;
		RatingManagement DAORating=new RatingManagement(new DriverManagerConnectionPool());

		if(id!=null && titolo!=null)
		{
			if(utils.Utils.checkTitolo(titolo))
			{
				
				try
				{	
					Integer.parseInt(id);
					articolo=(Articolo)DAOArticolo.doRetrieveByKey(id);
					if(articolo==null)
					{
						request.setAttribute("Errore","Paramentri errati");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
						requestDispatcher.forward(request, response);
						return;
					}
					else
					{
						if(!articolo.getTitolo().equals(titolo))
						{
							request.setAttribute("Errore","Paramentro titolo errato");
							RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
							requestDispatcher.forward(request, response);
							return;
						}
						else 
						{
							request.setAttribute("articolo",articolo);

							commenti=(ArrayList<Commento>) DAOCommento.doRetrieveAll(String.valueOf(articolo.getId()));
							if(commenti.size()==0)
								request.setAttribute("Vuoto", "non ci sono commenti");
							else
								request.setAttribute("commenti", commenti);
							if(request.getSession().getAttribute("Utente")!=null)
							{
								rating=DAORating.doRetrieveByKey(articolo.getId()+" "+request.getSession().getAttribute("Utente"));
								ratingArticolo=(ArrayList<Rating>) DAORating.doRetrieveAll(String.valueOf(articolo.getId()));
								if(rating!=null)
									request.setAttribute("ratingUtente",rating);
								else
									request.setAttribute("VotoRatingUtente","non hai inserito rating");
								if(ratingArticolo.size()>0)
									request.setAttribute("RatingArticolo", ratingArticolo);
								else
									request.setAttribute("VotoRatingArticolo","nessun rating");
							}
							if(request.getSession().getAttribute("Autore")!=null)
							{
								ratingArticolo=(ArrayList<Rating>) DAORating.doRetrieveAll(String.valueOf(articolo.getId()));
								if(ratingArticolo!=null && ratingArticolo.size()>0)
									request.setAttribute("RatingArticolo", ratingArticolo);
								else
									request.setAttribute("VotoRatingArticolo","nessun rating");
							}
							if(request.getSession().getAttribute("Autore")==null)
							{
								ratingArticolo=(ArrayList<Rating>) DAORating.doRetrieveAll(String.valueOf(articolo.getId()));
								if(ratingArticolo.size()>0)
									request.setAttribute("RatingArticolo", ratingArticolo);
								else
									request.setAttribute("VotoRatingArticolo","nessun rating");
							}
							RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
							requestDispatcher.forward(request, response);
						}
					}
				}
				catch (NumberFormatException e) 
				{
					request.setAttribute("Errore","id non valido");
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
					requestDispatcher.forward(request, response);
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
			}
			else
			{
				request.setAttribute("Errore","titolo non valido");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
				requestDispatcher.forward(request, response);
			}
		}

	}
}
