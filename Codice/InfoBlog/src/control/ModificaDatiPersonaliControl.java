package control;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import exception.DatiEsistentiException;
import model.bean.Autore;
import model.bean.Moderatore;
import model.bean.Utente;
import model.manager.AutoreManagement;
import model.manager.ModeratoreManagement;
import model.manager.UserManagement;
import storage.DriverManagerConnectionPool;
import utils.Utils;

/**
 * Servlet implementation class ModificaDatiPersonaliControl
 */
@WebServlet("/ModificaDatiPersonaliControl")
public class ModificaDatiPersonaliControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String notFoundPage = "notfound.jsp";
	private static final String profilePage = "profile.jsp";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModificaDatiPersonaliControl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		String emailSession = Utils.checkLogin(request.getSession(), request.getCookies());
		if(emailSession == null)
		{
			// invocata servlet senza essersi autenticato
			// reindirizzo alla pagina not_found
			response.sendRedirect(notFoundPage);
			return;
		}
		//Prendo i parametri passati alla servlet
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		//Serve a controllare i parametri passati alla servlet
		boolean cname = Utils.checkName(nome);
		boolean ccognome = Utils.checkName(cognome);
		boolean cusername = Utils.checkUsername(username);
		boolean cpassword = Utils.controlPassword(password);

		if(!cname || !ccognome || !cusername || !cpassword)
		{
			// errore nell'inserimento dei dati da parte dell'utente
			request.setAttribute("errore", "FORMATO_DATI_ERRATI");
			RequestDispatcher dispatcher = request.getRequestDispatcher(profilePage);
			dispatcher.forward(request, response);
			return;
		}
		
		//Se tutti i controlli precedenti sono stati superati, si può provare ad aggiornare l'account.
		//Istanzio il pool
		DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
		//Controllo che l'email e l'username non siano già stati utilizzati da nessun utente.
		UserManagement userDM = new UserManagement(pool);
		ModeratoreManagement moderatoreDM = new ModeratoreManagement(pool);
		AutoreManagement autoreDM = new AutoreManagement(pool);
		try
		{
			Utente utente = userDM.doRetrieveByKey(emailSession.substring(1));
			if(utente.getUsername().equals(username) && utente.getCognome().equals(cognome) && utente.getNome().equals(nome) && utente.getPassword().equals(password))
			{
				// errore nell'inserimento dei dati da parte dell'utente
				request.setAttribute("errore", "MODIFICHE_ASSENTI");
				RequestDispatcher dispatcher = request.getRequestDispatcher(profilePage);
				dispatcher.forward(request, response);
				return;
			}	
			//Se l'username è diverso da quello della sessione(lo sta modificando), cerco se è già presenre
			if(!username.equals(request.getSession().getAttribute("Username")))
			{
				ArrayList<Utente> utenti =  (ArrayList<Utente>) userDM.doRetrieveAll("username");
				for(Utente user : utenti)
				{
					if(user.getUsername().equals(username))
					{
						throw new DatiEsistentiException("existUsername");
					}
				}

				ArrayList<Autore> autori =  (ArrayList<Autore>) autoreDM.doRetrieveAll("username");
				for(Autore aut : autori)
				{
					if(aut.getUsername().equals(username))
					{
						throw new DatiEsistentiException("existUsername");
					}
				}

				ArrayList<Moderatore> moderatori =  (ArrayList<Moderatore>) moderatoreDM.doRetrieveAll("username");
				for(Moderatore mod : moderatori)
				{
					if(mod.getUsername().equals(username))
					{
						throw new DatiEsistentiException("existUsername");
					}
				}
			}
			HttpSession session = request.getSession();
			if(emailSession.substring(0,1).equals("u"))
			{
				Utente user = new Utente(emailSession.substring(1),password,nome,cognome,username);
				userDM.doUpdate(user);
				synchronized(session) 
				{
					//Risetto la sessione e cookie
					session.setAttribute("Username", user.getUsername());
					Cookie usernameCookie = new Cookie("Username", user.getUsername());
					response.addCookie(usernameCookie);
				}
			}
			if(emailSession.substring(0,1).equals("a"))
			{
				Autore autore = new Autore(emailSession.substring(1),password,nome,cognome,username);
				autoreDM.doUpdate(autore);
				synchronized(session) 
				{
					//Risetto la sessione e cookie
					session.setAttribute("Username", autore.getUsername());
					Cookie usernameCookie = new Cookie("Username", autore.getUsername());
					response.addCookie(usernameCookie);
				}
				
			}
			if(emailSession.substring(0,1).equals("m"))
			{
				//Serve per non far modificare la categoria
				Moderatore mod = moderatoreDM.doRetrieveByKey(emailSession.substring(1));
				mod.setNome(nome);
				mod.setCognome(cognome);
				mod.setUsername(username);
				mod.setPassword(password);
				moderatoreDM.doUpdate(mod);
				synchronized(session) 
				{
					//Risetto la sessione e cookie
					session.setAttribute("Username", mod.getUsername());
					Cookie usernameCookie = new Cookie("Username", mod.getUsername());
					response.addCookie(usernameCookie);
				}
			}
			request.setAttribute("success", "MODIFICA_SUCCESS");
			RequestDispatcher dispatcher = request.getRequestDispatcher(profilePage);
			dispatcher.forward(request, response);
		}
		catch(Exception e)
		{
			if(e.getMessage().equals("existUsername"))
			{
				request.setAttribute("errore", "DATI_PRESENTI");
			}
			else
			{
				// Dati già presenti
				request.setAttribute("errore", "FORMATO_DATI_ERRATI");
			}
			// mandiamo l'errore alla jsp 
			RequestDispatcher dispatcher = request.getRequestDispatcher(profilePage);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
