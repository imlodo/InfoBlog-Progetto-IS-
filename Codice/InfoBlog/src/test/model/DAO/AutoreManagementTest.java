package test.model.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import model.bean.Autore;
import model.manager.AutoreManagement;
import storage.DriverManagerConnectionPool;

public class AutoreManagementTest {

	Autore autoreEsistente=new Autore("lauro.francesco1@gmail.com","Alpaca20#","Mario","Baldi","Font0");
	Autore autoreEsistente2=new Autore("lauro.francesco1as12@gmail.com","Alpaca20#","Mario","Baldi","Font0");
	Autore autoreNuovo=new Autore("lauro.antonio@gmail.com","Alca20#","Antonio","Baldi","Eont0");
	AutoreManagement DAOAutore=new AutoreManagement(new DriverManagerConnectionPool());
	
	
	@Test
	public void doRetrieveByKeySuccess() throws SQLException
	{
		assertNotNull(DAOAutore.doRetrieveByKey(autoreEsistente.getEmail()));
	}
	
	@Test
	public void doRetrieveByKeyNull() throws SQLException
	{
		assertNull(DAOAutore.doRetrieveByKey("mailInesistente").getEmail());
	}
	
	@Test
	public void doRetrieveAllSuccess() throws SQLException
	{
		assertTrue(DAOAutore.doRetrieveAll("").size()>0);
	}
	
	@Test
	public void doSaveSuccess() throws SQLException
	{
		DAOAutore.doSave(autoreNuovo);
		assertNotNull(DAOAutore.doRetrieveByKey(autoreNuovo.getEmail()));
	}
	
	@Test(expected = SQLException.class)
	public void doSaveError() throws SQLException
	{
		DAOAutore.doSave(autoreEsistente);
	}
	
	@Test
	public void doUpdate() throws SQLException
	{
		autoreEsistente.setUsername("provaCambiamenot");
		DAOAutore.doUpdate(autoreEsistente);
		assertEquals(autoreEsistente.getUsername(),DAOAutore.doRetrieveByKey("mailInesistente").getUsername());
	}
	
	@Test
	public void doRetrieveByUsernameAutore() throws SQLException
	{
		assertTrue(DAOAutore.doRetrieveByUsernameAutore(autoreEsistente.getUsername()).size()>0);
	}
	@Test
	public void doDeleteSucces() throws SQLException
	{
		assertTrue(DAOAutore.doDelete(autoreEsistente2));
	}
	@Test
	public void doDeleteSuccesFalse() throws SQLException
	{
		Autore autoreEsistente2=new Autore("la@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertFalse(DAOAutore.doDelete(autoreEsistente2));
	}
}
