package test.model;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import org.junit.Test;
import model.bean.Articolo;
import model.bean.Autore;
import model.bean.Moderatore;
import model.bean.Rating;
import model.bean.Utente;

public class RatingTest {
	Utente utente=new Utente("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
	LocalDate data=LocalDate.now();
	Moderatore moderatore=new Moderatore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0","Algoritmi");
	Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
	Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
	

	@Test
	public void getNumeroStelle() 
	{
		Rating rating=new Rating(2,articolo,utente);
		assertEquals(2,rating.getNumeroStelle(),2);
	}
	
	@Test
	public void setNumeroStelle() 
	{
		Rating rating=new Rating(2,articolo,utente);
		rating.setNumeroStelle(3);
		assertEquals(3, rating.getNumeroStelle(),3);
	}
	@Test
	public void getArticolo() 
	{
		Rating rating=new Rating(2,articolo,utente);
		assertEquals(articolo, rating.getArticolo());
	}
	@Test
	public void setArticolo() 
	{
		Rating rating=new Rating(2,articolo,utente);
		Articolo articolo1=new Articolo("Algoritmi di ricerca", "Ricerca Dicotonica: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		rating.setArticolo(articolo1);
		assertEquals(articolo1, rating.getArticolo());
	}
	@Test
	public void getUtente() 
	{
		Rating rating=new Rating(2,articolo,utente);
		assertEquals(utente, rating.getUtente());
	}
	@Test
	public void setUtente() 
	{
		Rating rating=new Rating(2,articolo,utente);
		Utente utente1=new Utente("lauro.francesco13117@gmail.com","Alpacda20#","Antonio","Baldi","FoEnt0");
		rating.setUtente(utente1);
		assertEquals(utente1, rating.getUtente());
	}
}
