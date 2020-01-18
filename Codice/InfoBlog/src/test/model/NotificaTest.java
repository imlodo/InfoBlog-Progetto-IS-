package test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.bean.Notifica;
import model.bean.Stato;

public class NotificaTest 
{
	@Test
	public void getId()
	{
		Notifica notifica=new Notifica(1,"nuovo articolo", Stato.inviato, "lauro.francesco123@gmail.com", "lauro.francesco126@gmail.com");
		assertEquals(1, notifica.getId());
	}
	@Test
	public void setId()
	{		
		Notifica notifica=new Notifica(1,"nuovo articolo", Stato.inviato, "lauro.francesco123@gmail.com", "lauro.francesco126@gmail.com");
		notifica.setId(2);
		assertEquals(2, notifica.getId());
	}
	@Test
	public void getContenuto() 
	{
		Notifica notifica=new Notifica(1,"nuovo articolo", Stato.inviato, "lauro.francesco123@gmail.com", "lauro.francesco126@gmail.com");
		assertEquals("nuovo articolo", notifica.getContenuto());
	}
	@Test
	public void setContenuto() 
	{
		Notifica notifica=new Notifica(1,"nuovo articolo", Stato.inviato, "lauro.francesco123@gmail.com", "lauro.francesco126@gmail.com");
		notifica.setContenuto("modifica articolo");
		assertEquals("modifica articolo", notifica.getContenuto());
	}
	@Test
	public void getStato() 
	{	
		Notifica notifica=new Notifica(1,"nuovo articolo", Stato.inviato, "lauro.francesco123@gmail.com", "lauro.francesco126@gmail.com");
		assertEquals(Stato.inviato, notifica.getStato());
	}
	@Test
	public void setStato() 
	{		
		Notifica notifica=new Notifica(1,"nuovo articolo", Stato.inviato, "lauro.francesco123@gmail.com", "lauro.francesco126@gmail.com");
		notifica.setStato(Stato.letto);
		assertEquals(Stato.letto, notifica.getStato());
	}
	@Test
	public void getAutoreEmail() 
	{	
		Notifica notifica=new Notifica(1,"nuovo articolo", Stato.inviato, "lauro.francesco123@gmail.com", "lauro.francesco126@gmail.com");
		assertEquals("lauro.francesco123@gmail.com", notifica.getAutoreEmail());
	}
	@Test
	public void setAutoreEmail() 
	{		
		Notifica notifica=new Notifica(1,"nuovo articolo", Stato.inviato, "lauro.francesco123@gmail.com", "lauro.francesco126@gmail.com");
		notifica.setAutoreEmail("lauro.francesco1@gmail.com");
		assertEquals("lauro.francesco1@gmail.com", notifica.getAutoreEmail());
	}
	@Test
	public void getModeratoreEmail() 
	{	
		Notifica notifica=new Notifica(1,"nuovo articolo", Stato.inviato, "lauro.francesco123@gmail.com", "lauro.francesco126@gmail.com");
		assertEquals("lauro.francesco126@gmail.com", notifica.getModeratoreEmail());
	}
	@Test
	public void setModeratoreEmail() 
	{
		Notifica notifica=new Notifica(1,"nuovo articolo", Stato.inviato, "lauro.francesco123@gmail.com", "lauro.francesco126@gmail.com");
		notifica.setModeratoreEmail("lauro.francesco1@gmail.com");
		assertEquals("lauro.francesco1@gmail.com", notifica.getModeratoreEmail());
	}
}
