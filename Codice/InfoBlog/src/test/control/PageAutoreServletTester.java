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
import control.PageAutoreServlet;

public class PageAutoreServletTester extends Mockito
{

	private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();  
	private HttpServletRequest request = mock(HttpServletRequest.class);       
	private HttpServletResponse response = mock(HttpServletResponse.class);    
	private HttpSession session = mock(HttpSession.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);

	//Serve per il forward
	@Before
	public void setup()
	{
		Mockito.doAnswer(new Answer<Void>() 
		{
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
	public void accessoNegatoModeratoreTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Moderatore")).thenReturn("testmod@test.com");
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
		new PageAutoreServlet().doPost(request, response);
		assertEquals("Accesso negato",request.getAttribute("errore"));
	}
	
	@Test
	public void testEmailErrata() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
		when(request.getParameter("email")).thenReturn("@");
		new PageAutoreServlet().doPost(request, response);
		assertEquals("Errore, email errata",request.getAttribute("errore"));
	}
	
	@Test
	public void testArticoliNonPresenti() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("PaginaAutore.jsp")).thenReturn(dispatcher);
		when(request.getParameter("email")).thenReturn("autorenonesistente@autore.it");
		new PageAutoreServlet().doPost(request, response);
		assertEquals("Non ci sono articoli",request.getAttribute("Vuoto"));
	}
	
	@Test
	public void testArticoliPresenti() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("PaginaAutore.jsp")).thenReturn(dispatcher);
		when(request.getParameter("email")).thenReturn("testautore@test.it");
		new PageAutoreServlet().doPost(request, response);
		assertNotEquals("Articoli Non presenti",request.getAttribute("Vuoto"));
	}

}
