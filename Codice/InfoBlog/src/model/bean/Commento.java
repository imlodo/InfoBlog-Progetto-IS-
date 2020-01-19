package model.bean;

/**
 * Classe Commento, ha get e set per ottenere le informazioni dei commenti: Contenuto, Articolo,Utente
 *
 * 
 */
public class Commento {
	private String contenuto;
	private Articolo articolo;
	private Utente utente;

	/**
	 * Costruttore vuoto
	 */
	public Commento()
	{}

	/**
	 * Costrutture di commento
	 * @param contenuto String : Rappresenta l'opione lasciata da un utente sotto un articolo
	 * @param articolo Articolo : Rappresenta le informazioni relative all'articolo 
	 * @param utente Utente: Rappresenta la informazioni sull'utente che ha lasciato il commento
	
	 */
	public Commento(String contenuto, Articolo articolo, Utente utente) {
		this.contenuto = contenuto;
		this.articolo = articolo;
		this.utente = utente;
	}

	/**
	 * Restituisce il contenuto del commento
	 * @return contenuto String : Rappresenta il contenuto del commento
	 */
	public String getContenuto() 
	{
		return contenuto;
	}
	/**
	 * Imposta il contenuto del commento
	 * @param contenuto: Rappresenta il contenuto del commento
	 */
	public void setContenuto(String contenuto) 
	{
		this.contenuto = contenuto;
	}
	
	/**
	 * Restituisce l'articolo relativo al commento
	 * @return articolo Articolo : Rappresenta l'articolo sotto cui viene lasciato il commento
	 */
	public Articolo getArticolo() 
	{
		return articolo;
	}
	/**
	 * Imposta l'articolo relativo al commento
	 * @param articolo: Rappresenta l'articolo sotto cui viene lasciato il commento
	 */
	public void setArticolo(Articolo articolo)
	{
		this.articolo = articolo;
	}
	/**
	 * Restituisce l'utente che lascia il commento
	 * @return utente Utente : Rappresenta l'utente che lascia il commento
	 */
	public Utente getUtente() 
	{
		return utente;
	}
	/**
	 * Imposta l'utente che lascia il commento
	 * @param utente: Rappresenta l'utente che lascia il commento
	 */
	public void setUtente(Utente utente) 
	{
		this.utente = utente;
	}

}
