package control;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import model.bean.Allegato;
import model.bean.Articolo;
import model.manager.AllegatoManagement;
import model.manager.ArticoloManagement;
import storage.DriverManagerConnectionPool;
import utils.Utils;

/**
 * Servlet implementation class CancellaAllegatoControl
 */
@WebServlet("/CancellaAllegatoControl")
public class CancellaAllegatoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String notFoundPage = "notfound.jsp";
    
    public CancellaAllegatoControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String email = Utils.checkLogin(request.getSession(), request.getCookies());
		if(email != null)
		{
			if(email.substring(0,1).equals("a")){}
			else
			{
				//Errore 
				response.sendRedirect(notFoundPage);
				return;
			}
		}
		else
		{
			//Errore 
			response.sendRedirect(notFoundPage);
			return;
		}
		
		//Controllare che l'email dell'autore in sessione corrisponde a chi ha creato l'articolo, al quale corrisponde l'allegato
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		AllegatoManagement manAll=new AllegatoManagement(dm);
		Allegato allegato = (Allegato) request.getAttribute("allegato");
		try {
			if(allegato == null)
			{
				String id = request.getParameter("id");
				if(id !=  null)
				{	
					String pathContext = "C:\\users\\public";
					String path = request.getParameter("path");
					System.out.println(pathContext);
					allegato=new Allegato(pathContext+"\\"+path,
							Integer.parseInt(id));
				}
				else
				{
					request.setAttribute("errore", "idError");
					//Errore 
					response.sendRedirect(notFoundPage);
					return;
				}
			}
			//Controllo che l'allegato fa parte di un articolo scritto dall'autore loggato.
			ArticoloManagement articoloDM = new ArticoloManagement(dm);
			ArrayList<Articolo> articoli = new ArrayList<Articolo>();
			articoli = (ArrayList<Articolo>) articoloDM.doRetrieveAll("a:"+email.substring(1));
			boolean found = false;
			for(Articolo a : articoli)
			{
				if(a.getId() == allegato.getId())
					found = true;
			}
			if(!found)
			{
				request.setAttribute("found", found);
				//Errore si sta provando a cancellare un allegato non proprio
				response.sendRedirect(notFoundPage);
				return;
			}
//			System.out.println("Path da cancellare: "+allegato.getPercorsoFile());
			
			File file=new File(allegato.getPercorsoFile());
			
			Gson gson=new Gson();
			
			response.setContentType("application/json");

			manAll.doDelete(allegato);
			if(file.delete()) {
//				System.out.println("cancellato"+file.getAbsolutePath());
				String result=gson.toJson("si");
				request.setAttribute("result", "si");
				response.getWriter().print(result);
//				System.out.println(result);
			}
			else {
//				System.out.println("non cancellato"+file.getAbsolutePath());
				String result=gson.toJson("no");
				request.setAttribute("result", "no");
				response.getWriter().print(result);
			}
		}catch (SQLException e) {
			request.setAttribute("errore", e.getMessage());
			return;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
