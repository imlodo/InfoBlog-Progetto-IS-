package test.control;
import static org.junit.Assert.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import control.EventModifyServlet;
public class EventModifyServletTester extends Mockito
{

	private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();  
	private HttpServletRequest request = mock(HttpServletRequest.class);       
	private HttpServletResponse response = mock(HttpServletResponse.class);    
	private HttpSession session = mock(HttpSession.class);
	StringWriter stringWriter = new StringWriter();
    private PrintWriter writer = new PrintWriter(stringWriter);
    ServletOutputStream buffer = mock(ServletOutputStream.class);
    private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
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
	
	@Test
	public void AccessoNegatoTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(response.getWriter()).thenReturn(writer);
		when(response.getOutputStream()).thenReturn(buffer);
		new EventModifyServlet().doPost(request, response);
		assertEquals("Accesso negato",request.getAttribute("errore"));
	}
	
	@Test
	public void DataErrataTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("TitoloEvento");
		when(request.getParameter("via")).thenReturn("Via Casuale 3");
		when(request.getParameter("citta")).thenReturn("Via Casuale 3");
		when(request.getParameter("dataEvento")).thenReturn("Salerno");
		when(request.getParameter("dataEventoPrecedente")).thenReturn("2020-06-06");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi");
		when(request.getRequestDispatcher("ModifyEvent.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new EventModifyServlet().doPost(request, response);
		assertEquals("Formato data errato",request.getAttribute("errore"));
	}
	
	@Test
	public void ModificheAssentiTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("TitoloEvento");
		when(request.getParameter("event-titlePrecedente")).thenReturn("TitoloEvento");
		when(request.getParameter("viaPrecedente")).thenReturn("Via Casuale");
		when(request.getParameter("via")).thenReturn("Via Casuale");
		when(request.getParameter("cittaPrecedente")).thenReturn("Citta Casuale");
		when(request.getParameter("citta")).thenReturn("Citta Casuale");
		when(request.getParameter("dataEvento")).thenReturn("2020-06-06");
		when(request.getParameter("dataEventoPrecedente")).thenReturn("2020-06-06");
		when(request.getParameter("event-argumentPrecedente")).thenReturn("Parliamo di algortmi");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi");
		when(request.getRequestDispatcher("ModifyEvent.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new EventModifyServlet().doPost(request, response);
		assertEquals("Modifiche assenti",request.getAttribute("errore"));
	}
	
	@Test
	public void ModificheError() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("NuovoTitolo");
		when(request.getParameter("event-titlePrecedente")).thenReturn("TitoloBuono");
		when(request.getParameter("viaPrecedente")).thenReturn("Via Casuale");
		when(request.getParameter("via")).thenReturn("Via Casuale");
		when(request.getParameter("cittaPrecedente")).thenReturn("Città dei pascoli");
		when(request.getParameter("citta")).thenReturn("Città dei pascoli");
		when(request.getParameter("dataEvento")).thenReturn("2020-06-06");
		when(request.getParameter("dataEventoPrecedente")).thenReturn("2020-06-06");
		when(request.getParameter("event-argumentPrecedente")).thenReturn("Parliamo di algortmi");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi");
		when(request.getRequestDispatcher("event.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new EventModifyServlet().doPost(request, response);
		assertEquals("Evento già registrato",request.getAttribute("erroreRegistrazione"));
	}
	
	@Test
	public void ModificheSuccess() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("NuovoTitolo");
		when(request.getParameter("event-titlePrecedente")).thenReturn("TitoloBuono");
		when(request.getParameter("viaPrecedente")).thenReturn("Via Casuale");
		when(request.getParameter("via")).thenReturn("Via Casuale Nuova");
		when(request.getParameter("cittaPrecedente")).thenReturn("Città dei pascoli");
		when(request.getParameter("citta")).thenReturn("Città dei pascoli");
		when(request.getParameter("dataEvento")).thenReturn("2020-07-06");
		when(request.getParameter("dataEventoPrecedente")).thenReturn("2020-06-06");
		when(request.getParameter("event-argumentPrecedente")).thenReturn("Parliamo di algortmi");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi nuovamente");
		when(request.getRequestDispatcher("EventShowServlet")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new EventModifyServlet().doPost(request, response);
		assertNull(request.getAttribute("erroreRegistrazione"));
	}
	
	@Test
	public void ViaErrataTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("TitoloBuono");
		when(request.getParameter("via")).thenReturn("Via Casuale 3");
		when(request.getParameter("citta")).thenReturn("Citta d'esempio");
		when(request.getParameter("dataEvento")).thenReturn("2020-06-06");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi");
		when(request.getParameter("viaPrecedente")).thenReturn("Via Casuale");
		when(request.getParameter("cittaPrecedente")).thenReturn("Città dei pascoli");
		when(request.getParameter("dataEventoPrecedente")).thenReturn("2020-06-06");
		when(request.getParameter("event-argumentPrecedente")).thenReturn("Parliamo di algortmi");
		when(request.getParameter("event-titlePrecedente")).thenReturn("TitoloBuono");
		when(request.getRequestDispatcher("ModifyEvent.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new EventModifyServlet().doPost(request, response);
		assertEquals("formato via errata",request.getAttribute("errore"));
	}
	
	@Test
	public void CittaErrataTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("TitoloBuono");
		when(request.getParameter("via")).thenReturn("Via Casuale");
		when(request.getParameter("citta")).thenReturn("Citta d'esempio 3M");
		when(request.getParameter("dataEvento")).thenReturn("2020-06-06");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi");
		when(request.getParameter("viaPrecedente")).thenReturn("Via Casuale");
		when(request.getParameter("cittaPrecedente")).thenReturn("Città dei pascoli");
		when(request.getParameter("dataEventoPrecedente")).thenReturn("2020-06-06");
		when(request.getParameter("event-argumentPrecedente")).thenReturn("Parliamo di algortmi");
		when(request.getParameter("event-titlePrecedente")).thenReturn("TitoloBuono");
		when(request.getRequestDispatcher("ModifyEvent.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new EventModifyServlet().doPost(request, response);
		assertEquals("formato città errata",request.getAttribute("errore"));
	}
	
	@Test
	public void ArgomentoErratoTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("TitoloBuono");
		when(request.getParameter("via")).thenReturn("Via Casuale");
		when(request.getParameter("citta")).thenReturn("Citta d'esempio");
		when(request.getParameter("dataEvento")).thenReturn("2020-06-06");
		when(request.getParameter("event-argument")).thenReturn("");
		when(request.getParameter("viaPrecedente")).thenReturn("Via Casuale");
		when(request.getParameter("cittaPrecedente")).thenReturn("Città dei pascoli");
		when(request.getParameter("dataEventoPrecedente")).thenReturn("2020-06-06");
		when(request.getParameter("event-argumentPrecedente")).thenReturn("Parliamo di algortmi");
		when(request.getParameter("event-titlePrecedente")).thenReturn("TitoloBuono");
		when(request.getRequestDispatcher("ModifyEvent.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new EventModifyServlet().doPost(request, response);
		assertEquals("Formato argomento non valido",request.getAttribute("errore"));
	}
	
	@Test
	public void TitoloErratoTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("");
		when(request.getParameter("via")).thenReturn("Via Casuale");
		when(request.getParameter("citta")).thenReturn("Citta d'esempio");
		when(request.getParameter("dataEvento")).thenReturn("2020-06-06");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algoritmi");
		when(request.getParameter("viaPrecedente")).thenReturn("Via Casuale");
		when(request.getParameter("cittaPrecedente")).thenReturn("Città dei pascoli");
		when(request.getParameter("dataEventoPrecedente")).thenReturn("2020-06-06");
		when(request.getParameter("event-argumentPrecedente")).thenReturn("Parliamo di algortmi");
		when(request.getParameter("event-titlePrecedente")).thenReturn("TitoloBuono");
		when(request.getRequestDispatcher("ModifyEvent.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new EventModifyServlet().doPost(request, response);
		assertEquals("Formato titolo non valido",request.getAttribute("errore"));
	}
	
	@Test
	public void DataPassataTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("TitoloBello");
		when(request.getParameter("via")).thenReturn("Via Casuale");
		when(request.getParameter("citta")).thenReturn("Citta d'esempio");
		when(request.getParameter("dataEvento")).thenReturn("2019-06-06");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algoritmi");
		when(request.getParameter("viaPrecedente")).thenReturn("Via Casuale");
		when(request.getParameter("cittaPrecedente")).thenReturn("Città dei pascoli");
		when(request.getParameter("dataEventoPrecedente")).thenReturn("2020-06-06");
		when(request.getParameter("event-argumentPrecedente")).thenReturn("Parliamo di algortmi");
		when(request.getParameter("event-titlePrecedente")).thenReturn("TitoloBuono");
		when(request.getRequestDispatcher("ModifyEvent.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new EventModifyServlet().doPost(request, response);
		assertEquals("Data passata",request.getAttribute("errore"));
	}
}