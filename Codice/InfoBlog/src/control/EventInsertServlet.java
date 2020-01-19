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
import model.bean.Autore;
import model.bean.Evento;
import model.manager.AutoreManagement;
import model.manager.EventoManagement;
import storage.DriverManagerConnectionPool;


@WebServlet("/EventInsertServlet")
public class EventInsertServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(request.getSession().getAttribute("Autore")!=null)
		{
			String nome=request.getParameter("event-title");
			String via=request.getParameter("via");
			String città=request.getParameter("citta");
			String argomento=request.getParameter("event-argument");
			
			LocalDate data;

			String regexp = "[A-Z][a-zA-Z][^#&@<>~;$^%{}?{0-9}]{4,30}$";
			Pattern pt = Pattern.compile(regexp);
			Matcher mt = pt.matcher(via);
			boolean resultmatchvia = mt.matches();

			regexp="[A-Z][a-zA-Z][^#&<>@~;$^%{}?{0-9}]{4,30}$";
			pt = Pattern.compile(regexp);
			mt = pt.matcher(città);
			boolean resultmatchCittà = mt.matches();
			
			regexp="^[A-Z][a-z][^#!@&<>\\[\\]~;$^%{}?{0-9}]{0,50}$";
			pt = Pattern.compile(regexp);
			mt = pt.matcher(nome);
			boolean resultmatchNome = mt.matches();

			regexp="[a-zA-Z0-9#&<>~;$^%{}?][^~^]{4,500}$";
			pt = Pattern.compile(regexp);
			mt = pt.matcher(argomento);
			boolean resultmatchArgomento = mt.matches();
			
			try
			{
				data=LocalDate.parse(request.getParameter("dataEvento").replace("/", "-"));
			}
			catch (DateTimeParseException  e) 
			{
				request.setAttribute("errore","Formato data errato");
				request.setAttribute("citta", request.getParameter("citta"));
				request.setAttribute("data", request.getParameter("dataEvento"));
				request.setAttribute("via", request.getParameter("via"));
				request.setAttribute("argomento",request.getParameter("event-argument"));
				request.setAttribute("titoloEvento",request.getParameter("event-title"));
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("event.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
			
			
			boolean flag=data.compareTo(LocalDate.now())>0;
			
			if(!resultmatchArgomento)
			{
				request.setAttribute("errore","Formato argomento non valido");
				request.setAttribute("citta", request.getParameter("citta"));
				request.setAttribute("data", request.getParameter("dataEvento"));
				request.setAttribute("via", request.getParameter("via"));
				request.setAttribute("argomento",request.getParameter("event-argument"));
				request.setAttribute("titoloEvento",request.getParameter("event-title"));
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("event.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
				
			if(!resultmatchNome)
			{
				request.setAttribute("errore","Formato titolo non valido");
				request.setAttribute("citta", request.getParameter("citta"));
				request.setAttribute("data", request.getParameter("dataEvento"));
				request.setAttribute("via", request.getParameter("via"));
				request.setAttribute("argomento",request.getParameter("event-argument"));
				request.setAttribute("titoloEvento",request.getParameter("event-title"));
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("event.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
			
			
			if(!flag)
			{
				request.setAttribute("errore","Data passata");
				request.setAttribute("citta", request.getParameter("citta"));
				request.setAttribute("data", request.getParameter("dataEvento"));
				request.setAttribute("via", request.getParameter("via"));
				request.setAttribute("argomento",request.getParameter("event-argument"));
				request.setAttribute("titoloEvento",request.getParameter("event-title"));
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("event.jsp");
				requestDispatcher.forward(request, response);
				return;
			}

			if(!resultmatchCittà)
			{
				request.setAttribute("errore","formato città errata");
				request.setAttribute("citta", request.getParameter("citta"));
				request.setAttribute("data", request.getParameter("dataEvento"));
				request.setAttribute("via", request.getParameter("via"));
				request.setAttribute("argomento",request.getParameter("event-argument"));
				request.setAttribute("titoloEvento",request.getParameter("event-title"));
				
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("event.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
			if(!resultmatchvia)
			{
				request.setAttribute("errore","formato via errata");
				request.setAttribute("citta", request.getParameter("citta"));
				request.setAttribute("data", request.getParameter("dataEvento"));
				request.setAttribute("via", request.getParameter("via"));
				request.setAttribute("argomento",request.getParameter("event-argument"));
				request.setAttribute("titoloEvento",request.getParameter("event-title"));
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("event.jsp");
				requestDispatcher.forward(request, response);
				return;
			}		


			try
			{
				Autore scrittore=new AutoreManagement(new DriverManagerConnectionPool()).doRetrieveByKey((String)request.getSession().getAttribute("Autore"));
				Evento event=new Evento(via,città,nome,argomento,data,scrittore);

				EventoManagement DAOEvento=new EventoManagement(new DriverManagerConnectionPool());
				ArrayList<String> parametri=new ArrayList<String>();
				parametri.add(request.getParameter("dataEvento"));
				parametri.add(via);
				parametri.add(città);
				Evento event2=DAOEvento.doRetrieveByKey(parametri);
				if(event2==null)
				{
					DAOEvento.doSave(event);
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("EventShowServlet");
					requestDispatcher.forward(request, response);
				}
				else
				{
					request.setAttribute("erroreRegistrazione", "Evento già registrato");
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("event.jsp");
					requestDispatcher.forward(request, response);
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			request.setAttribute("errore", "Accesso negato");
			response.sendRedirect("notfound.jsp");
		}
	}
}
