package test.model.bean;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import model.bean.Articolo;
import model.bean.Autore;
import model.bean.Moderatore;

public class ArticoloTest 
{
	Autore autore=new Autore("lauro.francesco137@gmail.com","Alpaca20#","Mario","Baldi","Font0");
	Moderatore moderatore=new Moderatore("lauro.francesco133@gmail.com","Alpaca20#","Mario","Rossi","Tido12","Algoritmi");
	LocalDate data=LocalDate.now();
	
	@Test
	public void getTitolo() 
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		assertEquals("Algoritmi di ordinamento", articolo.getTitolo());
	}
	@Test
	public void setTitolo()
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		articolo.setTitolo("Agoritmi di ricerca");
		assertEquals("Agoritmi di ricerca", articolo.getTitolo());
	}
	@Test
	public void getContenuto() 
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		assertEquals("BubbleSort: algoritmo di ordinamaneto noto....", articolo.getContenuto());
	}
	@Test
	public void setContenuto() 
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		articolo.setContenuto("Ricerca binaria: ti va di trovare....");
		assertEquals("Ricerca binaria: ti va di trovare....", articolo.getContenuto());
	}
	@Test
	public void getCategoria() 
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		assertEquals("Algoritmi", articolo.getCategoria());
	}
	@Test
	public void setCategoria() 
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		articolo.setCategoria("software");
		assertEquals("software", articolo.getCategoria());
	}
	@Test
	public void getStato() 
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		assertEquals("daPubblicare", articolo.getStato());
	}
	@Test
	public void setStato() 
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		articolo.setStato("respinto");
		assertEquals("respinto", articolo.getStato());
	}
	@Test
	public void getData()
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		assertEquals(data, articolo.getData());
	}
	@Test
	public void setData() 
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		LocalDate date=LocalDate.parse("2020-12-26");
		articolo.setData(date);;
		assertEquals(date, articolo.getData());
	}
	@Test
	public void getId()
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		assertEquals(1, articolo.getId());
	}
	@Test
	public void setId()
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		articolo.setId(2);
		assertEquals(2, articolo.getId());
	}
	@Test
	public void getAutore()
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		assertEquals(autore, articolo.getAutore());
	}
	@Test
	public void setAutore() 
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		Autore autore2=new Autore("lauro.francesco117@gmail.com","Alpacra20#","Nicola","Ferrero","Enpty98");
		articolo.setAutore(autore2);
		assertEquals(autore2, articolo.getAutore());
	}
	@Test
	public void getModeratore()
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		assertEquals(moderatore, articolo.getModeratore());
	}
	@Test
	public void setModeratore()
	{
		Articolo articolo=new Articolo("Algoritmi di ordinamento", "BubbleSort: algoritmo di ordinamaneto noto....", "Algoritmi", "daPubblicare", data, 1, autore, moderatore);
		Moderatore moderatore2=new Moderatore("lauro.francesco133@gmail.com","Alpaca20#","Mario","Rossi","Tido12","software");
		articolo.setModeratore(moderatore2);
		assertEquals(moderatore2, articolo.getModeratore());
	}
}
