package test.model.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Test;

import model.bean.Articolo;
import model.bean.Autore;
import model.bean.Commento;
import model.bean.Moderatore;
import model.bean.Utente;
import model.manager.CommentoManagment;
import storage.DriverManagerConnectionPool;

public class CommentoManagmentTest 
{
	Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","algo");
	Autore autore=new Autore("lauro.francesco1@gmail.com","Alpaca20#","Mario","Baldi","Font0");
	Articolo articoloEsistente=new Articolo("Bubble Sort","dasdsadasdasdasdasdasdasdasdsadsadasdasdasdasdasdasdasdasdasdasdasdsadasdasdasdasd","algo","daPubblicare",LocalDate.parse("2020-10-12"),1,autore,moderatore); 
	Utente utente=new Utente("lauro.francesco47@gmail.com","Provadasa2#","Nicola","Siciliano","Sele45");
	Utente utente1=new Utente("lauro.francesco478@gmail.com","Provadasa2#","Matteo","Siciliano","Sele445");
	Commento commentoEsistente=new Commento("bello", articoloEsistente, utente);
	Commento commentoNuovo=new Commento("brutto", articoloEsistente, utente1);
	CommentoManagment DAOCommento=new CommentoManagment(new DriverManagerConnectionPool());

	@Test
	public void doRetrieveByKeySuccess() throws SQLException
	{
		assertNotNull(DAOCommento.doRetrieveByKey(articoloEsistente.getId()+" "+utente.getEmail()));
	}
	@Test
	public void doRetrieveByKeyNoComment() throws SQLException
	{
		assertNull(DAOCommento.doRetrieveByKey("15 "+utente.getEmail()));
	}
	@Test
	public void doRetrieveAllSuccess() throws SQLException
	{
		assertTrue(DAOCommento.doRetrieveAll(String.valueOf(articoloEsistente.getId())).size()>0);
	}
	@Test
	public void doRetrieveAllNocomment() throws SQLException
	{
		assertTrue(DAOCommento.doRetrieveAll("150").size()==0);
	}
	@Test
	public void doSaveSuccess() throws SQLException
	{
		DAOCommento.doSave(commentoNuovo);
		assertNotNull(DAOCommento.doRetrieveByKey(articoloEsistente.getId()+" "+utente1.getEmail()));
	}
	@Test(expected = SQLException.class)
	public void doSaveError() throws SQLException
	{
		DAOCommento.doSave(commentoEsistente);
	}
	@Test
	public void doUpdate() throws SQLException
	{
		commentoEsistente.setContenuto("brutto");
		DAOCommento.doUpdate(commentoEsistente);
		assertEquals(commentoEsistente.getContenuto(), DAOCommento.doRetrieveByKey(articoloEsistente.getId()+" "+utente1.getEmail()).getContenuto());
	}
	@Test
	public void doDelete() throws SQLException
	{
		assertFalse(DAOCommento.doDelete(commentoEsistente));
	}
}
