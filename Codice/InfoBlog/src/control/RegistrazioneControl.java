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
 * Servlet implementation class RegistrazioneControl
 */
@WebServlet("/RegistrazioneControl")
public class RegistrazioneControl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Prendo i parametri passati alla servlet
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String type = request.getParameter("typeUser");
		
		//Serve a controllare i parametri passati alla servlet
		boolean cname = Utils.checkName(nome);
		boolean ccognome = Utils.checkName(cognome);
		boolean cemail = Utils.checkEmail(email);
		boolean cusername = Utils.checkUsername(username);
		boolean cpassword = Utils.controlPassword(password);
		boolean ctype = Utils.controlTypeUser(type);
		
		if(!cname || !ccognome || !cemail || !cusername || !cpassword || !ctype)
		{
			//Se era giusto il tipo utente lo setto nella request.
			if(Utils.controlTypeUser(type))
			{
				request.setAttribute("checked", type+"Check");
			}
			// errore nell'inserimento dei dati da parte dell'utente
			// mandiamo l'errore alla jsp 
			String url = "registrazione.jsp"; // url della jsp
			request.setAttribute("errore", "FORMATO_DATI_ERRATI");
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
		
		//Se tutti i controlli precedenti sono stati superati, si può provare a registrare l'account.
		else
		{
			//Serve a salvare il tipo di account che si sta registrando(utente/autore)
			request.setAttribute("checked", type+"Check");
			//Istanzio il pool
			DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
			//Controllo che l'email e l'username non siano già stati utilizzati da nessun utente.
			UserManagement userDM = new UserManagement(pool);
			ModeratoreManagement moderatoreDM = new ModeratoreManagement(pool);
			AutoreManagement autoreDM = new AutoreManagement(pool);
			try
			{
				Utente u = userDM.doRetrieveByKey(email);
				Moderatore m = moderatoreDM.doRetrieveByKey(email);
				Autore a = autoreDM.doRetrieveByKey(email);
				if((m.getNome() != null) || (u.getNome() != null) || (a.getNome() != null)) 
				{
					throw new DatiEsistentiException("Email già esistente");
				}
				else
				{
					ArrayList<Utente> utenti =  (ArrayList<Utente>) userDM.doRetrieveAll("username");
					for(Utente user : utenti)
					{
						if(user.getUsername().equals(username))
						{
							throw new DatiEsistentiException("Username già esistente");
						}
					}
					
					ArrayList<Autore> autori =  (ArrayList<Autore>) autoreDM.doRetrieveAll("username");
					for(Autore aut : autori)
					{
						if(aut.getUsername().equals(username))
						{
							throw new DatiEsistentiException("Username già esistente");
						}
					}
					
					ArrayList<Moderatore> moderatori =  (ArrayList<Moderatore>) moderatoreDM.doRetrieveAll("username");
					for(Moderatore mod : moderatori)
					{
						if(mod.getUsername().equals(username))
						{
							throw new DatiEsistentiException("Username già esistente");
						}
					}
				}
			}
			catch(Exception e)
			{
				// Dati già presenti
				// mandiamo l'errore alla jsp 
				String url = "registrazione.jsp"; // url della jsp
				request.setAttribute("errore", "DATI_PRESENTI");
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
				return;
			}
			try
			{
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
						out.print("<h1 id='success'>Account Utente registrato con successo!</h1>\r\n" + 
								"	<table>\r\n" + 
								"		<caption>Riepilogo dati registrazione</caption>\r\n" + 
								"		<tr>\r\n" + 
								"			<th>Nome</th>\r\n" + 
								"			<th>Cognome</th>\r\n" + 
								"			<th>Email</th>\r\n" + 
								"			<th>Username</th>\r\n" + 
								"			<th>Password</th>\r\n" + 
								"		</tr>\r\n" + 
								"		<tr>\r\n" + 
								"			<td>"+nome+"</td>\r\n" + 
								"			<td>"+cognome+"</td>\r\n" + 
								"			<td>"+email+"</td>\r\n" + 
								"			<td>"+username+"</td>\r\n" + 
								"			<td>"+password+"</td>\r\n" + 
								"		</tr>\r\n" + 
								"	</table>\r\n" + 
								"	<p><a href='/InfoBlog/'>Torna alla HomePage</a></p>");
					}break;
					
					case "Autore":
					{
						AutoreManagement managerAutore = new AutoreManagement(pool);
						//Creo l'autore
						Autore autore = new Autore(email, password, nome, cognome, username);
						//Provo a salvare l'autore
						managerAutore.doSave(autore);
						ServletOutputStream out = response.getOutputStream();
						//Se tutto è andato a buon fine Visualizzo un messaggio di successo registrazione
						out.print("<h1 id='success'>Account Autore registrato con successo!</h1>\r\n" + 
								"	<table>\r\n" + 
								"		<caption>Riepilogo dati registrazione</caption>\r\n" + 
								"		<tr>\r\n" + 
								"			<th>Nome</th>\r\n" + 
								"			<th>Cognome</th>\r\n" + 
								"			<th>Email</th>\r\n" + 
								"			<th>Username</th>\r\n" + 
								"			<th>Password</th>\r\n" + 
								"		</tr>\r\n" + 
								"		<tr>\r\n" + 
								"			<td>"+nome+"</td>\r\n" + 
								"			<td>"+cognome+"</td>\r\n" + 
								"			<td>"+email+"</td>\r\n" + 
								"			<td>"+username+"</td>\r\n" + 
								"			<td><input type='password' value="+password+"/></td>\r\n" + 
								"		</tr>\r\n" + 
								"	</table>\r\n" + 
								"	<p><a href='/InfoBlog/'>Torna alla HomePage</a></p>");
					}break;
				}
			
			}
			catch (SQLException e)
			{
				// Dati già presenti
				// mandiamo l'errore alla jsp 
				String url = "registrazione.jsp"; // url della jsp
				request.setAttribute("errore", "DATI_PRESENTI");
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
			}		
		}
	}
	
}