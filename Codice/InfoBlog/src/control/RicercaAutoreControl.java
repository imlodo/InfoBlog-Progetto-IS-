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

import model.bean.Autore;
import model.manager.AutoreManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class RicercaAutoreControl
 */
@WebServlet("/RicercaAutoreControl")
public class RicercaAutoreControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RicercaAutoreControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		AutoreManagement autMan=new AutoreManagement(dm);
		
		try {
			System.out.println("Ricerca Autore:"+request.getParameter("text"));
			ArrayList<Autore> autoriList=(ArrayList<Autore>)autMan.doRetrieveByUsernameAutore(request.getParameter("text"));
			for(Autore a:autoriList)
				System.out.println(a.getUsername());
			String autori=new Gson().toJson(autoriList);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(autori);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
