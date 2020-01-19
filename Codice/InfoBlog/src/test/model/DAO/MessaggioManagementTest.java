package test.model.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

import model.bean.Messagio;
import model.manager.MessaggioManagment;
import storage.DriverManagerConnectionPool;

public class MessaggioManagementTest 
{
	Messagio messaggio=new Messagio("we come va", 1, "lauro.francesco47@gmail.com", "lauro.francesco1@gmail.com", "messaggio", "inviato", LocalDateTime.parse("2020-01-19T08:40"));
	Messagio risposta=new Messagio("we tutto bene", 1, "lauro.francesco1@gmail.com","lauro.francesco47@gmail.com", "risposta", "inviato", LocalDateTime.parse("2020-01-19T08:45"));
	Messagio messaggioNuovo=new Messagio("tutto bene te?", 2, "lauro.francesco47@gmail.com", "lauro.francesco1@gmail.com", "messaggio", "inviato", LocalDateTime.parse("2020-01-19T08:50"));
	MessaggioManagment DAOMessaggio=new MessaggioManagment(new DriverManagerConnectionPool());
	
	@Test
	public void doRetrieveBykey() throws SQLException
	{
		assertNotNull(DAOMessaggio.doRetrieveByKey("lauro.francesco47@gmail.com"+" "+"lauro.francesco1@gmail.com"));
	}
	@Test
	public void doRetrieveBykeyNoResult() throws SQLException
	{
		assertNull(DAOMessaggio.doRetrieveByKey("prova"+" "+"prova").getMittente());
	}
	
	@Test
	public void doRetrieveAll() throws SQLException
	{
		assertTrue(DAOMessaggio.doRetrieveAll("lauro.francesco47@gmail.com"+" "+"lauro.francesco1@gmail.com").size()>0);
	}
	@Test
	public void doRetrieveAllSizeZero() throws SQLException
	{
		assertTrue(DAOMessaggio.doRetrieveAll("prova"+" "+"prova").size()==0);
	}
	@Test
	public void doSave() throws SQLException
	{
		DAOMessaggio.doSave(messaggioNuovo);
		assertNotNull(DAOMessaggio.doRetrieveByKey("lauro.francesco47@gmail.com"+" "+"lauro.francesco1@gmail.com"));
	}
	@Test
	public void doUpdateTest() throws SQLException
	{
		DAOMessaggio.doUpdate(messaggio);
		ArrayList<Messagio> mex=(ArrayList<Messagio>) DAOMessaggio.doRetrieveAll("lauro.francesco47@gmail.com"+" "+"lauro.francesco1@gmail.com");
		assertEquals("letto",mex.get(0).getStato());
	}
	@Test
	public void doDelete() throws SQLException
	{
		assertFalse(DAOMessaggio.doDelete(messaggio));
	}
}
