package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.bean.Articolo;
import model.bean.Commento;
import model.bean.Rating;
import model.manager.ArticoloManagement;
import model.manager.CommentoManagment;
import model.manager.RatingManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class RicercaArticoloControl
 */
@WebServlet("/RicercaArticoloControl")
public class RicercaArticoloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RicercaArticoloControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		ArticoloManagement autMan=new ArticoloManagement(dm);
		CommentoManagment DAOCommento=new CommentoManagment(dm);
		RatingManagement DAORating=new RatingManagement(dm); 
		ArrayList<Commento> commenti=new ArrayList<Commento>();
		ArrayList<Rating> rating=new ArrayList<Rating>();
		
		ArrayList<ArrayList<Commento>> commentiArticoli=new ArrayList<ArrayList<Commento>>();
		ArrayList<ArrayList<Rating>> ratingCollettivo=new ArrayList<ArrayList<Rating>>();
		
		try {
			System.out.println("Ricerca Autore:"+request.getParameter("text"));
			ArrayList<Articolo> articoli=(ArrayList<Articolo>)autMan.doRetrieveByTitolo(request.getParameter("titolo"));
			for(Articolo a:articoli)
				System.out.println(a.getTitolo());
			for(int i=0;i<articoli.size();i++)
            {
                commenti=(ArrayList<Commento>) DAOCommento.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
                commentiArticoli.add(commenti);
                rating=(ArrayList<Rating>) DAORating.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
                ratingCollettivo.add(rating);
            }
            String jsonArticoli=new Gson().toJson(articoli);
            String jsonCommenti=new Gson().toJson(commentiArticoli);
            String jsonRating=new Gson().toJson(ratingCollettivo);
            
            String tripleJsons="["+jsonArticoli+","+jsonCommenti+","+jsonRating+"]";
            
            System.out.println(jsonArticoli);
            System.out.println(jsonRating);
            System.out.println(jsonCommenti);
            
			response.setContentType("application/json"); 
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(tripleJsons);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}