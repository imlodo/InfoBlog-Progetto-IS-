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
import model.bean.Commento;
import model.bean.Utente;
import model.manager.CommentoManagment;
import storage.DriverManagerConnectionPool;
import utils.Utils;

/**
 * Servlet implementation class CommentoControl
 */
@WebServlet("/CommentoControl")
public class CommentoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentoControl(){super();}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String emailSession = Utils.checkLogin(request.getSession(), request.getCookies());
		RequestDispatcher dispatcher = null;
		//Controllo se è un utente che sta commentando
		if(!emailSession.substring(0,1).equalsIgnoreCase("u"))
		{
			//redirect a notFound
			dispatcher = request.getRequestDispatcher("notfound.jsp");
			dispatcher.forward(request, response);
			return;
		}
		String idArticolo = request.getParameter("idArticolo");
		String commento = request.getParameter("commento");
		if(!Utils.checkCommento(commento))
		{
			request.setAttribute("errore","contenuto mancante");
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
		CommentoManagment commentoDM = new CommentoManagment(pool);
		try
		{
			Commento test = commentoDM.doRetrieveByKey(idArticolo+" "+emailSession.substring(1));
			if(test != null)
			{
				if(test.getContenuto().contentEquals(commento))
				{
					request.setAttribute("errore","nessuna modifica");
					dispatcher = request.getRequestDispatcher("SingleArticle.jsp");
					dispatcher.forward(request, response);
					return;
				}
				test.setContenuto(commento);
				commentoDM.doUpdate(test);
				request.setAttribute("successo","commento modificato");
				dispatcher = request.getRequestDispatcher("SingleArticle.jsp");
				dispatcher.forward(request, response);
			}
			else
			{
				Commento nuovo = new Commento();
				nuovo.setUtente(new Utente(emailSession.substring(1),"","","",""));
				nuovo.setContenuto(commento);
				Articolo tmp = new Articolo();
				tmp.setId(Integer.parseInt(idArticolo));
				nuovo.setArticolo(tmp);
				commentoDM.doSave(nuovo);
				request.setAttribute("successo","commento pubblicato");
				dispatcher = request.getRequestDispatcher("SingleArticle.jsp");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
