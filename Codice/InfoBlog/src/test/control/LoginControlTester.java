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
import control.LoginControl;

public class LoginControlTester extends Mockito
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
	
	//Quando si è già loggati e si prova ad accedere al login
	@Test
    public void testLoginRedirectHomepage() throws Exception 
	{   
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Moderatore")).thenReturn("email@email.it");
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    
	    new LoginControl().doPost(request, response);
	    assertNull(request.getAttribute("errore"));
	}
	
	@Test
	public void testLoginFormatoPasswordErrato() throws Exception
	{
		String emailTest = "email@live.it";
        String passwordTest = "ciao";
        String typeUserTest = "Moderatore";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATO",request.getAttribute("errore"));
	}
	
	@Test
	public void testLoginFormatoEmailErrato() throws Exception
	{
		String emailTest = "email@";
        String passwordTest = "FormatoGiusto1!";
        String typeUserTest = "Moderatore";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATO",request.getAttribute("errore"));
	    //Testo che lo stato del checked venga salvato[RIGA 70 LoginControl]
	    assertEquals("ModeratoreCheck", request.getAttribute("checked"));
	}
	
	@Test
	public void testLoginFormatoTipoUtenteErrato() throws Exception
	{
		String emailTest = "email@email.it";
        String passwordTest = "FormatoGiusto1!";
        String typeUserTest = "FormatoErrato";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertEquals("FORMATO_DATI_ERRATO",request.getAttribute("errore"));
	}
	
	@Test
    public void testLoginUtenteEmailSbagliata() throws Exception 
	{
        String emailTest = "testutente1@test.com";
        String passwordTest = "TestLogin1!";
        String typeUserTest = "Utente";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertEquals("DATI_ERRATI",request.getAttribute("errore"));
	}
	@Test
    public void testLoginUtentePasswordSbagliata() throws Exception 
	{
        String emailTest = "testutente@test.com";
        String passwordTest = "TestLogin13!";
        String typeUserTest = "Utente";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertEquals("DATI_ERRATI",request.getAttribute("errore"));
	}
	
	@Test
    public void testLoginUtenteSuccess() throws Exception 
	{
        String emailTest = "testutente@test.com";
        String passwordTest = "TestLogin1!";
        String typeUserTest = "Utente";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertNull(request.getAttribute("errore"));
	}
	
	@Test
    public void testLoginAutoreEmailSbagliata() throws Exception 
	{
        String emailTest = "testautore2@test.it";
        String passwordTest = "TestLogin1!";
        String typeUserTest = "Autore";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertEquals("DATI_ERRATI",request.getAttribute("errore"));
	}
	
	@Test
    public void testLoginAutorePasswordSbagliata() throws Exception 
	{
        String emailTest = "testautore@test.it";
        String passwordTest = "TestLogin12!";
        String typeUserTest = "Autore";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertEquals("DATI_ERRATI",request.getAttribute("errore"));
	}
	
	@Test
    public void testLoginAutoreSuccess() throws Exception 
	{
        String emailTest = "testautore@test.it";
        String passwordTest = "TestLogin1!";
        String typeUserTest = "Autore";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertNull(request.getAttribute("errore"));
	}
	
	@Test
    public void testLoginModeratoreEmailSbagliata() throws Exception 
	{
        String emailTest = "testmod2@test.com";
        String passwordTest = "TestLogin1!";
        String typeUserTest = "Moderatore";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertEquals("DATI_ERRATI",request.getAttribute("errore"));
	}
	
	@Test
    public void testLoginModeratorePasswordSbagliata() throws Exception 
	{
        String emailTest = "testmod@test.com";
        String passwordTest = "TestLogin12!";
        String typeUserTest = "Moderatore";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertEquals("DATI_ERRATI",request.getAttribute("errore"));
	}
	
	@Test
    public void testLoginModeratoreSuccess() throws Exception 
	{
        String emailTest = "testmod@test.com";
        String passwordTest = "TestLogin1!";
        String typeUserTest = "Moderatore";
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
	    when(request.getParameter("email")).thenReturn(emailTest);
	    when(request.getParameter("password")).thenReturn(passwordTest);
	    when(request.getParameter("typeUser")).thenReturn(typeUserTest);
	    
	    new LoginControl().doPost(request, response);
	    assertNull(request.getAttribute("errore"));
	}
	
}
