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
import model.bean.Moderatore;
import model.bean.Rating;
import model.bean.Utente;
import model.manager.RatingManagement;
import storage.DriverManagerConnectionPool;

public class RatingManagementTest 
{
	Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","algo");
	Autore autore=new Autore("lauro.francesco1@gmail.com","Alpaca20#","Mario","Baldi","Font0");
	Articolo articoloEsistente=new Articolo("Bubble Sort","dasdsadasdasdasdasdasdasdasdsadsadasdasdasdasdasdasdasdasdasdasdasdsadasdasdasdasd","algo","daPubblicare",LocalDate.parse("2020-10-12"),1,autore,moderatore); 
	Utente utente=new Utente("lauro.francesco47@gmail.com","Provadasa2#","Nicola","Siciliano","Sele45");
	Utente utente1=new Utente("lauro.francesco478@gmail.com","Provadasa2#","Matteo","Siciliano","Sele445");
	Rating ratingEsistente=new Rating(4, articoloEsistente, utente);
	Rating ratingNuovo=new Rating(3, articoloEsistente, utente1);
	RatingManagement DAORating=new RatingManagement(new DriverManagerConnectionPool());
	
	@Test
	public void doRetrieveByKeySuccess() throws SQLException
	{
		assertNotNull(DAORating.doRetrieveByKey(articoloEsistente.getId()+" "+utente.getEmail()));
	}
	@Test
	public void doRetrieveByKeyNoRating() throws SQLException
	{
		assertNull(DAORating.doRetrieveByKey("15 "+utente.getEmail()));
	}
	@Test
	public void doRetrieveAllSuccess() throws SQLException
	{
		assertTrue(DAORating.doRetrieveAll(String.valueOf(articoloEsistente.getId())).size()>0);
	}
	@Test
	public void doSaveSuccess() throws SQLException
	{
		DAORating.doSave(ratingNuovo);
		assertNotNull(DAORating.doRetrieveByKey(articoloEsistente.getId()+" "+utente1.getEmail()));
	}
	@Test(expected = SQLException.class)
	public void doSaveError() throws SQLException
	{
		DAORating.doSave(ratingEsistente);
	}
	@Test
	public void doUpdate() throws SQLException
	{
		ratingEsistente.setNumeroStelle(5);
		DAORating.doUpdate(ratingEsistente);
		assertEquals(ratingEsistente.getNumeroStelle(), DAORating.doRetrieveByKey(articoloEsistente.getId()+" "+utente1.getEmail()).getNumeroStelle(),ratingEsistente.getNumeroStelle());
	}
	@Test
	public void doDelete() throws SQLException
	{
		assertFalse(DAORating.doDelete(ratingEsistente));
	}
	
}
