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
import model.manager.AllegatoManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class AllegatiArticoloControl
 */
@WebServlet("/AllegatiArticoloControl")
public class AllegatiArticoloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AllegatiArticoloControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		AllegatoManagement allMan=new AllegatoManagement(dm);
	
		try {
			int id;
			if(request.getParameter("id")==null)
					return;
			else
				id=Integer.parseInt(request.getParameter("id"));
			ArrayList<Allegato> allegati=(ArrayList<Allegato>)allMan.doRetrieveByID(id);
			ArrayList<Allegato> nomi=new ArrayList<Allegato>();
			for(Allegato i:allegati) {
				File file=new File(i.getPercorsoFile());
				nomi.add(new Allegato(file.getName(),i.getId()));
			}
			String jsonAllegati=new Gson().toJson(nomi);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(jsonAllegati);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			System.err.println("L'id dell'articolo è arrivato null");
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
