package test.control;
import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import control.ScaricaAllegatoControl;

public class ScaricaAllegatoControlTester extends Mockito
{

	private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();  
	private HttpServletRequest request = mock(HttpServletRequest.class);       
	private HttpServletResponse response = mock(HttpServletResponse.class);    
	private HttpSession session = mock(HttpSession.class);
	StringWriter stringWriter = new StringWriter();
    private PrintWriter writer = new PrintWriter(stringWriter);
    ServletOutputStream buffer = mock(ServletOutputStream.class);

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
	public void PathNotFoundTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(response.getWriter()).thenReturn(writer);
		when(response.getOutputStream()).thenReturn(buffer);
		new ScaricaAllegatoControl().doPost(request, response);
		assertEquals("notfound",request.getAttribute("notfound"));
	}
	
	@Test
	public void DownloadAllegatiMultipliTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("id")).thenReturn("3");
		when(response.getOutputStream()).thenReturn(buffer);
		new ScaricaAllegatoControl().doPost(request, response);
		assertEquals("ok",request.getAttribute("ok"));
	}
	
	@Test
	public void DownloadSingoloAllegatoTest() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("path")).thenReturn("test3.pdf");
		when(response.getOutputStream()).thenReturn(buffer);
		new ScaricaAllegatoControl().doPost(request, response);
		assertEquals("ok",request.getAttribute("ok"));
	}
}
