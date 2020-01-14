package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
 * Servlet implementation class UpdateMessage
 */
@WebServlet("/UpdateMessage")
public class UpdateMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMessage() {
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
		
		String testo=request.getParameter("text");
		String proprietario=request.getParameter("prop");
		String destinatario=request.getParameter("dex");
		
		mex.setContenuto(testo);
		mex.setMittente(proprietario);
		mex.setDestinatario(destinatario);
		mex.setData(LocalDateTime.now());
		if(request.getSession().getAttribute("Utente")!=null)
			mex.setTipologia("messaggio");
		else
			mex.setTipologia("risposta");
		try
		{
			DAOMessaggio.doSave(mex);
			ArrayList<Messagio> newMex=new ArrayList<Messagio>();
			newMex=(ArrayList<Messagio>) DAOMessaggio.doRetrieveAll(mex.getMittente()+" "+mex.getDestinatario());
			System.out.println(g.toJson(newMex));
			response.getWriter().write(g.toJson(newMex));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

}
