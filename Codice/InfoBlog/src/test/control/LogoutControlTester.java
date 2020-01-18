package test.control;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import control.LogoutControl;

public class LogoutControlTester extends Mockito
{

	private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();  
	private HttpServletRequest request = mock(HttpServletRequest.class);       
	private HttpServletResponse response = mock(HttpServletResponse.class);    
	private HttpSession session = mock(HttpSession.class);
    
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
    public void testLogoutUtente() throws Exception 
	{   
		Cookie[] cookies = new Cookie[2];
		Cookie c = new Cookie("Username", "username");
		Cookie u = new Cookie("Utente","utente@live.it");
		u.setMaxAge(30*60);
		c.setMaxAge(30*60);
		cookies[0] = c;
		cookies[1] = u;
		when(request.getCookies()).thenReturn(cookies);
        when(request.getSession()).thenReturn(session);
	    new LogoutControl().doPost(request, response);
	    Cookie[] after = request.getCookies();
	    for(Cookie temp : after)
	    {
	    	if(temp.getName().equals("JSESSIONID"))
	    		assertEquals(0, temp.getMaxAge());
	    	if(temp.getName().equals("Username"))
	    		assertEquals(0, temp.getMaxAge());
	    	if(temp.getName().equals("Utente"));
	    		assertEquals(0, temp.getMaxAge());
	    }
	}
	
	@Test
    public void testLogoutModeratore() throws Exception 
	{   
		Cookie[] cookies = new Cookie[2];
		Cookie c = new Cookie("Username", "username");
		Cookie u = new Cookie("Moderatore","mod@live.it");
		u.setMaxAge(30*60);
		c.setMaxAge(30*60);
		cookies[0] = c;
		cookies[1] = u;
		when(request.getCookies()).thenReturn(cookies);
        when(request.getSession()).thenReturn(session);
	    new LogoutControl().doPost(request, response);
	    Cookie[] after = request.getCookies();
	    for(Cookie temp : after)
	    {
	    	if(temp.getName().equals("JSESSIONID"))
	    		assertEquals(0, temp.getMaxAge());
	    	if(temp.getName().equals("Username"))
	    		assertEquals(0, temp.getMaxAge());
	    	if(temp.getName().equals("Moderatore"));
	    		assertEquals(0, temp.getMaxAge());
	    }
	}
	
	@Test
    public void testLogoutAutore() throws Exception 
	{   
		Cookie[] cookies = new Cookie[2];
		Cookie c = new Cookie("Username", "username");
		Cookie u = new Cookie("Autore","autore@live.it");
		u.setMaxAge(30*60);
		c.setMaxAge(30*60);
		cookies[0] = c;
		cookies[1] = u;
		when(request.getCookies()).thenReturn(cookies);
        when(request.getSession()).thenReturn(session);
	    new LogoutControl().doPost(request, response);
	    Cookie[] after = request.getCookies();
	    for(Cookie temp : after)
	    {
	    	if(temp.getName().equals("JSESSIONID"))
	    		assertEquals(0, temp.getMaxAge());
	    	if(temp.getName().equals("Username"))
	    		assertEquals(0, temp.getMaxAge());
	    	if(temp.getName().equals("Autore"));
	    		assertEquals(0, temp.getMaxAge());
	    }
	}
	
}
