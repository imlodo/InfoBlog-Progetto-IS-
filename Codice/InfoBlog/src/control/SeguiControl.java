package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Seguace;
import model.manager.SeguiManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class SeguiControl
 */
@WebServlet("/SeguiControl")
public class SeguiControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SeguiControl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("Utente")!=null)
		{
			String emailAutore=request.getParameter("emailAutore");
			if(emailAutore!=null)
			{
				if(!utils.Utils.checkEmail(emailAutore))
				{
					request.setAttribute("errore", "mail non valida");
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
					requestDispatcher.forward(request, response);
				}
				else
				{
					SeguiManagement DAOSegui=new SeguiManagement(new DriverManagerConnectionPool());
					Seguace seguace=new Seguace();
					seguace.setAutore(emailAutore);
					seguace.setUtente((String)request.getSession().getAttribute("Utente"));
					try 
					{
						ArrayList<Seguace> seguaci=(ArrayList<Seguace>)DAOSegui.doRetrieveAll(seguace.getUtente());
						boolean flag=false;
						if(seguaci.size()>0)
						{
							for(int i=0;i<seguaci.size();i++)
								if(seguaci.get(i).getAutore().equals(emailAutore))
									flag=true;
							if(!flag)
							{
								DAOSegui.doSave(seguace);
								request.setAttribute("segui","operazione effettuata");
								RequestDispatcher requestDispatcher=request.getRequestDispatcher("PageAutoreServlet?email="+emailAutore);
								requestDispatcher.forward(request, response);
							}
							else
							{
								request.setAttribute("errore","Operazione gia registrata");
								RequestDispatcher requestDispatcher=request.getRequestDispatcher("PageAutoreServlet?email="+emailAutore);
								requestDispatcher.forward(request, response);
							}
						}
						else 
						{
							DAOSegui.doSave(seguace);
							request.setAttribute("segui","operazione effettuata");
							RequestDispatcher requestDispatcher=request.getRequestDispatcher("PageAutoreServlet?email="+emailAutore);
							requestDispatcher.forward(request, response);
						}
					} 
					catch (SQLException e) 
					{
						request.setAttribute("errore", "Infomazioni duplicate");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("notfound.jsp");
						requestDispatcher.forward(request, response);
					}
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
