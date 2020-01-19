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
import control.UpdateMessage;

public class UpdateMessageTester extends Mockito
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
    public void testModeratoreRedirectNotFound() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Moderatore")).thenReturn("email@email.it");
        when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
	    new UpdateMessage().doPost(request, response);
	    assertEquals("Accesso negato",request.getAttribute("errore"));
	}
	
	@Test
    public void ProprietarioErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getParameter("prop")).thenReturn("");
        when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
	    new UpdateMessage().doPost(request, response);
	    assertEquals("Accesso negato",request.getAttribute("errore"));
	}
	
	@Test
    public void DestinatarioErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getParameter("prop")).thenReturn("testutente@test.com");
        when(request.getParameter("dex")).thenReturn("");
        when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
	    new UpdateMessage().doPost(request, response);
	    assertEquals("Accesso negato",request.getAttribute("errore"));
	}
	
	@Test
    public void ContenutoErratoTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("email@email.it");
        when(request.getParameter("prop")).thenReturn("testutente@test.com");
        when(request.getParameter("dex")).thenReturn("testautore@test.it");
        when(request.getParameter("text")).thenReturn("");
        when(request.getRequestDispatcher("Chat")).thenReturn(dispatcher);
	    new UpdateMessage().doPost(request, response);
	    assertEquals("contunuto formato errato",request.getAttribute("ErroreFormato"));
	}
	
	@Test
    public void MessaggioSendSuccessTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getParameter("prop")).thenReturn("testutente@test.com");
        when(request.getParameter("dex")).thenReturn("testautore@test.it");
        when(request.getParameter("text")).thenReturn("ciaooooo");
        when(request.getRequestDispatcher("Chat")).thenReturn(dispatcher);
        when(response.getWriter()).thenReturn(writer);
	    new UpdateMessage().doPost(request, response);
	    assertNull(request.getAttribute("error"));
	}
	
	@Test
    public void RispostaSendSuccessTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
        when(request.getParameter("prop")).thenReturn("testautore@test.it");
        when(request.getParameter("dex")).thenReturn("testutente@test.com");
        when(request.getParameter("text")).thenReturn("okkkkkkkkkkk");
        when(request.getRequestDispatcher("Chat")).thenReturn(dispatcher);
        when(response.getWriter()).thenReturn(writer);
	    new UpdateMessage().doPost(request, response);
	    assertNull(request.getAttribute("error"));
	}
	
}
