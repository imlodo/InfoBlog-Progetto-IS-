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
import javax.servlet.http.HttpSession;

import model.bean.Articolo;
import model.bean.Moderatore;
import model.bean.Seguace;
import model.manager.ArticoloManagement;
import model.manager.ModeratoreManagement;
import model.manager.SeguiManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class ArticleShowServlet
 */
@WebServlet("/ArticleShowServlet")
public class ArticleShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArticleShowServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String url="AllArticle.jsp";
		ArticoloManagement DAOArticolo=new ArticoloManagement(new DriverManagerConnectionPool());
		ArrayList<Articolo> articoli=new ArrayList<Articolo>();
		HttpSession ssn=request.getSession();


		synchronized (ssn) 
		{
			if(ssn.getAttribute("Moderatore")!=null)
			{
				String email=(String)ssn.getAttribute("Moderatore");
				ModeratoreManagement dAOModeratoreManagement=new ModeratoreManagement(new DriverManagerConnectionPool());
				
				try
				{
					Moderatore mod=dAOModeratoreManagement.doRetrieveByKey(email);
					articoli=(ArrayList<Articolo>)DAOArticolo.doRetrieveAll("m:"+mod.getCategoria_moderazione());
					if(articoli!=null && articoli.size()>0)
						request.setAttribute("articoli",articoli);
					else
						request.setAttribute("Vuoto","Non hai pubblicato articoli");
					System.out.println(articoli.size());
					RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
					requestDispatcher.forward(request, response);
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
			}
			else
				if(ssn.getAttribute("Autore")!=null)
				{
					String email=(String)ssn.getAttribute("Autore");
					try
					{
						articoli=(ArrayList<Articolo>)DAOArticolo.doRetrieveAll("a:"+email);
						if(articoli!=null && articoli.size()>0)
							request.setAttribute("articoli",articoli);
						else
							request.setAttribute("Vuoto","Non hai pubblicato articoli");
						RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
						requestDispatcher.forward(request, response);
					}
					catch(SQLException ex)
					{
						ex.printStackTrace();
					}
				}
				else
				{
					if(ssn.getAttribute("Utente")==null)
					{
						try
						{
							articoli=(ArrayList<Articolo>)DAOArticolo.doRetrieveAll("");
							if(articoli!=null && articoli.size()>0)
								request.setAttribute("articoli",articoli);
							else
								request.setAttribute("Vuoto","Non ci sono articoli");
							RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
							requestDispatcher.forward(request, response);
						}
						catch(SQLException ex)
						{
							ex.printStackTrace();
						}
					}
					else
					{
						System.out.println("utente presente");
						String paramentro=request.getParameter("esplora");
						if(paramentro!=null)
						{
							System.out.println("sezione esplora");
							try
							{
								articoli=(ArrayList<Articolo>)DAOArticolo.doRetrieveAll("e:");
								if(articoli!=null && articoli.size()>0)
									request.setAttribute("articoli",articoli);
								else
									request.setAttribute("Vuoto","Non ci sono articoli");
								RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
								requestDispatcher.forward(request, response);
							}
							catch(SQLException ex)
							{
								ex.printStackTrace();
							}
						}
						else
						{
							System.out.println("tentiamo i segui");
							SeguiManagement seguiManagement=new SeguiManagement(new DriverManagerConnectionPool());
							ArrayList<Seguace> seguaci=new ArrayList<Seguace>();

							try
							{
								seguaci=(ArrayList<Seguace>)seguiManagement.doRetrieveAll((String)ssn.getAttribute("Utente"));
								ArrayList<Articolo> articoliSpecifici=new ArrayList<Articolo>();
								if(seguaci!=null && seguaci.size()>0)
								{
									System.out.println("siamo nei segui");
									for(int i=0;i<seguaci.size();i++)
									{
										articoliSpecifici=(ArrayList<Articolo>)DAOArticolo.doRetrieveAll("a:"+seguaci.get(i).getAutore());
										if(articoliSpecifici!=null && articoliSpecifici.size()>0)
											for(int j=0;j<articoliSpecifici.size();j++)
												articoli.add(articoliSpecifici.get(j));	

									}
									if(articoli!=null && articoli.size()>0)
										request.setAttribute("articoli",articoli);
									else
										request.setAttribute("Vuoto","Non ci sono articoli");
									RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
									requestDispatcher.forward(request, response);
								}
								else
								{
									System.out.println("nulla seguei");
									try
									{
										articoli=(ArrayList<Articolo>)DAOArticolo.doRetrieveAll("");
										if(articoli!=null && articoli.size()>0)
											request.setAttribute("articoli",articoli);
										else
											request.setAttribute("Vuoto","Non ci sono articoli");
										RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
										requestDispatcher.forward(request, response);
									}
									catch(SQLException ex)
									{
										ex.printStackTrace();
									}
								}
							}
							catch(SQLException ex)
							{
								ex.printStackTrace();
							}
						}
					}
				}
		}
	}

}
