package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.bean.Allegato;
import model.bean.Articolo;
import model.bean.Autore;
import model.bean.Moderatore;
import model.manager.AllegatoManagement;
import model.manager.ArticoloManagement;
import model.manager.AutoreManagement;
import model.manager.ModeratoreManagement;
import storage.DriverManagerConnectionPool;

public class AllegatoManagementTester {
	static DriverManagerConnectionPool dm=new DriverManagerConnectionPool();

	static Autore autore=new Autore("email55@email.it", "passoword", "nome", "cognome", "username");
	static AutoreManagement autMan=new AutoreManagement(dm);
	
	static Moderatore moderatore=new Moderatore("email132@email.ti", "password", "nome", "cognome", "username", "categoria");
	static ModeratoreManagement modMan=new ModeratoreManagement(dm);
	
	static ArticoloManagement artMan=new ArticoloManagement(dm);
	static Articolo articolo=new Articolo("test","prova","test",autore);

	
	static AllegatoManagement allMan=new AllegatoManagement(dm);
	
	@AfterClass
	public static void deleteBeansAndManagements() throws SQLException{
		artMan.doDelete(articolo);
		modMan.doDelete(moderatore);
		autMan.doDelete(autore);
	}
	
	@BeforeClass
	public static void addBeansAndManagements() throws SQLException{
		autMan.doSave(autore);
		modMan.doSave(moderatore);
		artMan.doSave(articolo);
	}
	
	@Test
	public void aggiungiAllegato() throws SQLException{
		Allegato allegato=new Allegato();
		allegato.setPercorsoFile("C:\\path\\da\\testare");
		allMan.doSave(allegato);
		assertNotNull(allMan.doRetrieveByKey(allegato.getPercorsoFile()));
		allMan.doDelete(allegato);
	}
	
	@Test
	public void retrieveAllegato() throws SQLException{
		Allegato allegato=new Allegato();
		allegato.setPercorsoFile("C:\\path\\da\\testare");
		allMan.doSave(allegato);
		assertNotNull(allMan.doRetrieveByKey(allegato.getPercorsoFile()));
		allMan.doDelete(allegato);
	}
	
	@Test
	public void retrieveAllAllegati() throws SQLException{
		Allegato allegato1=new Allegato();
		Allegato allegato2=new Allegato();
		allegato1.setPercorsoFile("Allegato allegato1=new Allegato();1");
		allegato2.setPercorsoFile("C:\\path\\da\\testare2");
		allMan.doSave(allegato1);
		allMan.doSave(allegato2);
		assertTrue(((ArrayList<Allegato>)allMan.doRetrieveAll(null)).size()==2);
		allMan.doDelete(allegato1);
		allMan.doDelete(allegato2);
	}
	
	@Test
	public void retrieveAllegatoByID() throws SQLException{
		int i=((ArrayList<Articolo>)artMan.doRetrieveAll("e")).get(0).getId();
		Allegato allegato1=new Allegato("C:\\path\\da\\testare",i);
		allMan.doSave(allegato1);
		assertTrue(((ArrayList<Allegato>)allMan.doRetrieveByID(allegato1.getId())).size()>0);
		allMan.doDelete(allegato1);
	}
	
	@Test
	public void cancellaAllegato() throws SQLException{
		Allegato allegato=new Allegato();
		allegato.setPercorsoFile("C:\\path\\da\\testare");
		allMan.doSave(allegato);
		assertTrue(allMan.doDelete(allegato));
	}
}