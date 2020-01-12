package control;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

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
 * Servlet implementation class CancellaAllegatoControl
 */
@WebServlet("/CancellaAllegatoControl")
public class CancellaAllegatoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CancellaAllegatoControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		AllegatoManagement manAll=new AllegatoManagement(dm);
		
		try {
			Allegato allegato=new Allegato(getServletContext().getInitParameter("allegati")+"\\"+request.getParameter("path"),
					Integer.parseInt(request.getParameter("id")));
			
			System.out.println("Path da cancellare: "+allegato.getPercorsoFile());
			
			File file=new File(allegato.getPercorsoFile());
			
			Gson gson=new Gson();
			
			response.setContentType("application/json");
			
			if(file.delete()) {
				System.out.println("cancellato"+file.getAbsolutePath());
				String result=gson.toJson("si");
				response.getWriter().print(result);
				manAll.doDelete(allegato);
				System.out.println(result);
			}
			else {
				System.out.println("non cancellato"+file.getAbsolutePath());
				String result=gson.toJson("no");
				response.getWriter().print(result);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
