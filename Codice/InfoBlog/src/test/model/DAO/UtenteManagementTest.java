package test.model.DAO;

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
	Utente utente2=new Utente("lauro.francesco47@gmail.com","password","Francesco","Lauro","Ento00");
	Utente utente1=new Utente("lauro.antonio@gmail.com","Provadasa2","Nicola","Siciliano","Sele45");
	UserManagement DAOUtente=new UserManagement(new DriverManagerConnectionPool());
	
	@Test
	public void doSaveSucces() throws SQLException
	{	
		DAOUtente.doSave(utente1);
		assertNotNull(DAOUtente.doRetrieveByKey(utente2.getEmail()));
	}
	
	@Test(expected = SQLException.class)
	public void doSaveError() throws SQLException
	{	
		DAOUtente.doSave(utente2);
	}
	
	@Test
	public void doRetrieveByKey() throws SQLException
	{
		assertNotNull(DAOUtente.doRetrieveByKey(utente2.getEmail()));
	}
	
	@Test
	public void doRetrieveByKeyError() throws SQLException
	{
		assertNull(DAOUtente.doRetrieveByKey("q"));
	}
	
	@Test
	public void doRetrieveAll() throws SQLException
	{
		assertTrue(DAOUtente.doRetrieveAll("").size()>0);
	}
	
	@Test
	public void doUpdate() throws SQLException
	{
		DAOUtente.doUpdate(utente1);
	}
	@Test 
	public void doDelete() throws SQLException
	{
		assertTrue(DAOUtente.doDelete(utente2));
	}
	@Test 
	public void doDeleteFalse() throws SQLException
	{
		assertFalse((DAOUtente.doDelete(utente1)));
	}
}
