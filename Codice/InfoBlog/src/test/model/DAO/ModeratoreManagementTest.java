package test.model.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import model.bean.Moderatore;
import model.manager.ModeratoreManagement;
import storage.DriverManagerConnectionPool;

public class ModeratoreManagementTest {
	Moderatore moderatoreEsistente=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","algo");
	Moderatore moderatoreEsistente2=new Moderatore("lauro7@gmail.com","Alpaca20#","Mario","Baldi","Font0","algo");
	Moderatore moderatoreNuovo=new Moderatore("mockissto12@gmail.com","Provadasa2","Nicola","Siciliano","Sele45","algo");
	ModeratoreManagement DAOmoderatore=new ModeratoreManagement(new DriverManagerConnectionPool());
	
	
	@Test
	public void doRetrieveByKeySuccess() throws SQLException
	{
		assertNotNull(DAOmoderatore.doRetrieveByKey(moderatoreEsistente.getEmail()));
	}
	
	@Test
	public void doRetrieveByKeyNull() throws SQLException
	{
		assertNull(DAOmoderatore.doRetrieveByKey("mailInesistente").getEmail());
	}
	
	@Test
	public void doRetrieveAllSuccess() throws SQLException
	{
		assertTrue(DAOmoderatore.doRetrieveAll("").size()>0);
	}
	
	@Test
	public void doSaveSuccess() throws SQLException
	{
		DAOmoderatore.doSave(moderatoreNuovo);
		assertNotNull(DAOmoderatore.doRetrieveByKey(moderatoreNuovo.getEmail()));
	}
	
	@Test(expected = SQLException.class)
	public void doSaveError() throws SQLException
	{
		DAOmoderatore.doSave(moderatoreEsistente);
	}
	
	@Test
	public void doUpdate() throws SQLException
	{
		moderatoreEsistente.setUsername("provaCambiamenot");
		DAOmoderatore.doUpdate(moderatoreEsistente);
		assertEquals(moderatoreEsistente.getUsername(),DAOmoderatore.doRetrieveByKey("mailInesistente").getUsername());
	}
	@Test
	public void doDeleteSucces() throws SQLException
	{
		assertTrue(DAOmoderatore.doDelete(moderatoreEsistente2));
	}
	@Test
	public void doDeleteSuccesFalse() throws SQLException
	{
		Moderatore moderatoreEsistente2=new Moderatore("la@gmail.com","Alpaca20#","Mario","Baldi","Font0","algo");
		assertFalse(DAOmoderatore.doDelete(moderatoreEsistente2));
	}
}
