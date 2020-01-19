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

import control.ViewForModifyEvent;
public class ViewForModifyEventTester extends Mockito
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
		when(request.getSession().getAttribute("Moderatore")).thenReturn("email@email.com");
		when(response.getWriter()).thenReturn(writer);
		when(response.getOutputStream()).thenReturn(buffer);
		new ViewForModifyEvent().doPost(request, response);
		assertEquals("Accesso negato",request.getAttribute("errore"));
	}
	
	@Test
	public void DataErrataTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("TitoloEvento");
		when(request.getParameter("via")).thenReturn("Via Casuale");
		when(request.getParameter("citta")).thenReturn("Città Casuale");
		when(request.getParameter("data")).thenReturn("");
		when(request.getParameter("dataEvento")).thenReturn("");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi");
		when(request.getRequestDispatcher("event.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new ViewForModifyEvent().doPost(request, response);
		assertEquals("Formato data Errato",request.getAttribute("errore"));
	}
	
	@Test
	public void DataAntecedenteOggiErrorTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("TitoloEvento");
		when(request.getParameter("via")).thenReturn("Via Casuale 3");
		when(request.getParameter("citta")).thenReturn("Via Casuale 3");
		when(request.getParameter("data")).thenReturn("2019-05-05");
		when(request.getParameter("dataEvento")).thenReturn("2019-05-05");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi");
		when(request.getRequestDispatcher("event.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new ViewForModifyEvent().doPost(request, response);
		assertEquals("Data passata",request.getAttribute("errore"));
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
		when(request.getParameter("dataEvento")).thenReturn("2020-05-05");
		when(request.getParameter("data")).thenReturn("2020-05-05");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi");
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new ViewForModifyEvent().doPost(request, response);
		assertEquals("formato via errata",request.getAttribute("errore"));
	}
//	
	@Test
	public void CittaErrataTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("TitoloBuono");
		when(request.getParameter("via")).thenReturn("Via Casuale");
		when(request.getParameter("citta")).thenReturn("c");
		when(request.getParameter("dataEvento")).thenReturn("2020-05-05");
		when(request.getParameter("data")).thenReturn("2020-05-05");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi");
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new ViewForModifyEvent().doPost(request, response);
		assertEquals("formato città errata",request.getAttribute("errore"));
	}

	@Test
	public void AccessoAllaPaginaDiModificaTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("TitoloBuono");
		when(request.getParameter("via")).thenReturn("Via Casuale");
		when(request.getParameter("citta")).thenReturn("Città dei pascoli");
		when(request.getParameter("dataEvento")).thenReturn("2020-06-06");
		when(request.getParameter("data")).thenReturn("2020-06-06");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi");
		when(request.getRequestDispatcher("ModifyEvent.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new ViewForModifyEvent().doPost(request, response);
	}
	
	@Test
	public void EventoNonEsistente() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("event-title")).thenReturn("TitoloCasuale");
		when(request.getParameter("via")).thenReturn("Via Casualeaaaa");
		when(request.getParameter("citta")).thenReturn("Città dei pascoliaaa");
		when(request.getParameter("dataEvento")).thenReturn("2020-06-06");
		when(request.getParameter("data")).thenReturn("2020-06-06");
		when(request.getParameter("event-argument")).thenReturn("Parliamo di algortmi");
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
		when(response.getOutputStream()).thenReturn(buffer);
		new ViewForModifyEvent().doPost(request, response);
		assertEquals("informazioni errate", request.getAttribute("errore"));
	}
}
