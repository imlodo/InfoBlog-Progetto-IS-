package test.model.DAO;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Test;

import model.bean.Articolo;
import model.bean.Autore;
import model.bean.Moderatore;
import model.manager.ArticoloManagement;
import storage.DriverManagerConnectionPool;

public class ArticoloManagementTest 
{
	
	Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","algo");
	Autore autore=new Autore("lauro.francesco1@gmail.com","Alpaca20#","Mario","Baldi","Font0");
	Articolo articoloEsistente=new Articolo("Bubble Sort","dasdsadasdasdasdasdasdasdasdsadsadasdasdasdasdasdasdasdasdasdasdasdsadasdasdasdasd","algo","daPubblicare",LocalDate.parse("2020-10-12"),1,autore,moderatore); 
	Articolo articoloEsistente2=new Articolo("Ricerca binaria","dasdsadasdasdasdasdasdasdasdsadsadasdasdasdasdasdasdasdasdasdasdasdsadasdasdasdasd","algo","pubblicato",LocalDate.parse("2020-10-12"),2,autore,moderatore); 
	Articolo ArticoloNuovo=new Articolo("Merge Sort","dasdsadasdasdasdasdasdasdasdsadsadasdasdasdasdasdasdasdasdasdasdasdsadasdasdasdasd","algo","daPubblicare",LocalDate.parse("2020-10-12"),3,autore,moderatore); 
	ArticoloManagement DAOArticolo=new ArticoloManagement(new DriverManagerConnectionPool());
	
	
	@Test
	public void doRetrieveByKeySuccess() throws SQLException
	{
		assertNotNull(DAOArticolo.doRetrieveByKey(String.valueOf(articoloEsistente.getId())));
	}
	
	@Test
	public void doRetrieveByKeyError() throws SQLException
	{
		assertNull(DAOArticolo.doRetrieveByKey("150"));
	}
	
	@Test
	public void doRetrieveAllAutoreSuccess() throws SQLException
	{
		assertTrue(DAOArticolo.doRetrieveAll("a:"+autore.getEmail()).size()>0);
	}
	
	@Test
	public void doRetrieveAllAutoreNoArticolo() throws SQLException
	{
		assertTrue(DAOArticolo.doRetrieveAll("a:"+"no.scrittore@gmail.com").size()==0);
	}
	
	@Test
	public void doRetrieveAllModeratoreSuccess() throws SQLException
	{
		assertTrue(DAOArticolo.doRetrieveAll("m:"+moderatore.getCategoria_moderazione()).size()>0);
	}
	
	@Test
	public void doRetrieveAllModeratoreNoArticolo() throws SQLException
	{
		assertTrue(DAOArticolo.doRetrieveAll("m:"+"software").size()==0);
	}
	

	@Test
	public void doRetrieveAllUtenteSuccess() throws SQLException
	{
		assertTrue(DAOArticolo.doRetrieveAll("u:").size()>0);
	}
	
	@Test
	public void doRetrieveAllEsploraSuccess() throws SQLException
	{
		assertTrue(DAOArticolo.doRetrieveAll("e:").size()>0);
	}
	
	@Test
	public void doRetrieveAllArticleSuccess() throws SQLException
	{
		assertTrue(DAOArticolo.doRetrieveAll("").size()>0);
	}
	
	@Test
	public void doSaveSuccess() throws SQLException
	{
		DAOArticolo.doSave(ArticoloNuovo);
		assertNotNull(DAOArticolo.doRetrieveByKey(String.valueOf(ArticoloNuovo.getId())));
	}

	@Test
	public void doUpdateSuccess() throws SQLException
	{
		ArticoloNuovo.setTitolo("Bubble sort");
		DAOArticolo.doSave(ArticoloNuovo);
	}
	
}
