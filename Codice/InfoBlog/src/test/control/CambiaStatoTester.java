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

import control.CambiaStato;

public class CambiaStatoTester extends Mockito
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
    public void accessoNegatoTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Moderatore")).thenReturn("email@email.it");
        when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
	    
	    new CambiaStato().doPost(request, response);
	    assertEquals("Accesso negato",request.getAttribute("errore"));
	    assertNull(request.getAttribute("cambiamento"));
	}
	
	@Test
    public void idErratoTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Moderatore")).thenReturn(null);
        when(request.getParameter("id")).thenReturn("stringa");
        when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
	    
	    new CambiaStato().doPost(request, response);
	    assertEquals("informazioni errate",request.getAttribute("errore"));
	}
	
	@Test
    public void cambiaTipologAutoreTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
        when(request.getParameter("id")).thenReturn("1");
        when(response.getWriter()).thenReturn(writer);
	    new CambiaStato().doPost(request, response);
	    assertNull(request.getParameter("errore"));
	}
	
	@Test
    public void cambiaTipologiaUtenteTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getParameter("id")).thenReturn("1");
        when(response.getWriter()).thenReturn(writer);
	    new CambiaStato().doPost(request, response);
	    assertNull(request.getParameter("errore"));
	}
	
	
	
}
