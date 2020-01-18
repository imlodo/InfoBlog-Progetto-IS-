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
import model.bean.Conversazione;
import model.bean.Messagio;
import model.manager.ConversazioniManagment;
import model.manager.MessaggioManagment;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class Chat
 */
@WebServlet("/Chat")
public class Chat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Chat() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		ArrayList<String> contatti=new ArrayList<String>();
		ArrayList<Conversazione> conv=new ArrayList<Conversazione>();
		MessaggioManagment DAOMessaggio=new MessaggioManagment(new DriverManagerConnectionPool());
		String url="chat.jsp";
		if(session.getAttribute("Moderatore")==null && (session.getAttribute("Autore")!=null) || session.getAttribute("Utente")!=null)
		{
			synchronized (session)
			{
				if(session.getAttribute("Autore")!=null)
				{
					String autore=(String) session.getAttribute("Autore");



					try 
					{
						if(contatti.size()>0)
						{
							contatti=ConversazioniManagment.getUtenti("R:"+autore);
							for(int i=0;i<contatti.size();i++)
							{
								ArrayList<Messagio> messaggi=(ArrayList<Messagio>)DAOMessaggio.doRetrieveAll(autore+" "+contatti.get(i));
								Conversazione cconv=new Conversazione();
								for(int j=0;j<messaggi.size();j++)
								{
									cconv.setMessaggi(messaggi.get(j));
								}
								conv.add(cconv);
							}

							if(conv.size()>0)
							{
								if(!conv.get(0).getMessaggi().get(conv.get(0).getMessaggi().size()-1).getMittente().equals(autore) && conv.get(0).getMessaggi().get(conv.get(0).getMessaggi().size()-1).getStato().equals("inviato"))
								{
									conv.get(0).getMessaggi().get(conv.get(0).getMessaggi().size()-1).setTipologia("messaggio");
									DAOMessaggio.doUpdate(conv.get(0).getMessaggi().get(conv.get(0).getMessaggi().size()-1));
									conv.remove(0);
									ArrayList<Messagio> messaggi=(ArrayList<Messagio>)DAOMessaggio.doRetrieveAll(autore+" "+contatti.get(0));
									Conversazione cconv=new Conversazione();
									for(int j=0;j<messaggi.size();j++)
									{
										cconv.setMessaggi(messaggi.get(j));
									}
									conv.add(0,cconv);
								}
							}

							session.setAttribute("chat",conv);
							request.setAttribute("contatti", contatti);
							RequestDispatcher dispatcher = request.getRequestDispatcher(url);
							dispatcher.forward(request, response);
							return;
						}
						else
						{
							request.setAttribute("Vuoto", "Nessuna conversazione");
							session.setAttribute("chat",conv);
							request.setAttribute("contatti", contatti);
							RequestDispatcher dispatcher = request.getRequestDispatcher(url);
							dispatcher.forward(request, response);
							return;
						}
					}
					catch (SQLException e) 
					{
						e.printStackTrace();
					}

				}
				else
				{
					String utente=(String) session.getAttribute("Utente");



					try 
					{
						contatti=ConversazioniManagment.getUtenti("M:"+utente);
						if(contatti.size()>0)
						{
							for(int i=0;i<contatti.size();i++)
							{
								ArrayList<Messagio> messaggi=(ArrayList<Messagio>)DAOMessaggio.doRetrieveAll(utente+" "+contatti.get(i));
								Conversazione cconv=new Conversazione();
								for(int j=0;j<messaggi.size();j++)
								{
									cconv.setMessaggi(messaggi.get(j));
								}
								conv.add(cconv);
							}

							if(conv.size()>0)
							{
								if(!conv.get(0).getMessaggi().get(conv.get(0).getMessaggi().size()-1).getMittente().equals(utente) && conv.get(0).getMessaggi().get(conv.get(0).getMessaggi().size()-1).getStato().equals("inviato"))
								{
									conv.get(0).getMessaggi().get(conv.get(0).getMessaggi().size()-1).setTipologia("risposta");
									DAOMessaggio.doUpdate(conv.get(0).getMessaggi().get(conv.get(0).getMessaggi().size()-1));

									conv.remove(0);
									ArrayList<Messagio> messaggi=(ArrayList<Messagio>)DAOMessaggio.doRetrieveAll(utente+" "+contatti.get(0));
									Conversazione cconv=new Conversazione();
									for(int j=0;j<messaggi.size();j++)
									{
										cconv.setMessaggi(messaggi.get(j));
									}
									conv.add(0,cconv);
								}
							}
							session.setAttribute("chat",conv);
							request.setAttribute("contatti", contatti);
							RequestDispatcher dispatcher = request.getRequestDispatcher(url);
							dispatcher.forward(request, response);
						}

						else
						{
							request.setAttribute("Vuoto", "Nessuna conversazione");
							session.setAttribute("chat",conv);
							request.setAttribute("contatti", contatti);
							RequestDispatcher dispatcher = request.getRequestDispatcher(url);
							dispatcher.forward(request, response);
							return;
						}
					}
					catch (SQLException e) 
					{
						e.printStackTrace();
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
