package control;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	private static boolean controlTypeUser(String text)
	{
		if(text == null)
		{
			return false;
		}
		switch(text)
		{
		case "Utente": return true;
		case "Autore": return true;
		case "Moderatore": return true;
		default: return false;
		}
	}
	private static boolean controlPassword(String text)
	{	
		if(text == null)
		{
			return false;
		}
		String regexp = "([\\wA-Z\\d]*(\\w|[A-Z]|\\d)[!%]*).{1,64}$";
		// in javascript vanno inserite tra /regexrp/ in java NO
		Pattern pt = Pattern.compile(regexp);
		Matcher mt = pt.matcher(text);
		boolean resultmatch = mt.matches();	
		return resultmatch;
	}

	private static boolean checkEmail(String text)
	{
		if(text == null)
		{
			return false;
		}
		String regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		// in javascript vanno inserite tra /regexrp/ in java NO

		Pattern pt = Pattern.compile(regexp);
		Matcher mt = pt.matcher(text);

		boolean resultmatch = mt.matches();

		return resultmatch;

	}
	
	

	private static final long serialVersionUID = 1L;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);
		//Vedo se è stato effettuato già l'accesso
		String email = Utils.checkLogin(session, request.getCookies());
		if(email != null)
		{
			if(email.substring(0, 1).equals("u"))
			{
				request.setAttribute("UtenteLog",email.substring(1));
			}
			if(email.substring(0, 1).equals("a"))
			{
				request.setAttribute("AutoreLog",email.substring(1));
			}
			if(email.substring(0, 1).equals("m"))
			{
				request.setAttribute("ModeratoreLog",email.substring(1));
			}
			String url = "login.jsp"; // url della jsp
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		email = request.getParameter("email");
		String password = request.getParameter("password");
		String typeUser = request.getParameter("typeUser");
//		System.out.println("email: " + email + " password: " + password + " tipo utente: " + typeUser);
		if(!controlPassword(password) || !checkEmail(email) || !controlTypeUser(typeUser))
		{
			//Se era giusto il tipo utente lo setto.
			if(controlTypeUser(typeUser))
			{
				request.setAttribute("checked", typeUser+"Check");
			}
			// errore nell'inserimento dei dati da parte dell'utente
			// mandiamo l'errore alla jsp 
			String url = "login.jsp"; // url della jsp
			request.setAttribute("errore", "DATI_ERRATI");
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
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
							String url = "login.jsp"; // url della jsp
							request.setAttribute("errore", "DATI_ERRATI");
							url = response.encodeRedirectURL(url);
							RequestDispatcher dispatcher = request.getRequestDispatcher(url);
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
									//Sessione
									synchronized(session) 
									{
										session.setAttribute("Utente", emailDB);
										session.setAttribute("Username", user.getUsername());
										//setting session to expiry in 30 mins
										session.setMaxInactiveInterval(30*60);
										//Cookie
										Cookie emailCookie = new Cookie("Utente", emailDB);
										Cookie usernameCookie = new Cookie("Username", user.getUsername());
										response.addCookie(emailCookie);
										response.addCookie(usernameCookie);
										//Reindirizzo l'utente loggato all'homepage
										String encodedURL = response.encodeRedirectURL("homepage.jsp");
										response.sendRedirect(encodedURL);
									}
								}
								else
								{
									String url = "login.jsp"; // url della jsp
									request.setAttribute("errore", "DATI_ERRATI");
									url = response.encodeRedirectURL(url);
									RequestDispatcher dispatcher = request.getRequestDispatcher(url);
									dispatcher.forward(request, response);
								}
							}
							else
							{
								String url = "login.jsp"; // url della jsp
								request.setAttribute("errore", "DATI_ERRATI");
								url = response.encodeRedirectURL(url);
								RequestDispatcher dispatcher = request.getRequestDispatcher(url);
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
							String url = "login.jsp"; // url della jsp
							request.setAttribute("errore", "DATI_ERRATI");
							RequestDispatcher dispatcher = request.getRequestDispatcher(url);
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
									// l'utente ha effettuato l'accesso con successo
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
										String encodedURL = response.encodeRedirectURL("homepage.jsp");
										response.sendRedirect(encodedURL);
									}
								}
								else
								{
									String url = "login.jsp"; // url della jsp
									request.setAttribute("errore", "DATI_ERRATI");
									RequestDispatcher dispatcher = request.getRequestDispatcher(url);
									dispatcher.forward(request, response);
								}
							}
							else
							{
								String url = "login.jsp"; // url della jsp
								request.setAttribute("errore", "DATI_ERRATI");
								RequestDispatcher dispatcher = request.getRequestDispatcher(url);
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
							String url = "login.jsp"; // url della jsp
							request.setAttribute("errore", "DATI_ERRATI");
							url = response.encodeRedirectURL(url);
							RequestDispatcher dispatcher = request.getRequestDispatcher(url);
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
									// l'utente ha effettuato l'accesso con successo
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
										String encodedURL = response.encodeRedirectURL("homepage.jsp");
										response.sendRedirect(encodedURL);
									}
								}
								else
								{
									String url = "login.jsp"; // url della jsp
									request.setAttribute("errore", "DATI_ERRATI");
									RequestDispatcher dispatcher = request.getRequestDispatcher(url);
									dispatcher.forward(request, response);
								}
							}
							else
							{
								String url = "login.jsp"; // url della jsp
								request.setAttribute("errore", "DATI_ERRATI");
								RequestDispatcher dispatcher = request.getRequestDispatcher(url);
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
