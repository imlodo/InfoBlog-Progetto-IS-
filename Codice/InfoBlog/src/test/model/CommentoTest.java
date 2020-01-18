package test.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import model.bean.Articolo;
import model.bean.Autore;
import model.bean.Commento;
import model.bean.Moderatore;
import model.bean.Utente;

public class CommentoTest 
{
	Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
	LocalDate data=LocalDate.now();
	Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
	Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
	Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
	
	@Test
	public void getContenuto() 
	{
		Commento commento=new Commento("bello",articolo,utente);
		assertEquals("bello", commento.getContenuto());
	}
	@Test
	public void setContenuto() 
	{
		Commento commento=new Commento("bello",articolo,utente);
		commento.setContenuto("brutto");
		assertEquals("brutto", commento.getContenuto());
	}
	@Test
	public void getArticolo() 
	{
		Commento commento=new Commento("bello",articolo,utente);
		assertEquals(articolo, commento.getArticolo());
	}
	@Test
	public void setArticolo()
	{
		Commento commento=new Commento("bello",articolo,utente);
		Articolo articolo2=new Articolo("Algoritmi di ricerca", "Ricerca dicotomica: algoritmo di ricerca noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		commento.setArticolo(articolo2);
		assertEquals(articolo2, commento.getArticolo());
	}
	@Test
	public void getUtente() 
	{
		Commento commento=new Commento("bello",articolo,utente);
		assertEquals(utente,commento.getUtente());
	}
	@Test
	public void setUtente()
	{
		Commento commento=new Commento("bello",articolo,utente);
		Utente utente1=new Utente("lauro.francesco11@gmail.com","Alptaca20#","Antonio","Lodato","Ento00");
		commento.setUtente(utente1);
		assertEquals(utente1,commento.getUtente());
	}

}
