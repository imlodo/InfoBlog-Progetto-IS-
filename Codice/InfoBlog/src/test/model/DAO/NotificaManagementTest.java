package test.model.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import model.bean.Notifica;
import model.bean.Stato;
import model.manager.NotificaManagement;
import storage.DriverManagerConnectionPool;

public class NotificaManagementTest {
	Notifica notificaEsistente=new Notifica(1,"pubblicazione articolo",Stato.inviato, "lauro.francesco1@gmail.com", "lauro.francesco137@gmail.com");
	Notifica notificaEsistente2=new Notifica(2,"pubblicazione articolo",Stato.inviato, "lauro.francesco1@gmail.com", "lauro.francesco137@gmail.com");
	Notifica notificaEsistente3=new Notifica(3,"Accettazione pubblicazione articolo",Stato.inviato, "lauro.francesco1@gmail.com", "lauro.francesco137@gmail.com");
	Notifica notificaNuova=new Notifica(4,"pubblicazione articolo",Stato.inviato, "lauro.francesco1@gmail.com", "lauro.francesco137@gmail.com");
	NotificaManagement DAONotifica=new NotificaManagement(new DriverManagerConnectionPool());
	
	@Test
	public void doRetrieveByKey() throws SQLException
	{
		assertNotNull(DAONotifica.doRetrieveByKey(notificaEsistente.getId()).getContenuto());
	}
	@Test
	public void doRetrieveByKeyNoResult() throws SQLException
	{
		assertNull(DAONotifica.doRetrieveByKey(24).getContenuto());
	}
	@Test
	public void doRetrieveAll() throws SQLException
	{
		assertTrue(DAONotifica.doRetrieveAll("").size()>0);
	}
	@Test
	public void doSaveTest() throws SQLException
	{
		DAONotifica.doSave(notificaNuova);
		assertNotNull(DAONotifica.doRetrieveByKey(notificaNuova.getId()).getContenuto());
	}
	@Test (expected = SQLException.class)
	public void doSaveTestError() throws SQLException
	{
		DAONotifica.doSave(notificaNuova);
	}
	@Test
	public void doUpdate() throws SQLException
	{
		notificaEsistente.setStato(Stato.inviato);
		DAONotifica.doUpdate(notificaEsistente);
		assertNotNull(DAONotifica.doRetrieveByKey(notificaEsistente.getId()).getStato());
	}
	@Test
	public void doDeleteTest() throws SQLException
	{
		assertTrue(DAONotifica.doDelete(notificaEsistente2));
	}
	@Test
	public void doDeleteTestFalse() throws SQLException
	{
		Notifica notificaEsistente=new Notifica(150,"pubblicazione articolo",Stato.inviato, "lauro.francesco1@gmail.com", "lauro.francesco137@gmail.com");
		assertFalse(DAONotifica.doDelete(notificaEsistente));
	}
	@Test
	public void leggiNotificaTest() throws SQLException
	{
		DAONotifica.leggiNotifica(notificaEsistente3.getId());
		assertEquals(Stato.letto,DAONotifica.doRetrieveByKey(notificaEsistente3.getId()).getStato());
	}
	@Test
	public void doRetrieveByEmailTest() throws SQLException
	{
		assertTrue(DAONotifica.doRetrieveByEmail(notificaEsistente3.getAutoreEmail()).size()>0);
	}
	@Test
	public void doRetrieveByEmailTestNoResult() throws SQLException
	{
		assertTrue(DAONotifica.doRetrieveByEmail("emailDiProva").size()==0);
	}
	
}
