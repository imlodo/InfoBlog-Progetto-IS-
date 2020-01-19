package model.bean;

import java.time.LocalDate;

/**
 * Classe Articolo, ha get e set per ottenere le informazioni dell'articolo: Titolo, Contenuto,Categoria,
 * Stato,Data di pubblicazione, Identificativo dell'articolo, Autore e Moderatore.
 *
 * 
 */

public class Articolo 
{
	private String titolo,contenuto,categoria,stato;
	private LocalDate data;
	private int id;
	private Autore autore;
	private Moderatore moderatore;
	


	/**
	 * Costruttore vuoto
	 */
	public Articolo()
	{
		
	}
	/**
	 * Costrutture di articolo
	 * @param titolo String : Rappresenta il titolo dell'articolo
	 * @param contenuto String : Rappresenta le informazioni contenute nell'articolo
	 * @param categoria String : Rappresenta la categoria di appparteneza dell'articolo
	 * @param autore Autore : Rappresente colui che ha scritto l'articolo
	 */
	public Articolo(String titolo, String contenuto, String categoria, Autore autore) 
	{
		super();
		this.titolo = titolo;
		this.contenuto = contenuto;
		this.categoria = categoria;
		this.autore = autore;
	}


	/**
	 * Costrutture di articolo
	 * @param titolo String  : Rappresenta il titolo dell'articolo
	 * @param contenuto String : Rappresenta le informazioni contenute nell'articolo
	 * @param categoria String : Rappresenta la categoria di appparteneza dell'articolo
	 * @param stato String : Rappresenta lo stato in cui si può trovare l'articolo
	 * @param data LocalDate: Rappresenta la data di pubblicazione dell'articolo
	 * @param id Int: Rappresenta l'identificativo dell'articolo
	 * @param autore Autore : Rappresente colui che ha scritto l'articolo
	 * @param moderatore Moderatore : Rappresente colui che ha moderato e accettato l'articolo
	 */
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

	/**
	 * Restituisce il titolo dell'articolo
	 * @return titolo String : Rappresenta il titolo dell'articolo
	 */
	public String getTitolo() {
		return titolo;
	}
	
	/**
	 * Imposta il titolo dell'articolo
	 * @param titolo : Rappresenta il titolo dell'articolo
	 */
	public void setTitolo(String titolo)
	{
		this.titolo = titolo;
	}
	/**
	 * Restituisce il contenuto dell'articolo
	 * @return contenuto String : Rappresenta il contenuto dell'articolo
	 */
	public String getContenuto() 
	{
		return contenuto;
	}
	/**
	 * Imposta il contenuto dell'articolo
	 * @param contenuto: Rappresenta il contenuto dell'articolo
	 */
	public void setContenuto(String contenuto) 
	{
		this.contenuto = contenuto;
	}
	/**
	 * Restituisce la categoria dell'articolo
	 * @return categoria String : Rappresenta la categoria dell'articolo
	 */
	public String getCategoria() 
	{
		return categoria;
	}
	/**
	 * Imposta la categoria dell'articolo
	 * @param categoria: Rappresenta la categoria dell'articolo
	 */
	public void setCategoria(String categoria) 
	{
		this.categoria = categoria;
	}
	/**
	 * Restituisce lo stato dell'articolo
	 * @return stato String : Rappresenta lo stato in cui si trova l'articolo
	 */
	public String getStato() 
	{
		return stato;
	}
	/**
	 * Imposta lo stato dell'articolo
	 * @param stato: Rappresenta lo stato dell'articolo
	 */
	public void setStato(String stato) 
	{
		this.stato = stato;
	}
	/**
	 * Restituisce la data di pubblicazione dell'articolo
	 * @return data LocalDate : Rappresenta la data di pubblicazione dell'articolo
	 */
	public LocalDate getData()
	{
		return data;
	}
	/**
	 * Imposta la data di pubblicazione dell'articolo
	 * @param data : Rappresenta la data di pubblicazione dell'articolo
	 */
	public void setData(LocalDate data) 
	{
		this.data = data;
	}
	/**
	 * Restituisce l'identificativo dell'articolo
	 * @return id Int : Rappresenta l'identificativo dell'articolo
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * Imposta l'identificativo dell'articolo
	 * @param id: Rappresenta l'identificativo dell'articolo
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	/**
	 * Restituisce l'autore dell'articolo
	 * @return autore Autore : Rappresenta l'autore dell'articolo
	 */
	public Autore getAutore()
	{
		return autore;
	}
	/**
	 * Imposta l'autore dell'articolo
	 * @param autore: Rappresenta l'autore dell'articolo
	 */
	public void setAutore(Autore autore) 
	{
		this.autore = autore;
	}
	/**
	 * Restituisce il moderatore dell'articolo
	 * @return moderatore Moderatore : Rappresenta il moderatore dell'articolo
	 */
	public Moderatore getModeratore()
	{
		return moderatore;
	}
	/**
	 * Imposta il moderatore dell'articolo
	 * @param moderatore: Rappresenta il moderatore dell'articolo
	 */
	public void setModeratore(Moderatore moderatore)
	{
		this.moderatore = moderatore;
	}
	
}
