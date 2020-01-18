package test.model.DAO;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import model.bean.Allegato;
import model.manager.AllegatoManagement;
import storage.DriverManagerConnectionPool;

public class AllegatoManagmentTest 
{
	Allegato allegatoEsistenete=new Allegato("file.psd", 1);
	Allegato allegatoEsistente2=new Allegato("fileCancella",1);
	Allegato allegatoNuovo=new Allegato("file.dox",1);
	AllegatoManagement DAOAllegato=new AllegatoManagement(new DriverManagerConnectionPool());
	
	@Test
	public void doSaveSucces() throws SQLException
	{	
		DAOAllegato.doSave(allegatoNuovo);
		assertNotNull(DAOAllegato.doRetrieveByKey(allegatoNuovo.getPercorsoFile()));
	}
	
	@Test(expected = SQLException.class)
	public void doSaveError() throws SQLException
	{	
		DAOAllegato.doSave(allegatoEsistenete);
	}
	
	@Test
	public void doRetrieveByKey() throws SQLException
	{
		assertNotNull(DAOAllegato.doRetrieveByKey(allegatoEsistenete.getPercorsoFile()));
	}
	
	@Test
	public void doRetrieveByKeyError() throws SQLException
	{
		assertNull(DAOAllegato.doRetrieveByKey("q").getPercorsoFile());
	}
	
	@Test
	public void doRetrieveAll() throws SQLException
	{
		assertTrue(DAOAllegato.doRetrieveAll("").size()>0);
	}
	
	
	@Test
	public void doDelete() throws SQLException
	{
		assertTrue(DAOAllegato.doDelete(allegatoEsistente2));
	}
	
	@Test
	public void doDeleteFail() throws SQLException
	{
		Allegato allegato=new Allegato("aa",1);
		assertFalse(DAOAllegato.doDelete(allegato));
	}
	
}
