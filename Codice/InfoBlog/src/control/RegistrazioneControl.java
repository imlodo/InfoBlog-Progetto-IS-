package control;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Autore;
import model.bean.Moderatore;
import model.bean.Utente;
import model.manager.AutoreManagement;
import model.manager.ModeratoreManagement;
import model.manager.UserManagement;
import storage.DriverManagerConnectionPool;
import utils.Utils;

/**
 * Servlet implementation class RegistrazioneControl
 */
@WebServlet("/RegistrazioneControl")
public class RegistrazioneControl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String regPage = "registrazione.jsp";
	private static final String notFoundPage = "notfound.jsp";

	public RegistrazioneControl() 
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(!(Utils.checkLogin(request.getSession(), request.getCookies()) == null))
		{
			response.sendRedirect(notFoundPage);
			return;
		}
		//Prendo i parametri passati alla servlet
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String type = request.getParameter("typeUser");

		//Setto i parametri passati per non perdere lo stato in caso di errore
		if(nome != null)
			request.setAttribute("nome", nome);
		if(cognome != null)
			request.setAttribute("cognome", cognome);
		if(email != null)
			request.setAttribute("email", email);
		if(username != null)
			request.setAttribute("username", username);
		if(password != null)
			request.setAttribute("password", password);
		if(type != null)
			request.setAttribute("checked", type+"Check");

		//Serve a controllare i parametri passati alla servlet
		boolean cname = Utils.checkName(nome);
		boolean ccognome = Utils.checkName(cognome);
		boolean cemail = Utils.checkEmail(email);
		boolean cusername = Utils.checkUsername(username);
		boolean cpassword = Utils.controlPassword(password);
		boolean ctype = Utils.controlTypeUser(type);

		if(!cname || !ccognome || !cemail || !cusername || !cpassword || !ctype)
		{
			// errore nell'inserimento dei dati da parte dell'utente
			// mandiamo l'errore alla jsp
			request.setAttribute("errore", "FORMATO_DATI_ERRATI");
			RequestDispatcher dispatcher = request.getRequestDispatcher(regPage);
			dispatcher.forward(request, response);
			return;
		}

		//Se tutti i controlli precedenti sono stati superati, si può provare a registrare l'account.
		else
		{
			//Istanzio il pool
			DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
			//Controllo che l'email e l'username non siano già stati utilizzati da nessun utente.
			UserManagement userDM = new UserManagement(pool);
			ModeratoreManagement moderatoreDM = new ModeratoreManagement(pool);
			AutoreManagement autoreDM = new AutoreManagement(pool);

			Utente u = null;
			Moderatore m = null;
			Autore a = null;
			try
			{
				u = userDM.doRetrieveByKey(email);
				m = moderatoreDM.doRetrieveByKey(email);
				a = autoreDM.doRetrieveByKey(email);
				boolean found = false;
				if((m.getEmail()!= null) || (u != null) || (a.getEmail() != null)) 
				{
					found = true;
				}
				else
				{
					ArrayList<Utente> utenti =  (ArrayList<Utente>) userDM.doRetrieveAll("username");
					for(Utente user : utenti)
					{
						if(user.getUsername().equals(username))
						{
							found=true;
						}
					}

					ArrayList<Autore> autori =  (ArrayList<Autore>) autoreDM.doRetrieveAll("username");
					for(Autore aut : autori)
					{
						if(aut.getUsername().equals(username))
						{
							found=true;
						}
					}

					ArrayList<Moderatore> moderatori =  (ArrayList<Moderatore>) moderatoreDM.doRetrieveAll("username");
					for(Moderatore mod : moderatori)
					{
						if(mod.getUsername().equals(username))
						{
							found=true;
						}
					}
				}
				if(found)
				{
					// Dati già presenti
					// mandiamo l'errore alla jsp 
					request.setAttribute("errore", "DATI_PRESENTI");
					RequestDispatcher dispatcher = request.getRequestDispatcher(regPage);
					dispatcher.forward(request, response);
					return;
				}
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try
		{
			DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
			switch(type)
			{
			case "Utente":
			{
				//Istanzio il manager per l'utente
				UserManagement managerUser = new UserManagement(pool);
				//Creo l'utente
				Utente user = new Utente(email, password, nome, cognome, username);
				//Provo a salvare l'utente
				managerUser.doSave(user);
				//Se tutto è andato a buon fine Visualizzo un messaggio di successo registrazione
				ServletOutputStream out = response.getOutputStream();
				response.setContentType("text/html");
				out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() +  "/css/successoRegistrazione.css' />");
				out.print(
						"<div class='container'>"+
								"<div class='subContainer'>"+
								"<h1 id='success'>Account Utente registrato con successo!</h1>"+
								"<table>"+
								"<caption>Riepilogo dati registrazione</caption>"+
								"<thead>"+
								"<tr>"+
								"<th scope='col'>Nome</th>"+
								"<th scope='col'>Cognome</th>"+
								"<th scope='col'>Email</th>"+
								"<th scope='col'>Username</th>"+
								"<th scope='col'>Password</th>"+
								"</tr>"+
								"</thead>"+
								"<tbody>"+
								"<tr>"+
								"<td data-label='Nome'>"+nome+"</td>"+
								"<td data-label='Cognome'>"+cognome+"</td>"+
								"<td data-label='Email'>"+email+"</td>"+
								"<td data-label='Username'>"+username+"</td>"+
								"<td data-label='Password'><input type='password' value="+password+"/></td>"+
								"</tr>"+
								"</tbody>"+
								"</table>"+
								"<p class='link'><a href='/InfoBlog/'>Torna alla HomePage</a><a href='/InfoBlog/login.jsp'>Vai al login</a></p></div>"+
						"</div>");
				request.setAttribute("success", "ok");
			}break;

			case "Autore":
			{
				AutoreManagement managerAutore = new AutoreManagement(pool);
				//Creo l'autore
				Autore autore = new Autore(email, password, nome, cognome, username);
				//Provo a salvare l'autore
				managerAutore.doSave(autore);
				ServletOutputStream out = response.getOutputStream();
				response.setContentType("text/html");
				out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() +  "/css/successoRegistrazione.css' />");
				//Se tutto è andato a buon fine Visualizzo un messaggio di successo registrazione
				out.print(
						"<div class='container'>"+
								"<div class='subContainer'>"+
								"<h1 id='success'>Account Autore registrato con successo!</h1>"+
								"<table>"+
								"<caption>Riepilogo dati registrazione</caption>"+
								"<thead>"+
								"<tr>"+
								"<th scope='col'>Nome</th>"+
								"<th scope='col'>Cognome</th>"+
								"<th scope='col'>Email</th>"+
								"<th scope='col'>Username</th>"+
								"<th scope='col'>Password</th>"+
								"</tr>"+
								"</thead>"+
								"<tbody>"+
								"<tr>"+
								"<td data-label='Nome'>"+nome+"</td>"+
								"<td data-label='Cognome'>"+cognome+"</td>"+
								"<td data-label='Email'>"+email+"</td>"+
								"<td data-label='Username'>"+username+"</td>"+
								"<td data-label='Password'><input type='password' value="+password+"/></td>"+
								"</tr>"+
								"</tbody>"+
								"</table>"+
								"<p class='link'><a href='/InfoBlog/'>Torna alla HomePage</a><a href='/InfoBlog/login.jsp'>Vai al login</a></p></div>"+
						"</div>");
				request.setAttribute("success", "ok");
			}break;
			}

		}
		catch (SQLException e)
		{
			// Dati già presenti
			// mandiamo l'errore alla jsp 
			request.setAttribute("errore", "DATI_PRESENTI");
			RequestDispatcher dispatcher = request.getRequestDispatcher(regPage);
			dispatcher.forward(request, response);
		}		
	}
}
