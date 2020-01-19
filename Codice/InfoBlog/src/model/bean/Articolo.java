package model.bean;

import java.time.LocalDate;

public class Articolo 
{
	private String titolo,contenuto,categoria,stato;
	private LocalDate data;
	private int id;
	private Autore autore;
	private Moderatore moderatore;
	


	public Articolo()
	{
		
	}
	
	public Articolo(String titolo, String contenuto, String categoria, Autore autore) 
	{
		super();
		this.titolo = titolo;
		this.contenuto = contenuto;
		this.categoria = categoria;
		this.autore = autore;
	}



	public Articolo(String titolo, String contenuto, String categoria, String stato, LocalDate data, int id,
			Autore autore, Moderatore moderatore) {
		super();
		this.titolo = titolo;
		this.contenuto = contenuto;
		this.categoria = categoria;
		this.stato = stato;
		this.data = data;
		this.id = id;
		this.autore = autore;
		this.moderatore = moderatore;
	}

	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo)
	{
		this.titolo = titolo;
	}
	public String getContenuto() 
	{
		return contenuto;
	}
	public void setContenuto(String contenuto) 
	{
		this.contenuto = contenuto;
	}
	public String getCategoria() 
	{
		return categoria;
	}
	public void setCategoria(String categoria) 
	{
		this.categoria = categoria;
	}
	public String getStato() 
	{
		return stato;
	}
	public void setStato(String stato) 
	{
		this.stato = stato;
	}
	public LocalDate getData()
	{
		return data;
	}
	public void setData(LocalDate data) 
	{
		this.data = data;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public Autore getAutore()
	{
		return autore;
	}
	public void setAutore(Autore autore) 
	{
		this.autore = autore;
	}
	public Moderatore getModeratore()
	{
		return moderatore;
	}
	public void setModeratore(Moderatore moderatore)
	{
		this.moderatore = moderatore;
	}

	@Override
	public String toString() {
		return "Articolo [titolo=" + titolo + ", contenuto=" + contenuto + ", categoria=" + categoria + ", stato="
				+ stato + ", data=" + data + ", id=" + id + ", autore=" + autore + ", moderatore=" + moderatore + "]";
	}
	
	
}
