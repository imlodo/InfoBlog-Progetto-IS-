package test.model.bean;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.bean.Utente;

public class UtenteTest {

	@Test
	public void getEmail() 
	{
		Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertEquals("lauro.francesco137@gmail.com",utente.getEmail());
	}
	@Test
	public void getPassword() 
	{
		Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertEquals("Alpaca20#",utente.getPassword());
	}
	@Test
	public void getNome() 
	{
		Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertEquals("Mario",utente.getNome());
	}
	@Test
	public void getCognome() 
	{
		Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertEquals("Baldi",utente.getCognome());
	}
	@Test
	public void getUsername() 
	{
		Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertEquals("Font0",utente.getUsername());
	}
	@Test
	public void setEmail() {
		Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		utente.setEmail("lauro.francesco123@gmail.com");
		assertEquals("lauro.francesco123@gmail.com",utente.getEmail());
	}
	@Test
	public void setPassword() 
	{
		Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		utente.setPassword("Fetta29#");
		assertEquals("Fetta29#",utente.getPassword());
	}
	@Test
	public void setNome() 
	{
		Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		utente.setNome("Nicola");
		assertEquals("Nicola",utente.getNome());
	}
	@Test
	public void setCognome() 
	{
		Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		utente.setCognome("Lodato");
		assertEquals("Lodato",utente.getCognome());
	}
	@Test
	public void setUsername() 
	{
		Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		utente.setUsername("Font01");
		assertEquals("Font01",utente.getUsername());
	}
}

