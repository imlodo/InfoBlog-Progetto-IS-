package test.model.DAO;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.sql.SQLException;
import org.junit.Test;
import model.bean.Utente;
import model.manager.UserManagement;
import storage.DriverManagerConnectionPool;
public class UtenteManagementTest 
{
	Utente utenteEsistente=new Utente("lauro.francesco47@gmail.com","password","Francesco","Lauro","Ento00");
	Utente utenteEsistente2=new Utente("lauro.francesco478@gmail.com","password","Francesco","Lauro","Ento00");
	Utente utenteNuovo=new Utente("mockissto12@gmail.com","Provadasa2","Nicola","Siciliano","Sele45");
	UserManagement DAOUtente=new UserManagement(new DriverManagerConnectionPool());
	
	
	@Test
	public void doRetrieveByKeySuccess() throws SQLException
	{
		assertNotNull(DAOUtente.doRetrieveByKey(utenteEsistente.getEmail()));
	}
	
	@Test
	public void doRetrieveByKeyNull() throws SQLException
	{
		assertNull(DAOUtente.doRetrieveByKey("mailInesistente"));
	}
	
	@Test
	public void doRetrieveAllSuccess() throws SQLException
	{
		assertTrue(DAOUtente.doRetrieveAll("").size()>0);
	}
	
	@Test
	public void doSaveSuccess() throws SQLException
	{
		DAOUtente.doSave(utenteNuovo);
		assertNotNull(DAOUtente.doRetrieveByKey(utenteNuovo.getEmail()));
	}
	
	@Test(expected = SQLException.class)
	public void doSaveError() throws SQLException
	{
		DAOUtente.doSave(utenteEsistente);
	}
	
	@Test
	public void doUpdate() throws SQLException
	{
		utenteEsistente.setUsername("provaCambiamenot");
		DAOUtente.doUpdate(utenteEsistente);
		assertEquals(utenteEsistente.getUsername(),DAOUtente.doRetrieveByKey("mailInesistente").getUsername());
	}
	@Test
	public void doDeleteSucces() throws SQLException
	{
		assertTrue(DAOUtente.doDelete(utenteEsistente2));
	}
	@Test
	public void doDeleteSuccesFalse() throws SQLException
	{
		Utente utenteEsistente2=new Utente("la@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertFalse(DAOUtente.doDelete(utenteEsistente2));
	}
}
