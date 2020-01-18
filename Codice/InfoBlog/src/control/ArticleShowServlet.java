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
import model.bean.Commento;
import model.bean.Moderatore;
import model.bean.Rating;
import model.bean.Seguace;
import model.manager.ArticoloManagement;
import model.manager.CommentoManagment;
import model.manager.ModeratoreManagement;
import model.manager.RatingManagement;
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
		CommentoManagment DAOCommento=new CommentoManagment(new DriverManagerConnectionPool());
		ArrayList<Commento> commenti;
		ArrayList<ArrayList<Commento>> commentiArticoli= new ArrayList<ArrayList<Commento>>();
		ArrayList<Rating> rating;
		ArrayList<ArrayList<Rating>> ratingCollettivo=new ArrayList<ArrayList<Rating>>();
		RatingManagement DAORating=new RatingManagement(new DriverManagerConnectionPool());
		
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
					{
						request.setAttribute("Nocommenti",0);
						request.setAttribute("Norating", 0);
						request.setAttribute("articoli",articoli);
					}
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
				if(ssn.getAttribute("Autore")!=null)
				{
					String email=(String)ssn.getAttribute("Autore");
					try
					{
						articoli=(ArrayList<Articolo>)DAOArticolo.doRetrieveAll("a:"+email);
						if(articoli!=null && articoli.size()>0)
						{
							request.setAttribute("articoli",articoli);
							for(int i=0;i<articoli.size();i++)
							{
								commenti=(ArrayList<Commento>) DAOCommento.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
								commentiArticoli.add(commenti);
								rating=(ArrayList<Rating>) DAORating.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
								ratingCollettivo.add(rating);
							}
							request.setAttribute("commenti",commentiArticoli);
							request.setAttribute("rating",ratingCollettivo);
						}
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
							{
								request.setAttribute("articoli",articoli);
								for(int i=0;i<articoli.size();i++)
								{
									commenti=(ArrayList<Commento>) DAOCommento.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
									commentiArticoli.add(commenti);
									rating=(ArrayList<Rating>) DAORating.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
									ratingCollettivo.add(rating);
								}
								request.setAttribute("commenti",commentiArticoli);
								request.setAttribute("rating",ratingCollettivo);
							}
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
						String paramentro=request.getParameter("esplora");
						if(paramentro!=null)
						{
							try
							{
								articoli=(ArrayList<Articolo>)DAOArticolo.doRetrieveAll("e:");
								if(articoli!=null && articoli.size()>0)
								{
									request.setAttribute("articoli",articoli);
									for(int i=0;i<articoli.size();i++)
									{
										commenti=(ArrayList<Commento>) DAOCommento.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
										commentiArticoli.add(commenti);
										rating=(ArrayList<Rating>) DAORating.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
										ratingCollettivo.add(rating);
									}
									request.setAttribute("commenti",commentiArticoli);
									request.setAttribute("rating",ratingCollettivo);
								}
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
							SeguiManagement seguiManagement=new SeguiManagement(new DriverManagerConnectionPool());
							ArrayList<Seguace> seguaci=new ArrayList<Seguace>();

							try
							{
								seguaci=(ArrayList<Seguace>)seguiManagement.doRetrieveAll((String)ssn.getAttribute("Utente"));
								ArrayList<Articolo> articoliSpecifici=new ArrayList<Articolo>();
								if(seguaci!=null && seguaci.size()>0)
								{
									for(int i=0;i<seguaci.size();i++)
									{
										articoliSpecifici=(ArrayList<Articolo>)DAOArticolo.doRetrieveAll("a:"+seguaci.get(i).getAutore());
										if(articoliSpecifici!=null && articoliSpecifici.size()>0)
											for(int j=0;j<articoliSpecifici.size();j++)
												articoli.add(articoliSpecifici.get(j));	

									}
									if(articoli!=null && articoli.size()>0)
									{
										request.setAttribute("articoli",articoli);
										for(int i=0;i<articoli.size();i++)
										{
											commenti=(ArrayList<Commento>) DAOCommento.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
											commentiArticoli.add(commenti);
											rating=(ArrayList<Rating>) DAORating.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
											ratingCollettivo.add(rating);
										}
										request.setAttribute("commenti",commentiArticoli);
										request.setAttribute("rating",ratingCollettivo);
									}
									else
										request.setAttribute("Vuoto","Non ci sono articoli");
									RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
									requestDispatcher.forward(request, response);
								}
								else
								{
									try
									{
										articoli=(ArrayList<Articolo>)DAOArticolo.doRetrieveAll("");
										if(articoli!=null && articoli.size()>0)
										{
											request.setAttribute("articoli",articoli);
											for(int i=0;i<articoli.size();i++)
											{
												commenti=(ArrayList<Commento>) DAOCommento.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
												commentiArticoli.add(commenti);
												rating=(ArrayList<Rating>) DAORating.doRetrieveAll(String.valueOf(articoli.get(i).getId()));
												ratingCollettivo.add(rating);
											}
											request.setAttribute("commenti",commentiArticoli);
											request.setAttribute("rating",ratingCollettivo);
										}
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
