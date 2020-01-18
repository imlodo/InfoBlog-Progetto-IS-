package test.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import model.bean.Messagio;

public class MessaggioTest 
{
	LocalDateTime data=LocalDateTime.now();

	@Test
	public void getStato() 
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		assertEquals("inviato",messaggio.getStato());
	}
	@Test
	public void setStato() 
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		messaggio.setStato("ricevuto");
		assertEquals("ricevuto",messaggio.getStato());
	}
	@Test
	public void getData() 
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		assertEquals(data, messaggio.getData());
	}
	@Test
	public void setData() 
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		LocalDateTime dateTime=LocalDateTime.now();
		messaggio.setData(dateTime);
		assertEquals(dateTime, messaggio.getData());
	}
	@Test
	public void getTipologia() 
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		assertEquals("messaggio", messaggio.getTipologia());
	}
	@Test
	public void setTipologia()
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		messaggio.setTipologia("risposta");
		assertEquals("risposta", messaggio.getTipologia());
	}
	@Test
	public void getMittente() 
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		assertEquals("lauro.francesco123@gmail.com", messaggio.getMittente());
	}
	@Test
	public void setMittente()
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		messaggio.setMittente("lauro.francesco128@gmail.com");
		assertEquals("lauro.francesco128@gmail.com", messaggio.getMittente());
	}
	@Test
	public void getDestinatario()
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		assertEquals("lauro.francesco124@gmail.com", messaggio.getDestinatario());
	}
	@Test
	public void setDestinatario()
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		messaggio.setDestinatario("lauro.francesco1@gmail.com");
		assertEquals("lauro.francesco1@gmail.com", messaggio.getDestinatario());
	}
	@Test
	public void getContenuto() 
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		assertEquals("ciao", messaggio.getContenuto());
	}
	@Test
	public void setContenuto() 
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		messaggio.setContenuto("we");
		assertEquals("we", messaggio.getContenuto());
	}
	@Test
	public void getIdMessaggio() 
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		assertEquals(1, messaggio.getIdMessaggio());
	}
	@Test
	public void setIdMessaggio() 
	{
		Messagio messaggio=new Messagio("ciao", 1,"lauro.francesco123@gmail.com","lauro.francesco124@gmail.com","messaggio","inviato", data);
		messaggio.setIdMessaggio(2);
		assertEquals(2, messaggio.getIdMessaggio());
	}
}
