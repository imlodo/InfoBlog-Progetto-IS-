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
import control.RegistrazioneControl;

public class RegistrazioneControlTester extends Mockito
{

	private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();  
	private HttpServletRequest request = mock(HttpServletRequest.class);       
	private HttpServletResponse response = mock(HttpServletResponse.class);    
	private HttpSession session = mock(HttpSession.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	PrintWriter stringWriter = mock(PrintWriter.class);
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
    public void LogRedirectNotFountTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Moderatore")).thenReturn("email@email.it");
        when(request.getRequestDispatcher("notfound.jsp")).thenReturn(dispatcher);
	    new RegistrazioneControl().doPost(request, response);
	}
	
	@Test
    public void NomeErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("nome")).thenReturn("");
        when(request.getRequestDispatcher("registrazione.jsp")).thenReturn(dispatcher);
	    new RegistrazioneControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATI",request.getAttribute("errore"));
	}
	@Test
    public void CognomeErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("");
        when(request.getRequestDispatcher("registrazione.jsp")).thenReturn(dispatcher);
	    new RegistrazioneControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATI",request.getAttribute("errore"));
	}
	@Test
    public void EmailErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("email")).thenReturn("");
        when(request.getRequestDispatcher("registrazione.jsp")).thenReturn(dispatcher);
	    new RegistrazioneControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATI",request.getAttribute("errore"));
	}
	@Test
    public void UsernameErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("email")).thenReturn("ciao@live.it");
        when(request.getParameter("username")).thenReturn("");
        when(request.getRequestDispatcher("registrazione.jsp")).thenReturn(dispatcher);
	    new RegistrazioneControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATI",request.getAttribute("errore"));
	}
	@Test
    public void PasswordErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("email")).thenReturn("ciao@live.it");
        when(request.getParameter("username")).thenReturn("Username1");
        when(request.getParameter("password")).thenReturn("");
        when(request.getRequestDispatcher("registrazione.jsp")).thenReturn(dispatcher);
	    new RegistrazioneControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATI",request.getAttribute("errore"));
	}
	@Test
    public void TypeErrorTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("email")).thenReturn("ciao@live.it");
        when(request.getParameter("username")).thenReturn("Username1");
        when(request.getParameter("password")).thenReturn("Password1!");
        when(request.getParameter("typeUser")).thenReturn("");
        when(request.getRequestDispatcher("registrazione.jsp")).thenReturn(dispatcher);
	    new RegistrazioneControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATI",request.getAttribute("errore"));
	}
	
	@Test
    public void EmailExistUtenteTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("email")).thenReturn("testmod@test.com");
        when(request.getParameter("username")).thenReturn("utentecasuale");
        when(request.getParameter("password")).thenReturn("Password1!");
        when(request.getParameter("typeUser")).thenReturn("Utente");
        when(request.getRequestDispatcher("registrazione.jsp")).thenReturn(dispatcher);
	    new RegistrazioneControl().doPost(request, response);
	    assertEquals("DATI_PRESENTI",request.getAttribute("errore"));
	}
	
	@Test
    public void UsernameExistUtenteTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("email")).thenReturn("ciao@live.it");
        when(request.getParameter("username")).thenReturn("testerModeratore30");
        when(request.getParameter("password")).thenReturn("Password1!");
        when(request.getParameter("typeUser")).thenReturn("Utente");
        when(request.getRequestDispatcher("registrazione.jsp")).thenReturn(dispatcher);
	    new RegistrazioneControl().doPost(request, response);
	    assertEquals("DATI_PRESENTI",request.getAttribute("errore"));
	}
	@Test
    public void EmailExistAutoreTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("email")).thenReturn("testmod@test.com");
        when(request.getParameter("username")).thenReturn("utentecasuale");
        when(request.getParameter("password")).thenReturn("Password1!");
        when(request.getParameter("typeUser")).thenReturn("Autore");
        when(request.getRequestDispatcher("registrazione.jsp")).thenReturn(dispatcher);
	    new RegistrazioneControl().doPost(request, response);
	    assertEquals("DATI_PRESENTI",request.getAttribute("errore"));
	}
	
	@Test
    public void UsernameExistAutoreTest() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Lodato");
        when(request.getParameter("email")).thenReturn("ciao@live.it");
        when(request.getParameter("username")).thenReturn("testerModeratore30");
        when(request.getParameter("password")).thenReturn("Password1!");
        when(request.getParameter("typeUser")).thenReturn("Autore");
        when(request.getRequestDispatcher("registrazione.jsp")).thenReturn(dispatcher);
	    new RegistrazioneControl().doPost(request, response);
	    assertEquals("DATI_PRESENTI",request.getAttribute("errore"));
	}
	
////	C'è un problema su getContextPath()
//	@Test
//    public void AutoreRegistrazioneSuccess() throws Exception 
//	{   
//        when(request.getSession()).thenReturn(session);
//        when(request.getParameter("nome")).thenReturn("Antonio");
//        when(request.getParameter("cognome")).thenReturn("Lodato");
//        when(request.getParameter("email")).thenReturn("autorecasual@live.it");
//        when(request.getParameter("username")).thenReturn("autorecasualereg");
//        when(request.getParameter("password")).thenReturn("Password1!");
//        when(request.getParameter("typeUser")).thenReturn("Autore");
//        when(response.getWriter()).thenReturn(stringWriter);
//	    new RegistrazioneControl().doPost(request, response);
//	    assertEquals("ok",request.getAttribute("success"));
//	}
//	
//	@Test
//    public void UtenteRegistrazioneSuccess() throws Exception 
//	{   
//        when(request.getSession()).thenReturn(session);
//        when(request.getParameter("nome")).thenReturn("Antonio");
//        when(request.getParameter("cognome")).thenReturn("Lodato");
//        when(request.getParameter("email")).thenReturn("utentecasual@live.it");
//        when(request.getParameter("username")).thenReturn("utentecasualereg");
//        when(request.getParameter("password")).thenReturn("Password1!");
//        when(request.getParameter("typeUser")).thenReturn("Utente");
//        when(response.getWriter()).thenReturn(stringWriter);
//	    new RegistrazioneControl().doPost(request, response);
//	    assertEquals("ok",request.getAttribute("success"));
//	}

}
