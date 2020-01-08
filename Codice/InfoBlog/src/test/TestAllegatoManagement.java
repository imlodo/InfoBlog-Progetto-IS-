package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.bean.Allegato;
import model.bean.Autore;
import model.bean.Moderatore;
import model.manager.AllegatoManagement;
import storage.DriverManagerConnectionPool;

public class TestAllegatoManagement {
	private final static Allegato ORACOLO=new Allegato("C:\\users\\Federico\\Desktop\\allegati\\oracolo.txt");
	private final static Moderatore moderatore=new Moderatore("mail1@mail.it", "pass", "nome", "cognome", "user1", "cate");
	private final static Autore autore=new Autore("mail2@mail.it", "pass", "nome", "cognome", "user2");
	private final static AllegatoManagement allegatoManagement=new AllegatoManagement(new DriverManagerConnectionPool());
	
	public TestAllegatoManagement() {

	}
	
	@BeforeClass
	public static void inserireAllegatoTest() throws SQLException{
		allegatoManagement.doSave(ORACOLO);
	}
	
	@AfterClass
	public static void cancellaAllegatoTest() throws SQLException{
		allegatoManagement.doDelete(ORACOLO);
	}
	
	//TODO Bho da continuare?
	//public void 
}