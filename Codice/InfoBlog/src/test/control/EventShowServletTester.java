package test.control;

import static org.junit.Assert.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import control.EventShowServlet;

public class EventShowServletTester extends Mockito
{

	private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();  
	private HttpServletRequest request = mock(HttpServletRequest.class);       
	private HttpServletResponse response = mock(HttpServletResponse.class);    
	private HttpSession session = mock(HttpSession.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
//	StringWriter stringWriter = new StringWriter();
//    private PrintWriter writer = new PrintWriter(stringWriter);

	//Serve per il forward
	@Before
	public void setup()
	{
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				String key = (String) invocation.getArguments()[0];//getArgumentAt(0, String.class);
				Object value = invocation.getArguments()[1];
				attributes.put(key, value);
				//	            System.out.println("put attribute key="+key+", value="+value);
				return null;
			}
		}).when(request).setAttribute(Mockito.anyString(), Mockito.anyObject());

		Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable 
			{
				String key = (String) invocation.getArguments()[0];
				Object value = attributes.get(key);
				//	            System.out.println("get attribute value for key="+key+" : "+value);
				return value;
			}
		}).when(request).getAttribute(Mockito.anyString()); 
	}
	
	//Da rivedere riga 65 EventShowServlet
	@Test
	public void UtenteNoEventiDisponibiliTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Utente")).thenReturn("testutente2@test.com");
		when(request.getRequestDispatcher("ShowEvent.jsp")).thenReturn(dispatcher);

		new EventShowServlet().doPost(request, response);
		assertNull(request.getAttribute("NoEventi"));
	}
	
	@Test
	public void ViewEventiOrganizzatiAutore() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(request.getRequestDispatcher("ShowEvent.jsp")).thenReturn(dispatcher);

		new EventShowServlet().doPost(request, response);
		assertNotNull(request.getAttribute("eventi"));
	}
	
	@Test
	public void ViewNessunEventoOrganizzatoAutore() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("autorenoarticoli@test.it");
		when(request.getRequestDispatcher("ShowEvent.jsp")).thenReturn(dispatcher);

		new EventShowServlet().doPost(request, response);
		assertNotNull(request.getAttribute("NoEventi"));
	}
	
	
	@Test
	public void AccessoNegatoModeratoreTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Moderatore")).thenReturn("testmod@test.com");
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);

		new EventShowServlet().doPost(request, response);
		assertNotNull(request.getAttribute("Accesso negato"));
	}
}