package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

import model.bean.Allegato;
import model.manager.AllegatoManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class ScaricaAllegatoControl
 */
@WebServlet("/ScaricaAllegatoControl")
public class ScaricaAllegatoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ScaricaAllegatoControl() {
    	super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String pathAllegato=request.getParameter("path");
		
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		AllegatoManagement allMan=new AllegatoManagement(dm);
		
		try {
			Allegato allegato=allMan.doRetrieveByKey(getServletContext().getInitParameter("allegati")+"\\"+request.getParameter("path"));
			File file=new File(allegato.getPercorsoFile());
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename="+file.getName());
			
			FileInputStream in=new FileInputStream(file);
			ServletOutputStream out=response.getOutputStream();
			
			byte[] buffer = new byte[4096];
	        int length;
	        while ((length = in.read(buffer)) > 0){
	           out.write(buffer, 0, length);
	        }
	        in.close();
	        out.flush();
		}catch (SQLException e) {
			System.out.println("SQL andato storto...");
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
