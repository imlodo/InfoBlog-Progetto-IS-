package control;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import model.bean.Allegato;
import model.manager.AllegatoManagement;
import storage.DriverManagerConnectionPool;
import utils.Utils;

/**
 * Servlet implementation class CaricaAllegatoControl
 */
@WebServlet("/CaricaAllegatoControl")
@MultipartConfig
public class CaricaAllegatoControl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public CaricaAllegatoControl() 
    {
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
//		Part fileAllegato=request.getPart("file");
		ArrayList<Part> files = null;
		String idArticolo = null;
//		String idArticolo = request.getParameter("id");
//		if(idArticolo != null)
//		{
//			//Prendo i file
//			files = (ArrayList<Part>) request.getParts();
//			ArrayList<Part> fileDaCaricare = new ArrayList<Part>();
//			for(Part el : files)
//			{
//				String name = el.getSubmittedFileName();
//				if(name != null)
//				{
//					
//					if(!Utils.checkFormato(name))
//					{
//						// mandiamo l'errore alla jsp 
//						String url = "modificaArticolo.jsp"; // url della jsp
//						request.setAttribute("errore", "FORMATO_ALLEGATI_ERRATO");
//						RequestDispatcher dispatcher = request.getRequestDispatcher(url);
//						dispatcher.forward(request, response);
//						return;
//					}
//					//è un file pdf, word o wordx, posso caricarlo
//					fileDaCaricare.add(el);
//				}	
//			}
//			files = fileDaCaricare;
//		}
//		else
//		{
		files = (ArrayList<Part>) request.getAttribute("files");
//		}
		int allegatiCaricati = 0;
		try 
		{
			if(files != null)
			{
				for(Part el : files)
				{
					if(idArticolo == null)
			    		idArticolo = String.valueOf(request.getAttribute("id"));
					
					//path dell'allegato
				    String fileName = Paths.get(el.getSubmittedFileName()).getFileName().toString();
					System.out.println(fileName);
				    
				    	Allegato nuovoAll=new Allegato();
				    	nuovoAll.setPercorsoFile(new File(uploadsDir,fileName).toString());
				    	nuovoAll.setId(Integer.parseInt(idArticolo));
				    	
				    	allMan.doSave(nuovoAll);
					
						if(!uploadsDir.exists()) {
					    	uploadsDir.mkdir();
					    	System.out.println("cartella creata");
					    }
					    
						File file=new File(nuovoAll.getPercorsoFile());
						
						Gson gson=new Gson();
						
						response.setContentType("application/json");
						
						if(file.delete()) 
						{
							System.out.println("cancellato"+file.getAbsolutePath());
							String result=gson.toJson("si");
							response.getWriter().print(result);
							System.out.println(result);
							
						}
						else {
							System.out.println("non cancellato"+file.getAbsolutePath());
							String result=gson.toJson("no");
							response.getWriter().print(result);
						}
					    InputStream fileContent=el.getInputStream();
					    Files.copy(fileContent, new File(uploadsDir,fileName).toPath());
					    
					    allegatiCaricati+=1;
				  }
				request.setAttribute("successUpload", allegatiCaricati);
			}
		}
		catch (SQLException | FileAlreadyExistsException e) 
	    {
			if(e.getClass().getSimpleName().equals(SQLException.class.getSimpleName()))
				request.setAttribute("errorUpload", "ALLEGATO_PRESENTE");
			else
			{
				request.setAttribute("errorUpload", "FILE_ALLEGATO_PRESENTE");
			}
			return;
		}	
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
