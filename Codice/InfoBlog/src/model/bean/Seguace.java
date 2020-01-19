package model.bean;
/**
 * Classe Seguace, ha get e set per ottenere le informazioni dei seguaci: utente, autore seguito
 *
 * 
 */

public class Seguace 
{
	private String autore;
	private String utente;
	
	/**
	 * Costruttore vuoto
	 */
	public Seguace() 
	{}
	
	/**
	 * Costrutture di seguace
	 * @param autore String : Rappresenta l'autore seguito
	 * @param utente String : Rappresenta l'utente
	 */	
	public Seguace(String autore, String utente) 
	{
		this.autore = autore;
		this.utente = utente;
	}
	/**
	 * Restituisce l'autore seguito
	 * @return autore String : Rappresenta l'autore seguito
	 */
	public String getAutore() {
		return autore;
	}
	/**
	 * Imposta l'autore da seguire
	 * @param autore: Rappresenta l'autore seguito
	 */
	public void setAutore(String autore) {
		this.autore = autore;
	}
	/**
	 * Restituisce l'utente
	 * @return utente String : Rappresenta l'utente
	 */
	public String getUtente() {
		return utente;
	}
	/**
	 * Imposta l'utente
	 * @param utente: Rappresenta l'utente
	 */
	public void setUtente(String utente) {
		this.utente = utente;
	}
	
}
