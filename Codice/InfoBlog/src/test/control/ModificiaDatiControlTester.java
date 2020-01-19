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
import control.ModificaDatiPersonaliControl;

public class ModificiaDatiControlTester extends Mockito
{

	private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();  
	private HttpServletRequest request = mock(HttpServletRequest.class);       
	private HttpServletResponse response = mock(HttpServletResponse.class);    
	private HttpSession session = mock(HttpSession.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	StringWriter stringWriter = new StringWriter();
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
    public void GuestNotFoundRedirectTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	}
	
	@Test
    public void NomeErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getParameter("nome")).thenReturn("");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATI",request.getAttribute("errore"));
	}
	@Test
    public void CognomeErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATI",request.getAttribute("errore"));
	}
	@Test
    public void UsernameErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("username")).thenReturn("");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATI",request.getAttribute("errore"));
	}
	@Test
    public void PasswordErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("username")).thenReturn("Username1");
        when(request.getParameter("password")).thenReturn("");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATI",request.getAttribute("errore"));
	}
	
	@Test
    public void UsernameExistUtenteTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getSession().getAttribute("Username")).thenReturn("testerUtente");
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("username")).thenReturn("testerUtente4");
        when(request.getParameter("password")).thenReturn("Funzionante1!");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("DATI_PRESENTI",request.getAttribute("errore"));
	}
	@Test
    public void UsernameExistAutoreTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getSession().getAttribute("Username")).thenReturn("testerUtente");
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("username")).thenReturn("testerAutore");
        when(request.getParameter("password")).thenReturn("Funzionante1!");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("DATI_PRESENTI",request.getAttribute("errore"));
	}
	@Test
    public void UsernameExistModeratoreTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getSession().getAttribute("Moderatore")).thenReturn("testerModeratore");
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("username")).thenReturn("testerUtente");
        when(request.getParameter("password")).thenReturn("Funzionante1!");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("DATI_PRESENTI",request.getAttribute("errore"));
	}
	@Test
    public void ModificheAssentiUtente() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getSession().getAttribute("Username")).thenReturn("testerUtente");
        when(request.getParameter("nome")).thenReturn("Tester");
        when(request.getParameter("cognome")).thenReturn("Tester");
        when(request.getParameter("username")).thenReturn("testerUtente");
        when(request.getParameter("password")).thenReturn("TestLogin1!");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("MODIFICHE_ASSENTI",request.getAttribute("errore"));
	}
	@Test
    public void ModificheAssentiAutore() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
        when(request.getSession().getAttribute("Username")).thenReturn("testerAutore");
        when(request.getParameter("nome")).thenReturn("Tester");
        when(request.getParameter("cognome")).thenReturn("Tester");
        when(request.getParameter("username")).thenReturn("testerAutore");
        when(request.getParameter("password")).thenReturn("TestLogin1!");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("MODIFICHE_ASSENTI",request.getAttribute("errore"));
	}
	@Test
    public void ModificheAssentiModeratore() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Moderatore")).thenReturn("testmod@test.com");
        when(request.getSession().getAttribute("Username")).thenReturn("testerModeratore");
        when(request.getParameter("nome")).thenReturn("Tester");
        when(request.getParameter("cognome")).thenReturn("Tester");
        when(request.getParameter("username")).thenReturn("testerModeratore");
        when(request.getParameter("password")).thenReturn("TestLogin1!");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("MODIFICHE_ASSENTI",request.getAttribute("errore"));
	}
	@Test
    public void ModificheSuccessModeratore() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Moderatore")).thenReturn("testmod@test.com");
        when(request.getSession().getAttribute("Username")).thenReturn("testerModeratore");
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("username")).thenReturn("testerModeratore30");
        when(request.getParameter("password")).thenReturn("Funzionante1!");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("MODIFICA_SUCCESS",request.getAttribute("success"));
	}
	@Test
    public void ModificheSuccessAutore() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
        when(request.getSession().getAttribute("Username")).thenReturn("testerAutore");
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("username")).thenReturn("testerAutore30");
        when(request.getParameter("password")).thenReturn("Funzionante1!");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("MODIFICA_SUCCESS",request.getAttribute("success"));
	}
	@Test
    public void ModificheSuccessUtente() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
        when(request.getSession().getAttribute("Username")).thenReturn("testerUtente");
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("username")).thenReturn("testerUtente30");
        when(request.getParameter("password")).thenReturn("Funzionante1!");
        when(request.getRequestDispatcher("profile.jsp")).thenReturn(dispatcher);
	    new ModificaDatiPersonaliControl().doPost(request, response);
	    assertEquals("MODIFICA_SUCCESS",request.getAttribute("success"));
	}
}
