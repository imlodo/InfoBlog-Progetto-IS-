package control;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.bean.Autore;
import model.bean.Moderatore;
import model.bean.Utente;
import model.manager.AutoreManagement;
import model.manager.ModeratoreManagement;
import model.manager.UserManagement;
import storage.DriverManagerConnectionPool;
import utils.Utils;

/**
 * Servlet implementation class LoginControl
 */
@WebServlet("/LoginControl")
public class LoginControl extends HttpServlet 
{	

	private static final long serialVersionUID = 1L;
	private static final String loginPage = "login.jsp";
	private static final String homePage = "/InfoBlog/";
	/**
	 * Default constructor. 
	 */
	public LoginControl() 
	{
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		//Vedo se è stato effettuato già l'accesso
		String email = Utils.checkLogin(session, request.getCookies());
		if(email != null)
		{
			response.sendRedirect(homePage);
			return;
		}
		
		email = request.getParameter("email");
		String password = request.getParameter("password");
		String typeUser = request.getParameter("typeUser");

		request.setAttribute("email", email);
		request.setAttribute("password", password);
		if(!Utils.controlPassword(password) || !Utils.checkEmail(email) || !Utils.controlTypeUser(typeUser))
		{
			//Se era giusto il tipo utente lo setto.
			if(Utils.controlTypeUser(typeUser))
				request.setAttribute("checked", typeUser+"Check");
			// errore nell'inserimento dei dati da parte dell'utente, mandiamo l'errore alla jsp 
			request.setAttribute("errore", "FORMATO_DATI_ERRATO");
			RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
			dispatcher.forward(request, response);
		}
		else
		{
			request.setAttribute("checked", typeUser+"Check");
			//Istanzio il pool
			DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
			try
			{
				switch(typeUser)
				{
					case "Utente":
					{
						//Istanzio il manager per l'utente
						UserManagement managerUser = new UserManagement(pool);
						Utente user = managerUser.doRetrieveByKey(email);
						if(user == null)
						{
							request.setAttribute("errore", "DATI_ERRATI");
							RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
							dispatcher.forward(request, response);
						}
						else
						{
							String emailDB = user.getEmail();
							String passwordDB = user.getPassword();
							if(emailDB != null && passwordDB != null)
							{
								if(emailDB.equals(email) && passwordDB.equals(password))
								{
									// l'utente ha effettuato l'accesso con successo
									// prendo la sessione in modo sincronizzato
									synchronized(session) 
									{
										session.setAttribute("Utente", emailDB);
										session.setAttribute("Username", user.getUsername());
										//sessione invalida in 30 minuti
										session.setMaxInactiveInterval(30*60);
										//setto i cookie
										Cookie emailCookie = new Cookie("Utente", emailDB);
										Cookie usernameCookie = new Cookie("Username", user.getUsername());
										response.addCookie(emailCookie);
										response.addCookie(usernameCookie);
										//Reindirizzo l'utente loggato all'homepage
										response.sendRedirect(homePage);
									}
								}
								else
								{
									//Username e password errati segnalo l'errore alla jsp
									request.setAttribute("errore", "DATI_ERRATI");
									RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
									dispatcher.forward(request, response);
								}
							}
							else
							{
								request.setAttribute("errore", "DATI_ERRATI");
								RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
								dispatcher.forward(request, response);
							}
						}
					}break;
					
					case "Autore":
					{
						AutoreManagement managerAutore = new AutoreManagement(pool);
						Autore autore = managerAutore.doRetrieveByKey(email);
						if(autore == null)
						{
							request.setAttribute("errore", "DATI_ERRATI");
							RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
							dispatcher.forward(request, response);
						}
						else
						{
							String emailDB = autore.getEmail();
							String passwordDB = autore.getPassword();
							if(emailDB != null && passwordDB != null)
							{
								if(emailDB.equals(email) && passwordDB.equals(password))
								{
									// l'autore ha effettuato l'accesso con successo
									//Sessione
									synchronized(session) 
									{
										session.setAttribute("Autore", emailDB);
										session.setAttribute("Username", autore.getUsername());
										//setting session to expiry in 30 mins
										session.setMaxInactiveInterval(30*60);
										//Cookie
										Cookie emailCookie = new Cookie("Autore", emailDB);
										Cookie usernameCookie = new Cookie("Username", autore.getUsername());
										response.addCookie(emailCookie);
										response.addCookie(usernameCookie);
										//Reindirizzo l'utente loggato all'homepage
										response.sendRedirect(homePage);
									}
								}
								else
								{
									request.setAttribute("errore", "DATI_ERRATI");
									RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
									dispatcher.forward(request, response);
								}
							}
							else
							{
								request.setAttribute("errore", "DATI_ERRATI");
								RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
								dispatcher.forward(request, response);
							}
						}
					}break;
					
					case "Moderatore":
					{
						ModeratoreManagement managerModeratore = new ModeratoreManagement(pool);
						Moderatore moderatore = managerModeratore.doRetrieveByKey(email);
						if(moderatore == null)
						{
							request.setAttribute("errore", "DATI_ERRATI");
							RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
							dispatcher.forward(request, response);
						}
						else
						{
							String emailDB = moderatore.getEmail();
							String passwordDB = moderatore.getPassword();
							if(emailDB != null && passwordDB != null)
							{
								if(emailDB.equals(email) && passwordDB.equals(password))
								{
									// il moderatore ha effettuato l'accesso con successo
									//Sessione
									synchronized(session) 
									{
										session.setAttribute("Moderatore", emailDB);
										session.setAttribute("Username", moderatore.getUsername());
										//setting session to expiry in 30 mins
										session.setMaxInactiveInterval(30*60);
										//Cookie
										Cookie emailCookie = new Cookie("Moderatore", emailDB);
										Cookie usernameCookie = new Cookie("Username", moderatore.getUsername());
										response.addCookie(emailCookie);
										response.addCookie(usernameCookie);
										//Reindirizzo l'utente loggato all'homepage
										response.sendRedirect(homePage);
									}
								}
								else
								{
									request.setAttribute("errore", "DATI_ERRATI");
									RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
									dispatcher.forward(request, response);
								}
							}
							else
							{
								request.setAttribute("errore", "DATI_ERRATI");
								RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
								dispatcher.forward(request, response);
							}
						}
					}break;
					
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}		
		}
	}

}
