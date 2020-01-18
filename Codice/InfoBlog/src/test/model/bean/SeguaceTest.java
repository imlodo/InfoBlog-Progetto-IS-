package test.model.bean;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.bean.Seguace;

public class SeguaceTest {
	@Test
	public void getAutore()
	{
		Seguace seguace=new Seguace("lauro.francesco123@gmail.com","lauro.francesco16@gmail.com");
		assertEquals("lauro.francesco123@gmail.com",seguace.getAutore());
	}
	@Test
	public void setAutore() 
	{
		Seguace seguace=new Seguace("lauro.francesco123@gmail.com","lauro.francesco16@gmail.com");
		seguace.setAutore("lauro.francesco1@gmail.com");
		assertEquals("lauro.francesco1@gmail.com",seguace.getAutore());
	}
	@Test
	public void getUtente() 
	{
		Seguace seguace=new Seguace("lauro.francesco123@gmail.com","lauro.francesco16@gmail.com");
		assertEquals("lauro.francesco16@gmail.com",seguace.getUtente());
	}
	@Test
	public void setUtente() 
	{
		Seguace seguace=new Seguace("lauro.francesco123@gmail.com","lauro.francesco16@gmail.com");
		seguace.setUtente("lauro.francesco1@gmail.com");
		assertEquals("lauro.francesco1@gmail.com",seguace.getUtente());
	}
}
