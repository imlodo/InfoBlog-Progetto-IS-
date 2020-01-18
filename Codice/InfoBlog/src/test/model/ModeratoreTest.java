package test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.bean.Moderatore;

public class ModeratoreTest 
{
	@Test
	public void getEmail() 
	{
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		assertEquals("lauro.francesco137@gmail.com",moderatore.getEmail());
	}
	@Test
	public void getPassword() 
	{
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		assertEquals("Alpaca20#",moderatore.getPassword());
	}
	@Test
	public void getNome() 
	{
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		assertEquals("Mario",moderatore.getNome());
	}
	@Test
	public void getCognome() 
	{
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		assertEquals("Baldi",moderatore.getCognome());
	}
	@Test
	public void getUsername() 
	{
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		assertEquals("Font0",moderatore.getUsername());
	}
	@Test
	public void getCategoria()
	{
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		assertEquals("Algoritmi",moderatore.getCategoria_moderazione());
	}
	@Test
	public void setEmail() {
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		moderatore.setEmail("lauro.francesco123@gmail.com");
		assertEquals("lauro.francesco123@gmail.com",moderatore.getEmail());
	}
	@Test
	public void setPassword() 
	{
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		moderatore.setPassword("Fetta29#");
		assertEquals("Fetta29#",moderatore.getPassword());
	}
	@Test
	public void setNome() 
	{
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		moderatore.setNome("Nicola");
		assertEquals("Nicola",moderatore.getNome());
	}
	@Test
	public void setCognome() 
	{
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		moderatore.setCognome("Lodato");
		assertEquals("Lodato",moderatore.getCognome());
	}
	@Test
	public void setUsername() 
	{
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		moderatore.setUsername("Font01");
		assertEquals("Font01",moderatore.getUsername());
	}
	@Test
	public void setCategoria()
	{
		Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
		moderatore.setCategoria_moderazione("Software");
		assertEquals("Software",moderatore.getCategoria_moderazione());
	}
}
