package control;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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
import model.bean.Autore;
import model.bean.Moderatore;
import model.bean.Notifica;
import model.manager.ArticoloManagement;
import model.manager.AutoreManagement;
import model.manager.ModeratoreManagement;
import model.manager.NotificaManagement;
import storage.DriverManagerConnectionPool;
import utils.Utils;

/**
 * Servlet implementation class PubblicazioneControl
 */
@WebServlet("/PubblicazioneControl")
@MultipartConfig()
public class PubblicazioneControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String notFoundPage = "notfound.jsp";
    private static final String pubPage = "richiestapubblicazione.jsp";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PubblicazioneControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Controllo se l'utente è loggato
		String emailSession = Utils.checkLogin(request.getSession(), request.getCookies());
		if(emailSession == null || !emailSession.subSequence(0, 1).equals("a"))
		{
			// invocata servlet senza essersi autenticato
			// reindirizzo alla pagina not_found
			response.sendRedirect(notFoundPage);
			return;
		}
		
		
		//Prendo il titolo passato
		String titoloArticolo = request.getParameter("titolo");
		//Prendo il contenuto passato
		String contenutoArticolo = request.getParameter("contenuto");
		//Prendo la categoria passata
		String categoriaArticolo = request.getParameter("categoria");
		
		//Serve per non perdere lo stato della jsp, in caso di fallimento
		if(titoloArticolo != null)
			request.setAttribute("titolo", titoloArticolo);
		if(contenutoArticolo != null)
			request.setAttribute("contenuto", contenutoArticolo);
		
		if(!Utils.checkTitolo(titoloArticolo) || !Utils.checkContenuto(contenutoArticolo) || !Utils.checkCategoria(categoriaArticolo))
		{
			request.setAttribute("errore", "FORMATO_DATI_ERRATO");
			RequestDispatcher dispatcher = request.getRequestDispatcher(pubPage);
			dispatcher.forward(request, response);
			return;
		}
		DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
		ArticoloManagement articoloDM = new ArticoloManagement(pool);
		ArrayList<Articolo> articoli;
		try
		{
			articoli = (ArrayList<Articolo>) articoloDM.doRetrieveAll("");
			for(Articolo art : articoli)
			{
				if(art.getTitolo().equals(titoloArticolo))
				{
					request.setAttribute("errore", "TITOLO_PRESENTE");
					RequestDispatcher dispatcher = request.getRequestDispatcher(pubPage);
					dispatcher.forward(request, response);
					return;
				}
				if(art.getContenuto().contentEquals(contenutoArticolo))
				{
					request.setAttribute("errore", "CONTENUTO_ARTICOLO_PRESENTE");
					RequestDispatcher dispatcher = request.getRequestDispatcher(pubPage);
					dispatcher.forward(request, response);
					return;
				}
			}
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		//Prendo i file
		ArrayList<Part> files = (ArrayList<Part>) request.getParts();
		ArrayList<Part> fileDaCaricare = new ArrayList<Part>();
		for(Part el : files)
		{
			String name = el.getSubmittedFileName();
			if(name != null)
			{
				
				if(!Utils.checkFormato(name))
				{
					request.setAttribute("errore", "FORMATO_ALLEGATI_ERRATO");
					RequestDispatcher dispatcher = request.getRequestDispatcher(pubPage);
					dispatcher.forward(request, response);
					return;
				}
				//è un file pdf, word o wordx, posso caricarlo
				fileDaCaricare.add(el);
			}	
		}
		//Arrivati a questo punto ho superato tutti i controlli e posso caricare l'articolo con i relativi allegati
		//Prendo l'autore corrente che sta richiedendo la pubblicazione
		AutoreManagement autoreDM = new AutoreManagement(pool);
		try
		{
			Autore autore = autoreDM.doRetrieveByKey(emailSession.substring(1));
			Articolo articolo = new Articolo(titoloArticolo,contenutoArticolo,categoriaArticolo,autore);
			articoloDM.doSave(articolo);
			
			Articolo articoloSalvato = null;
			articoli = (ArrayList<Articolo>) articoloDM.doRetrieveAll("a:"+emailSession.substring(1));
			for(Articolo art: articoli)
			{
				//Implementare equals di articolo
				if(articolo.getTitolo().equals(art.getTitolo()))
				{
					articoloSalvato=art;
					break;
				}
			
			}
			if(articoloSalvato != null)
			{
				//da qui se ne occupa CaricaAllegatoControl
				request.setAttribute("files", fileDaCaricare);
				request.setAttribute("id", articoloSalvato.getId());
				RequestDispatcher dispatcher = request.getRequestDispatcher("CaricaAllegatoControl");
				dispatcher.include(request, response);
//				System.out.println("Sono tornato nel control pubblicazione");
				String errorUpload = (String) request.getAttribute("errorUpload");
				if(errorUpload != null)
				{
					articoloDM.doDelete(articoloSalvato);
					// mandiamo l'errore alla jsp
					request.setAttribute("errore", errorUpload);
					dispatcher = request.getRequestDispatcher(pubPage);
					dispatcher.forward(request, response);
					return;
				}
				ModeratoreManagement moderatoreDM = new ModeratoreManagement(pool);
				Moderatore modRiceventeNotifica = Utils.getRiceventeNotifica(moderatoreDM,categoriaArticolo,articoloDM);
				if(modRiceventeNotifica != null)
				{
					Notifica notifica = new Notifica("Richiesta_pubblicazione_articolo",autore.getEmail(), modRiceventeNotifica.getEmail());
					NotificaManagement notificaDM = new NotificaManagement(pool);
					notificaDM.doSave(notifica);
					articoloSalvato.setModeratore(modRiceventeNotifica);
					articoloDM.doUpdate(articoloSalvato);
				}
				else
				{
					articoloDM.doDelete(articoloSalvato);
					for(Part a : fileDaCaricare)
					{
						File uploadsDir = new File(getServletContext().getInitParameter("allegati"));
						String fileName = Paths.get(a.getSubmittedFileName()).getFileName().toString();
					    Allegato delete=new Allegato();
					    delete.setPercorsoFile(new File(uploadsDir,fileName).toString());
						
					    //da qui se ne occupa CancellaAllegatoControl
						request.setAttribute("allegato", delete);
						dispatcher = request.getRequestDispatcher("CancellaAllegatoControl");
						dispatcher.include(request, response);
					}
					// mandiamo l'errore alla jsp 
					request.setAttribute("errore", "NESSUN_MODERATORE_ESISTENTE");
					dispatcher = request.getRequestDispatcher(pubPage);
					dispatcher.forward(request, response);
					return;
				}
				
				
				// forward tutto è andato bene!
				dispatcher = request.getRequestDispatcher("ArticleShowServlet");
				dispatcher.forward(request, response);
				return;
			}
			else
			{
				// mandiamo l'errore alla jsp 
				request.setAttribute("errore", "ERRORE_DB");
				RequestDispatcher dispatcher = request.getRequestDispatcher(pubPage);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
