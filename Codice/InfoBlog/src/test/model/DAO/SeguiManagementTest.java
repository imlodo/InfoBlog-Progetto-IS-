package test.model.DAO;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import model.bean.Seguace;
import model.manager.SeguiManagement;
import storage.DriverManagerConnectionPool;

public class SeguiManagementTest 
{
	Seguace segEsistente=new Seguace("lauro.francesco1@gmail.com","lauro.francesco47@gmail.com");
	Seguace segNuovo=new Seguace("lauro.francesco1@gmail.com","lauro.francesco478@gmail.com");
	SeguiManagement DAOSegui=new SeguiManagement(new DriverManagerConnectionPool());

	@Test
	public void doRetrieveByKeyTest() throws SQLException
	{
		assertNull(DAOSegui.doRetrieveByKey(""));
	}
	@Test
	public void doRetrieveByAllTest() throws SQLException
	{
		assertTrue(DAOSegui.doRetrieveAll("lauro.francesco47@gmail.com").size()>0);
	}
	@Test
	public void doRetrieveByAllTestNoSegui() throws SQLException
	{
		assertTrue(DAOSegui.doRetrieveAll("prova").size()==0);
	}
	@Test
	public void doSaveTest() throws SQLException
	{
		DAOSegui.doSave(segNuovo);
		assertTrue(DAOSegui.doRetrieveAll(segNuovo.getUtente()).size()>0);
	}
	@Test(expected = SQLException.class)
	public void doSaveError() throws SQLException
	{
		DAOSegui.doSave(segEsistente);
	}
	@Test
	public void doDelete() throws SQLException
	{
		assertFalse(DAOSegui.doDelete(segEsistente));
	}
}
