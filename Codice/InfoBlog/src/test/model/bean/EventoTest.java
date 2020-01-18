package test.model.bean;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import model.bean.Autore;
import model.bean.Evento;



public class EventoTest 
{
	LocalDate data=LocalDate.now();
	Autore autore= new Autore("lauro.francesco123@gmail.com", "Bottiglia20#", "Francesco", "Lauro", "ento00");
	
	
	@Test
	public void getIdEvento()
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		assertEquals(1,evento.getIdEvento());
	}
	
	@Test
	public void setIdEvento()
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		evento.setIdEvento(2);
		assertEquals(2,evento.getIdEvento());
	}
	
	@Test
	public void getVia()
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		assertEquals("Fontana",evento.getVia());
	}
	
	@Test
	public void setVia()
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		evento.setVia("Ugo Foscolo");
		assertEquals("Ugo Foscolo",evento.getVia());
	}
	
	@Test
	public void getCittà()
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		assertEquals("Pagani",evento.getCittà());
	}
	
	@Test
	public void setCittà()
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		evento.setCittà("Nocera");
		assertEquals("Nocera",evento.getCittà());
	}
	
	@Test
	public void getNome() 
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		assertEquals("Mips explain",evento.getNome());
	}
	
	@Test
	public void setNome() 
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		evento.setNome("ALU e le sue funzionalità");
		assertEquals("ALU e le sue funzionalità",evento.getNome());
	}
	
	@Test
	public void getArgomento()
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		assertEquals("Parliamo del MIPS e delle sue componenti",evento.getArgomento());
	}
	
	@Test
	public void setArgomento() 
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		evento.setArgomento("ALU explain");
		assertEquals("ALU explain",evento.getArgomento());
	}
	
	@Test
	public void getData()
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		assertEquals(data,evento.getData());
	}
	
	@Test
	public void setData()
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		evento.setData(LocalDate.parse("2020-12-12"));
		assertEquals(LocalDate.parse("2020-12-12"),evento.getData());
	}
	
	@Test
	public void getAutore() 
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		assertEquals(autore,evento.getAutore());
	}
	
	@Test
	public void setAutore() 
	{
		Evento evento=new Evento("Fontana", "Pagani", "Mips explain", "Parliamo del MIPS e delle sue componenti", data, 1, autore);
		Autore author=new Autore("lauro.francesco124@gmail.com", "Bottiglia20#", "Antonio", "Lodato", "akami");
		evento.setAutore(author);
		assertEquals(author,evento.getAutore());
	}
}
