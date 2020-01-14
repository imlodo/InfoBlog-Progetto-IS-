package control;

import java.io.IOException;
import java.sql.SQLException;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Gson g = new Gson();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        MessaggioManagment DAOMessaggio=new MessaggioManagment(new DriverManagerConnectionPool());
		Messagio mex=new Messagio();
        
		
		int codiceMessaggio=Integer.parseInt(request.getParameter("id"));
		if(request.getSession().getAttribute("Utente")!=null)
			mex.setTipologia("risposta");
		else
			mex.setTipologia("messaggio");
		mex.setIdMessaggio(codiceMessaggio);
		try 
		{
			DAOMessaggio.doUpdate(mex);
			response.getWriter().write(g.toJson("cambiamentoEffettuato"));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

}
