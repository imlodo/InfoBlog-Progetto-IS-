package control;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
		
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		AllegatoManagement allMan=new AllegatoManagement(dm);
		String idArticolo = request.getParameter("id");
		if(idArticolo != null)
		{
			response.setContentType("application/zip");
	        response.setHeader("Content-Disposition", "attachment; filename=allegati.zip");
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			ZipOutputStream zos = new ZipOutputStream(bos);
			try {
				ArrayList<Allegato> allegati = (ArrayList<Allegato>) allMan.doRetrieveByID(Integer.parseInt(idArticolo));
				
				for(Allegato a: allegati)
				{
					File file=new File(a.getPercorsoFile());
					// not available on BufferedOutputStream
			        zos.putNextEntry(new ZipEntry(file.getName()));
			        zos.write(file.getName().getBytes());
			        // not available on BufferedOutputStream
			        zos.closeEntry();
				}
				request.setAttribute("ok", "ok");
			}
			catch (SQLException e) {
				System.out.println("SQL andato storto...");
				e.printStackTrace();
			}
			finally {
			    zos.close();
			}
		}
		else
		{
			String path = request.getParameter("path");
			try {
				if(path != null)
				{
					String pathContext = "C:\\users\\public";
					Allegato allegato=allMan.doRetrieveByKey(pathContext+"\\"+path);
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
			        request.setAttribute("ok", "ok");
				}
				else
				{
					request.setAttribute("notfound", "notfound");
					response.sendRedirect("notfound.jsp");
				}
			}catch (SQLException e) {
				System.out.println("SQL andato storto...");
				e.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
