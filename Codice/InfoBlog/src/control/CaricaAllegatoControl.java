package control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import model.bean.Allegato;
import model.manager.AllegatoManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class CaricaAllegatoControl
 */
@WebServlet("/CaricaAllegatoControl")
@MultipartConfig
public class CaricaAllegatoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CaricaAllegatoControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		AllegatoManagement allMan=new AllegatoManagement(dm);
	
		//path della cartella degli allegati del server
		File uploadsDir = new File(getServletContext().getInitParameter("allegati"));
		System.out.println(uploadsDir.toString());
		//dati dell'allegato
		Part fileAllegato=request.getPart("file");
		//path dell'allegato
	    String fileName = Paths.get(fileAllegato.getSubmittedFileName()).getFileName().toString();
		System.out.println(fileName);
	    
	    try {
	    	Allegato nuovoAll=new Allegato();
	    	nuovoAll.setPercorsoFile(new File(uploadsDir,fileName).toString());
	    	nuovoAll.setId(Integer.parseInt(request.getParameter("id")));
	    	
			allMan.doSave(nuovoAll);
		
			if(!uploadsDir.exists()) {
		    	uploadsDir.mkdir();
		    	System.out.println("cartella creata");
		    }
		    
		    InputStream fileContent=fileAllegato.getInputStream();
		    Files.copy(fileContent, new File(uploadsDir,fileName).toPath());
			response.sendRedirect(response.encodeURL(request.getContextPath()+"/caricaAllegato.jsp"));

	    } catch (SQLException | FileAlreadyExistsException e) {
			e.printStackTrace();
		}	
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
