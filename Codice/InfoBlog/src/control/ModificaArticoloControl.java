package control;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.bean.Allegato;
import model.bean.Articolo;
import model.bean.Moderatore;
import model.bean.Notifica;
import model.manager.AllegatoManagement;
import model.manager.ArticoloManagement;
import model.manager.ModeratoreManagement;
import model.manager.NotificaManagement;
import storage.DriverManagerConnectionPool;
import utils.Utils;

@WebServlet("/ModificaArticoloControl")
@MultipartConfig
public class ModificaArticoloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String notFoundPage = "notfound.jsp";
	private static final String modificaPage = "modificaArticolo.jsp";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModificaArticoloControl() 
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Controllo se l'utente � loggato ed � un autore
		String emailSession = Utils.checkLogin(request.getSession(), request.getCookies());
		if(emailSession == null || !emailSession.subSequence(0, 1).equals("a"))
		{
			// invocata servlet senza essersi autenticato
			// reindirizzo alla pagina not_found
			response.sendRedirect(notFoundPage);
			return;
		}
		
		Articolo articolo = null;
		String idArticolo = request.getParameter("idArticolo");
		DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
		if(idArticolo != null)
		{
			try
			{
				ArticoloManagement articoloDM = new ArticoloManagement(pool);
				articolo = articoloDM.doRetrieveByKey(idArticolo);
				request.setAttribute("articolo", articolo);
			}
			catch (SQLException e)
			{
				
				e.printStackTrace();
			}
		
			String action = request.getParameter("action"); 
			if(action != null && action.equals("richiestaModifica"))
			{
			
				if(articolo.getAutore() != null)
				{	if(articolo.getAutore().getEmail().equals(emailSession.substring(1)))
					{
						RequestDispatcher dispatcher = request.getRequestDispatcher(modificaPage);
						dispatcher.forward(request, response);
						return;
					}
					else
					{
						// mandiamo l'errore alla jsp 
						request.setAttribute("errore", "ERRORE_ID");
						RequestDispatcher dispatcher = request.getRequestDispatcher(notFoundPage);
						dispatcher.forward(request, response);
						return;
					}	
				}
			}
			else
			{
				boolean modificato = false;
				//Effettuata commit modifiche
				String titoloArticolo = request.getParameter("titolo");
				String contenutoArticolo = request.getParameter("contenuto");
				String categoriaArticolo = request.getParameter("categoria");
				String fileDaCancellare [] = request.getParameterValues("fileDelete");
				if(fileDaCancellare != null)
				{
					for(String e: fileDaCancellare)
					{
						Allegato daCancellare = new Allegato(getServletContext().getInitParameter("allegati")+"\\"+e,Integer.parseInt(idArticolo));
						//da qui se ne occupa CaricaAllegatoControl
						request.setAttribute("allegato", daCancellare);
						RequestDispatcher dispatcher = request.getRequestDispatcher("CancellaAllegatoControl");
						dispatcher.include(request, response);
//						System.out.println("Sono tornato nel control pubblicazione");
						String errorDelete = (String) request.getAttribute("errore");
						if(errorDelete != null)
						{
							// mandiamo l'errore alla jsp 
							request.setAttribute("errore", errorDelete);
							dispatcher = request.getRequestDispatcher(modificaPage);
							dispatcher.forward(request, response);
							return;
						}
						modificato = true;
					}
				}
				//Prendo i file
				ArrayList<Part> files = (ArrayList<Part>) request.getParts();
				ArrayList<Part> fileDaCaricare = new ArrayList<Part>();
				for(Part el : files)
				{
					String name = el.getSubmittedFileName();
					if(name != null && !name.equals(""))
					{
						
						if(!Utils.checkFormato(name))
						{
							// mandiamo l'errore alla jsp 
							request.setAttribute("errore", "FORMATO_ALLEGATI_ERRATO");
							RequestDispatcher dispatcher = request.getRequestDispatcher(modificaPage);
							dispatcher.forward(request, response);
							return;
						}
						//� un file pdf, word o wordx, posso caricarlo
						fileDaCaricare.add(el);
					}	
				}
				
				//da qui se ne occupa CaricaAllegatoControl
				request.setAttribute("files", fileDaCaricare);
				request.setAttribute("id", articolo.getId());
				RequestDispatcher dispatcher = request.getRequestDispatcher("CaricaAllegatoControl");
				dispatcher.include(request, response);
//				System.out.println("Sono tornato nel control pubblicazione");
				String errorUpload = (String) request.getAttribute("errorUpload");
				if(errorUpload != null)
				{
					// mandiamo l'errore alla jsp 
					request.setAttribute("errore", errorUpload);
					dispatcher = request.getRequestDispatcher(modificaPage);
					dispatcher.forward(request, response);
					return;
				}
				
				if((int) request.getAttribute("successUpload") > 0)
				{
//					System.out.print((int) request.getAttribute("successUpload"));
					modificato = true;
				}
				
				//Se � stata fatta una modifica
				if(!titoloArticolo.equals(articolo.getTitolo()) || !contenutoArticolo.equals(articolo.getContenuto()) || !categoriaArticolo.equals(articolo.getCategoria()))
				{
					modificato = true;
				}
				
				if(modificato == true)
				{
					String statoPrecedente = articolo.getStato();
					
					articolo.setTitolo(titoloArticolo);
					articolo.setContenuto(contenutoArticolo);
					articolo.setCategoria(categoriaArticolo);
					articolo.setStato("daPubblicare");
					ArticoloManagement articoloDM = new ArticoloManagement(pool);
					try
					{
						articoloDM.doUpdate(articolo);
						//Controllo che sia presente almeno un allegato
						AllegatoManagement allegatoDM = new AllegatoManagement(pool);
						ArrayList<Allegato> a = (ArrayList<Allegato>) allegatoDM.doRetrieveByID(articolo.getId());
						//Non ci sono allegati presenti, setto l'errore e rimando alla pagina di modifica.
						if(a.size() == 0)
						{
							articolo.setModeratore(null);
							articoloDM.doUpdate(articolo);
							// mandiamo l'errore alla jsp 
							request.setAttribute("errore", "NESSUN_ALLEGATO");
							dispatcher = request.getRequestDispatcher(modificaPage);
							dispatcher.forward(request, response);
							return;
						}
						else
						{
							ModeratoreManagement moderatoreDM = new ModeratoreManagement(pool);
							Moderatore modRiceventeNotifica = Utils.getRiceventeNotifica(moderatoreDM,categoriaArticolo,articoloDM);
							articolo.setModeratore(modRiceventeNotifica);
							articoloDM.doUpdate(articolo);
						}
						if(!statoPrecedente.equals("daPubblicare"))
						{
							Articolo articoloDB = articoloDM.doRetrieveByKey(String.valueOf(articolo.getId()));
							Notifica notifica = null;
							if(articoloDB.getModeratore() == null)
							{
								notifica = new Notifica("Richiesta_pubblicazione_articolo",articolo.getAutore().getEmail(),articolo.getModeratore().getEmail());
							}
							else
							{
								notifica = new Notifica("Richiesta_pubblicazione_articolo",articolo.getAutore().getEmail(),articolo.getModeratore().getEmail());
							}
							NotificaManagement notificaDM = new NotificaManagement(pool);
							if(notifica != null)
								notificaDM.doSave(notifica);
						}
						//Inutile inviare la notifica � gi� stata inviata precedentemente.
						String url = "ArticleShowServlet"; // url del control visualizza
						dispatcher = request.getRequestDispatcher(url);
						dispatcher.forward(request, response);
						return;
					}
					catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					// mandiamo l'errore alla jsp 
					request.setAttribute("errore", "MODIFICHE_ASSENTI");
					dispatcher = request.getRequestDispatcher(modificaPage);
					dispatcher.forward(request, response);
					return;
				}
			}
		}
		else
		{
			// mandiamo l'errore alla jsp 
			String url = "notfound.jsp"; // url della jsp
			request.setAttribute("errore", "ERRORE_ID");
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
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
