package test.control;
import static org.junit.Assert.*;

import java.util.ArrayList;
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
import control.ArticleShowServlet;
import model.bean.Articolo;
import model.bean.Commento;
import model.bean.Rating;

public class ArticleShowServletTest extends Mockito
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
	public void testModeratoreNoArticoli() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Moderatore")).thenReturn("email@test.com");
		when(request.getRequestDispatcher("AllArticle.jsp")).thenReturn(dispatcher);

		new ArticleShowServlet().doPost(request, response);
		assertEquals("Non hai pubblicato articoli",request.getAttribute("Vuoto"));
	}

	@Test
	public void testModeratoreArticoli() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Moderatore")).thenReturn("testmod@test.com");
		when(request.getRequestDispatcher("AllArticle.jsp")).thenReturn(dispatcher);

		new ArticleShowServlet().doPost(request, response);
		assertEquals(new ArrayList<Articolo>().getClass(),request.getAttribute("articoli").getClass());
	}
	
	@Test
	public void testAutoreNoArticoli() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("autorenoarticoli@test.it");
		when(request.getRequestDispatcher("AllArticle.jsp")).thenReturn(dispatcher);

		new ArticleShowServlet().doPost(request, response);
		assertEquals("Non hai pubblicato articoli",request.getAttribute("Vuoto"));
	}

	@Test
	public void testAutoreArticoliWithCommentAndRating() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testautore@test.it");
		when(request.getRequestDispatcher("AllArticle.jsp")).thenReturn(dispatcher);

		new ArticleShowServlet().doPost(request, response);
		assertEquals((new ArrayList<Articolo>()).getClass(),request.getAttribute("articoli").getClass());
		assertEquals(new ArrayList<Commento>().getClass(),request.getAttribute("commenti").getClass());
		assertEquals(new ArrayList<Rating>().getClass(),request.getAttribute("rating").getClass());
	}
	
	@Test
	public void testUtenteNoArticoli() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Autore")).thenReturn("testutente@test.com");
		when(request.getRequestDispatcher("AllArticle.jsp")).thenReturn(dispatcher);

		new ArticleShowServlet().doPost(request, response);
		assertEquals("Non hai pubblicato articoli",request.getAttribute("Vuoto"));
	}
	
	@Test
	public void testEsploraArticoli() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("esplora")).thenReturn("");
		when(request.getRequestDispatcher("AllArticle.jsp")).thenReturn(dispatcher);

		new ArticleShowServlet().doPost(request, response);
		assertEquals((new ArrayList<Articolo>()).getClass(),request.getAttribute("articoli").getClass());
		assertEquals(new ArrayList<Commento>().getClass(),request.getAttribute("commenti").getClass());
		assertEquals(new ArrayList<Rating>().getClass(),request.getAttribute("rating").getClass());
	}
	
	@Test
	public void testSeguiArticoli() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Utente")).thenReturn("testutente@test.com");
		when(request.getParameter("esplora")).thenReturn(null);
		when(request.getRequestDispatcher("AllArticle.jsp")).thenReturn(dispatcher);

		new ArticleShowServlet().doPost(request, response);
		assertEquals((new ArrayList<Articolo>()).getClass(),request.getAttribute("articoli").getClass());
		assertEquals(new ArrayList<Commento>().getClass(),request.getAttribute("commenti").getClass());
		assertEquals(new ArrayList<Rating>().getClass(),request.getAttribute("rating").getClass());
	}
	
	@Test
	public void testElseSeguiArticoli() throws Exception 
	{   
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("Utente")).thenReturn(null);
		when(request.getParameter("esplora")).thenReturn(null);
		when(request.getRequestDispatcher("AllArticle.jsp")).thenReturn(dispatcher);

		new ArticleShowServlet().doPost(request, response);
		assertEquals((new ArrayList<Articolo>()).getClass(),request.getAttribute("articoli").getClass());
		assertEquals(new ArrayList<Commento>().getClass(),request.getAttribute("commenti").getClass());
		assertEquals(new ArrayList<Rating>().getClass(),request.getAttribute("rating").getClass());
	}
}
