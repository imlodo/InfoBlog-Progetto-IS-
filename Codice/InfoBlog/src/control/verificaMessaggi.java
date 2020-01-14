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

import com.google.gson.Gson;

import model.bean.Conversazione;
import model.bean.Messagio;
import model.manager.ConversazioniManagment;
import model.manager.MessaggioManagment;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class verificaMessaggi
 */
@WebServlet("/verificaMessaggi")
public class verificaMessaggi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public verificaMessaggi() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email=request.getParameter("email");
		String tipologia=request.getParameter("tipologia");
		boolean flag=false;
		ArrayList<String> contatti;
		MessaggioManagment DAOMessaggio= new MessaggioManagment(new DriverManagerConnectionPool());
		Gson g = new Gson();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		if(tipologia.equals("risposta"))
			contatti=ConversazioniManagment.getUtenti("R:"+email);
		else
			contatti=ConversazioniManagment.getUtenti("M:"+email);

		try 
		{
			for(int i=0;i<contatti.size();i++)
			{
				ArrayList<Messagio> messaggi=(ArrayList<Messagio>)DAOMessaggio.doRetrieveAll(email+" "+contatti.get(i));
				if(messaggi.get(messaggi.size()-1).getStato().equals("inviato") && !messaggi.get(messaggi.size()-1).getMittente().equals(email))
				{
					flag=true;
					break;
				}
			}
			response.getWriter().write(g.toJson(String.valueOf(flag)));
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}

}
