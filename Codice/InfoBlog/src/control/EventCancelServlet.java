package control;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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


@WebServlet("/EventCancelServlet")
public class EventCancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(request.getSession().getAttribute("Autore")!=null)
		{
			int idEvento=0;
			try
			{
				idEvento=Integer.parseInt(request.getParameter("idEvento"));
			}
			catch (NumberFormatException e) 
			{
				request.setAttribute("errore", "id non valido");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
				requestDispatcher.forward(request, response);
			}

			String via=request.getParameter("via");
			String città=request.getParameter("citta");
			String data=request.getParameter("data");
			String url="notfound.jsp";

			if(via!=null && città!=null && data!=null)
			{
				Evento event=new Evento();
				EventoManagement DAOEvento=new EventoManagement(new DriverManagerConnectionPool());
				try
				{
					String regexp = "^[A-Z][a-z][^#!@&<>\\[\\]\"~;$^%{}?{0-9}]{2,50}$";
					Pattern pt = Pattern.compile(regexp);
					Matcher mt = pt.matcher(via);
					boolean resultmatchvia = mt.matches();

					regexp="[A-Z][a-zA-Z][^#&@<>\"~;$^%{}?{0-9}]{2,30}";
					pt = Pattern.compile(regexp);
					mt = pt.matcher(città);
					boolean resultmatchCittà = mt.matches();
					
					try 
					{
						LocalDate.parse(data);
					}
					catch(DateTimeParseException ex)
					{
						request.setAttribute("errore","Formato data non valido");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
						requestDispatcher.forward(request, response);
						return;
					}
										
					LocalDate date=LocalDate.parse(data);
					boolean flag=date.compareTo(LocalDate.now())>0;
					
					
					if(!flag)
					{
						request.setAttribute("errore","Data passata");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
						requestDispatcher.forward(request, response);
						return;
					}
													
					if(!resultmatchCittà)
					{
						request.setAttribute("errore","formato città errata");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
						requestDispatcher.forward(request, response);
						return;
					}
					if(!resultmatchvia)
					{
						request.setAttribute("errore","formato via errata");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
						requestDispatcher.forward(request, response);
						return;
					}	

					event.setIdEvento(idEvento);
					event.setCittà(città);
					event.setData(LocalDate.parse(data));
					event.setVia(via);
					boolean flag1=DAOEvento.doDelete(event);
					
					if(flag1)
					{
						request.setAttribute("successo", "cancellazione avvenuta");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("EventShowServlet");
						requestDispatcher.forward(request,response);
					}
					else
					{
						request.setAttribute("errore", "cancellazione non avvenuta");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
						requestDispatcher.forward(request,response);
					}
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
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
