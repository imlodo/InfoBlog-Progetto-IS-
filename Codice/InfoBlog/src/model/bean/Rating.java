package model.bean;
/**
 * Classe Rating, ha get e set per ottenere le informazioni sul rating fornito: numeroStelle, articolo,utente
 *
 * 
 */
public class Rating
{
	private float numeroStelle;
	private Articolo articolo;
	private Utente utente;

	/**
	 * Costruttore vuoto
	 */
	public Rating()
	{}
	
	/**
	 * Costrutture di Rating
	 * @param numeroStelle Float: Rappresenta la valutatione dell'utente
	 * @param articolo Articolo: Rappresenta l'articolo ove viene lasciata la valutazione
	 * @param utente Utente: Rappresenta l'utente che ha lasciato il commento
	 */
	public Rating(float numeroStelle, Articolo articolo, Utente utente) {
		this.numeroStelle = numeroStelle;
		this.articolo = articolo;
		this.utente = utente;
	}

	/**
	 * Restituisce il rating dell'utente
	 * @return numeroStelle Float: Rappresenta il rating dell'utente
	 */
	public float getNumeroStelle() {
		return numeroStelle;
	}
	/**
	 * Imposta il rating dell'utente
	 * @param numeroStelle: Rappresenta il rating dell'utente
	 */
	public void setNumeroStelle(float numeroStelle) {
		this.numeroStelle = numeroStelle;
	}
	/**
	 * Restituisce l'articolo ove viene lasciata la valutazione
	 * @return articolo Articolo: Rappresenta  l'articolo ove viene lasciata la valutazione
	 */
	public Articolo getArticolo() {
		return articolo;
	}
	/**
	 * Imposta l'articolo ove viene lasciata la valutazione
	 * @param articolo : Rappresenta  l'articolo ove viene lasciata la valutazione
	 */
	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}
	/**
	 * Restituisce l'utente che ha fornito la valutazione
	 * @return utente Utente: Rappresenta  l'articolo ove viene lasciata la valutazione
	 */
	public Utente getUtente() {
		return utente;
	}
	/**
	 * Restituisce l'utente che ha fornito la valutazione
	 * @param utente : Rappresenta  l'articolo ove viene lasciata la valutazione
	 */
	public void setUtente(Utente utente) {
		this.utente = utente;
	}


	
}
