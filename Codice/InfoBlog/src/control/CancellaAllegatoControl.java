package control;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//String pathAllegato=request.getParameter("path");
		
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		AllegatoManagement manAll=new AllegatoManagement(dm);
		
		try {
			Allegato allegato=new Allegato();
			allegato.setPercorsoFile("C:\\Users\\Federico\\Desktop\\allegati\\03.Integration and System Testing.pdf");
			
			File file=new File(allegato.getPercorsoFile());
			file.delete();
			
			manAll.doDelete(allegato);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	    response.sendRedirect(request.getContextPath()+"/index.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
