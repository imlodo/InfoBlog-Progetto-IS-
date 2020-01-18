package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Messagio;
import model.manager.MessaggioManagment;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class InviaMessaggioControl
 */
@WebServlet("/InviaMessaggioControl")
public class InviaMessaggioControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InviaMessaggioControl() {
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
		if(request.getSession().getAttribute("Utente")!=null)
		{
			String email=request.getParameter("autore");
			String me=request.getParameter("email");
			String contenuto=request.getParameter("contenuto");
			if(!utils.Utils.checkEmail(me))
			{
				request.setAttribute("errore", "Accesso negato");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
			if(!utils.Utils.checkEmail(email))
			{
				request.setAttribute("errore", "Accesso negato");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
			if(!request.getSession().getAttribute("Utente").equals(me))
			{
				request.setAttribute("errore", "Accesso negato");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
			
			
			String regexp = "[a-zA-Z0-9 #&<>~;$^%{}?][^~^]{1,700}$";
			Pattern pt = Pattern.compile(regexp);
			Matcher mt = pt.matcher(contenuto);
			boolean resultmatchContenuto = mt.matches();
			
			if(!resultmatchContenuto)
			{
				request.setAttribute("contenuto",contenuto);
				request.setAttribute("emailAutore", email);
				request.setAttribute("ErroreFormato", "contunuto formato errato");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("ModuloContatta.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
			
			MessaggioManagment DAOMessaggio=new MessaggioManagment(new DriverManagerConnectionPool());
			Messagio mex=new Messagio();
			
			mex.setContenuto(contenuto);
			mex.setData(LocalDateTime.now());
			mex.setDestinatario(email);
			mex.setMittente(me);
			mex.setTipologia("messaggio");
			try
			{
				DAOMessaggio.doSave(mex);
				request.setAttribute("Successo", "Messaggio inviato");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("PageAutoreServlet?email="+email);
				requestDispatcher.forward(request, response);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			request.setAttribute("errore", "Accesso negato");
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
			requestDispatcher.forward(request, response);
		}

	}
}

