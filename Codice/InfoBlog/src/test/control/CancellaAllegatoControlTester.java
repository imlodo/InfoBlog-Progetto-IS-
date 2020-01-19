package test.control;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;
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

import control.CancellaAllegatoControl;
import model.bean.Allegato;

public class CancellaAllegatoControlTester extends Mockito
{

	private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();  
	private HttpServletRequest request = mock(HttpServletRequest.class);       
	private HttpServletResponse response = mock(HttpServletResponse.class);    
	private HttpSession session = mock(HttpSession.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	StringWriter stringWriter = new StringWriter();
    private PrintWriter writer = new PrintWriter(stringWriter);

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
	public void testCancellaAllegatoNoLogin() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);

		new CancellaAllegatoControl().doPost(request, response);
		assertNull(request.getAttribute("errore"));
		assertNull(request.getAttribute("found"));
		assertNull(request.getAttribute("result"));
	}
	
	@Test
	public void testCancellaAllegatoLogUtente() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Utente")).thenReturn("email@email.it");
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);

		new CancellaAllegatoControl().doPost(request, response);
		assertNull(request.getAttribute("errore"));
		assertNull(request.getAttribute("found"));
		assertNull(request.getAttribute("result"));
	}
	
	@Test
	public void testCancellaAllegatoLogModeratore() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Moderatore")).thenReturn("email@email.it");
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);

		new CancellaAllegatoControl().doPost(request, response);
		assertNull(request.getAttribute("errore"));
		assertNull(request.getAttribute("found"));
		assertNull(request.getAttribute("result"));
	}
	
	@Test
	public void testCancellaAllegatoNotFound() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(request.getAttribute("allegato")).thenReturn(new Allegato("C:\\users\\public\\", 10));
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);

		new CancellaAllegatoControl().doPost(request, response);
		assertEquals(false,request.getAttribute("found"));
	}
	
	@Test
	public void testCancellaAllegatoIdParameterError() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);

		new CancellaAllegatoControl().doPost(request, response);
		assertEquals("idError",request.getAttribute("errore"));
	}
	
	@Test
	public void testCancellaAllegatoIdParameterExistAndNotFound() throws Exception 
	{   
		when(request.getParameter("id")).thenReturn("5");
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
		when(request.getParameter("path")).thenReturn("test.pdf");
	    
		new CancellaAllegatoControl().doPost(request, response);
		assertEquals(false,request.getAttribute("found"));
	}
	
	//Inserire il file test.pdf nella cartella C:\User\Public prima di testare
	@Test
	public void testCancellaAllegatoSuccess() throws Exception
	{
		when(request.getParameter("id")).thenReturn("1");
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
		when(request.getParameter("path")).thenReturn("test.pdf");
		when(response.getWriter()).thenReturn(writer);
		new CancellaAllegatoControl().doPost(request, response);
		assertEquals("si", request.getAttribute("result"));
	}
	
	//Assicurarsi che test2.pd non sia presente in C:\User\Public prima di testare
	@Test
	public void testCancellaAllegatoNoSuccess() throws Exception
	{
		when(request.getParameter("id")).thenReturn("2");
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
		when(request.getParameter("path")).thenReturn("test2.pdf");
		when(response.getWriter()).thenReturn(writer);
		new CancellaAllegatoControl().doPost(request, response);
		assertEquals("no", request.getAttribute("result"));
	}
}
