package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Articolo;
import model.bean.Seguace;
import model.manager.ArticoloManagement;
import model.manager.SeguiManagement;
import storage.DriverManagerConnectionPool;
import utils.Utils;

@WebServlet("/HomepageArticoliControl")
public class HomepageArticoliControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String notFoundPage = "notfound.jsp";

    public HomepageArticoliControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailSession = Utils.checkLogin(request.getSession(), request.getCookies());
		if(emailSession == null || !emailSession.subSequence(0, 1).equals("u"))
		{
			// invocata servlet senza essersi autenticato
			// reindirizzo alla pagina not_found
			response.sendRedirect(notFoundPage);
			return;
		}
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		SeguiManagement seMan=new SeguiManagement(dm);
		ArticoloManagement artMan=new ArticoloManagement(dm);
		String email=emailSession.substring(1);
		ArrayList<Articolo> articoli=new ArrayList<Articolo>();
		try {
			ArrayList<Seguace> seguaci=(ArrayList<Seguace>)seMan.doRetrieveAll(email);
			for(Seguace e:seguaci) {
				ArrayList<Articolo> results=(ArrayList<Articolo>)artMan.doRetrieveAll("a:"+e.getAutore());
				for(Articolo a:results) {
					if(a.getStato().equals("Pubblicato"))
						articoli.add(a);
				}
			}
			request.setAttribute("articoli", articoli);
			request.getRequestDispatcher("homepage.jsp").forward(request, response);
			return;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}