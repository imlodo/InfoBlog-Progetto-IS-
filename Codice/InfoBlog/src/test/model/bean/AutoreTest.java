package test.model.bean;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.bean.Autore;

public class AutoreTest {

	
	@Test
	public void getEmail() 
	{
		Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertEquals("lauro.francesco137@gmail.com",autore.getEmail());
	}
	@Test
	public void getPassword() 
	{
		Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertEquals("Alpaca20#",autore.getPassword());
	}
	@Test
	public void getNome() 
	{
		Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertEquals("Mario",autore.getNome());
	}
	@Test
	public void getCognome() 
	{
		Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertEquals("Baldi",autore.getCognome());
	}
	@Test
	public void getUsername() 
	{
		Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		assertEquals("Font0",autore.getUsername());
	}
	@Test
	public void setEmail() {
		Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		autore.setEmail("lauro.francesco123@gmail.com");
		assertEquals("lauro.francesco123@gmail.com",autore.getEmail());
	}
	@Test
	public void setPassword() 
	{
		Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		autore.setPassword("Fetta29#");
		assertEquals("Fetta29#",autore.getPassword());
	}
	@Test
	public void setNome() 
	{
		Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		autore.setNome("Nicola");
		assertEquals("Nicola",autore.getNome());
	}
	@Test
	public void setCognome() 
	{
		Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		autore.setCognome("Lodato");
		assertEquals("Lodato",autore.getCognome());
	}
	@Test
	public void setUsername() 
	{
		Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
		autore.setUsername("Font01");
		assertEquals("Font01",autore.getUsername());
	}
}
