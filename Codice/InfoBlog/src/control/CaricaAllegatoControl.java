package control;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private static final String notFoundPage = "notfound.jsp";
       
    public CaricaAllegatoControl() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	@SuppressWarnings("unchecked")
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
		
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		AllegatoManagement allMan=new AllegatoManagement(dm);
		//path della cartella degli allegati del server
		File uploadsDir = new File(getServletContext().getInitParameter("allegati"));
//		System.out.println(uploadsDir.toString());
		
		//dati dell'allegato
		ArrayList<Part> files = null;
		String idArticolo = null;
		files = (ArrayList<Part>) request.getAttribute("files");
		ArrayList<Allegato> allegatiSave = new ArrayList<Allegato>();
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
//					System.out.println(fileName);
				    
				    Allegato nuovoAll=new Allegato();
				    nuovoAll.setPercorsoFile(new File(uploadsDir,fileName).toString());
				    nuovoAll.setId(Integer.parseInt(idArticolo));
				    	
					if(!uploadsDir.exists()) {
					    uploadsDir.mkdir();
//					    System.out.println("cartella creata");
					}
					
					File file=new File(nuovoAll.getPercorsoFile());
						
					Gson gson=new Gson();
						
					response.setContentType("application/json");
						
					file.delete();
					InputStream fileContent=el.getInputStream();
					Files.copy(fileContent, new File(uploadsDir,fileName).toPath());
					allMan.doSave(nuovoAll);
					allegatiSave.add(nuovoAll);
					allegatiCaricati+=1;
				}
				request.setAttribute("successUpload", allegatiCaricati);
			}
			else
			{
				response.sendRedirect(notFoundPage);
			}
		}
		catch (SQLException | FileAlreadyExistsException e) 
	    {
			//Cancello gli allegati caricati
			for(Allegato a : allegatiSave)
			{
				try
				{
					allMan.doDelete(a);
					File file=new File(a.getPercorsoFile());
					
					Gson gson=new Gson();
					
					response.setContentType("application/json");
					
					file.delete();
				}
				catch (SQLException e1)
				{
					
					e1.printStackTrace();
				}
			}
			
			if(e.getClass().getSimpleName().equals(SQLException.class.getSimpleName()))
				request.setAttribute("errorUpload", "ALLEGATO_PRESENTE");
			else
			{
				request.setAttribute("errorUpload", "FILE_ALLEGATO_PRESENTE");
			}
			return;
		}	
	    
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
