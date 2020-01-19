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
import model.bean.Rating;
import model.bean.Utente;
import model.manager.ArticoloManagement;
import model.manager.RatingManagement;
import storage.DriverManagerConnectionPool;
import utils.Utils;

/**
 * Servlet implementation class RatingControl
 */
@WebServlet("/RatingControl")
public class RatingControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RatingControl() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String emailSession = Utils.checkLogin(request.getSession(), request.getCookies());
		RequestDispatcher dispatcher = null;
		//Controllo se è un utente che sta facendo il rating
		if(emailSession == null || !emailSession.substring(0,1).equalsIgnoreCase("u"))
		{
			//redirect a notFound
			dispatcher = request.getRequestDispatcher("notfound.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		String idArticolo = request.getParameter("idArticolo");
		String numeroStelle = request.getParameter("numeroStelle");
		if(!Utils.checkNumeroStelle(numeroStelle))
		{
			request.setAttribute("errore","numeroStelle errato");
			dispatcher = request.getRequestDispatcher("SingleArticle.jsp");
			dispatcher.forward(request, response);
			return;
		}
		if(!Utils.isNumeric(idArticolo))
		{
			//redirect a notFound
			request.setAttribute("errore","campi errati");
			dispatcher = request.getRequestDispatcher("notfound.jsp");
			dispatcher.forward(request, response);
			return;
		}
		//Qui controllo se il commento esiste, se esiste faccio l'update, altrimenti faccio il save.
		DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
		RatingManagement ratingDM = new RatingManagement(pool);
		ArticoloManagement article=new ArticoloManagement(pool);
		
		
		try
		{
			Articolo articolo=article.doRetrieveByKey(idArticolo);
			Rating test = ratingDM.doRetrieveByKey(idArticolo+" "+emailSession.substring(1));
			if(test != null)
			{
				float numeroStelleF = Float.parseFloat(numeroStelle);
				if(test.getNumeroStelle() == numeroStelleF)
				{
					request.setAttribute("errore","nessuna modifica");
					dispatcher = request.getRequestDispatcher("ViewArticleServlet?id="+idArticolo+"&Titolo="+articolo.getTitolo());
					dispatcher.forward(request, response);
					return;
				}
				//Faccio l'update
				test.setNumeroStelle(numeroStelleF);
				Articolo tmp = new Articolo();
				tmp.setId(Integer.parseInt(idArticolo));
				test.setArticolo(tmp);
				test.setUtente(new Utente(emailSession.substring(1),"","","",""));
				ratingDM.doUpdate(test);
				request.setAttribute("successo","rating modificato");
				dispatcher = request.getRequestDispatcher("ViewArticleServlet?id="+idArticolo+"&Titolo="+articolo.getTitolo());
				dispatcher.forward(request, response);
			}
			else
			{
				//Faccio il save
				Rating nuovo = new Rating();
				nuovo.setUtente(new Utente(emailSession.substring(1),"","","",""));
				nuovo.setNumeroStelle(Float.parseFloat(numeroStelle));
				Articolo tmp = new Articolo();
				tmp.setId(Integer.parseInt(idArticolo));
				nuovo.setArticolo(tmp);
				ratingDM.doSave(nuovo);
				request.setAttribute("successo","rating inserito");
				dispatcher = request.getRequestDispatcher("ViewArticleServlet?id="+idArticolo+"&Titolo="+articolo.getTitolo());
				dispatcher.forward(request, response);
			}
		}
		catch (SQLException e)
		{
			
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
