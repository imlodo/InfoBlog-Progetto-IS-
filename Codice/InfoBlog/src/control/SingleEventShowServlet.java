package control;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Evento;
import model.manager.EventoManagement;
import storage.DriverManagerConnectionPool;

@WebServlet("/SingleEventShowServlet")
public class SingleEventShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(request.getSession().getAttribute("Moderatore")==null)
		{
			EventoManagement DAOEvento=new EventoManagement(new DriverManagerConnectionPool());
			ArrayList<String> parametri=new ArrayList<String>();
			Evento event=null;


			String data=request.getParameter("data");
			String via=request.getParameter("via");
			String citta=request.getParameter("citta");
		
			LocalDate date;
			
			if(data!=null && via!=null && citta!=null)
			{
				
				
				String regexp = "^[A-Z][a-z][^#!@&<>\\[\\]\"~;$^%{}?{0-9}]{2,50}$";
				Pattern pt = Pattern.compile(regexp);
				Matcher mt = pt.matcher(via);
				boolean resultmatchvia = mt.matches();

				regexp="[A-Z][a-zA-Z][^#&@<>\"~;$^%{}?{0-9}]{2,30}";
				pt = Pattern.compile(regexp);
				mt = pt.matcher(citta);
				boolean resultmatchCittà = mt.matches();
				
				try
				{
					date=LocalDate.parse(data);
				}
				catch (DateTimeParseException  e) 
				{
					request.setAttribute("errore","Formato Data errato");
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
					requestDispatcher.forward(request, response);
					return;
				}
				
				if(!(date.compareTo(LocalDate.now())>0))
				{
					request.setAttribute("errore","Data passata");
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
					requestDispatcher.forward(request, response);
					return;
				}
				
				if(!resultmatchCittà)
				{
					request.setAttribute("errore","formato città errata");
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
					requestDispatcher.forward(request, response);
					return;
				}
				if(!resultmatchvia)
				{
					request.setAttribute("errore","formato via errata");
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
					requestDispatcher.forward(request, response);
					return;
				}	
				
				parametri.add(data);
				parametri.add(via);
				parametri.add(citta);
				parametri.add(request.getParameter("id"));
				try 
				{
					event=DAOEvento.doRetrieveByKey(parametri);
					if(event!=null)
					{
						request.setAttribute("evento", event);
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("ShowSingleEvent.jsp");
						requestDispatcher.forward(request,response);
					}
					else
					{
						request.setAttribute("errore", "informazioni errate");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
						requestDispatcher.forward(request, response);
						return;
					}
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			request.setAttribute("errore", "Accesso negato");
			response.sendRedirect("notfound.jsp");
		}	
	}
}
