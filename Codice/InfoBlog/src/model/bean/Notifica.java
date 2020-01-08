package model.bean;

public class Notifica {
	private int id;
	private String contenuto;
	private Stato stato=Stato.INVIATO; //Default
	private String emailAutore;
	private String emailModeratore;
	
	/**
	 * Costruttore vuoto
	 */
	public Notifica() {
		
	}
	
	/**
	 * Costruttore notifica che prende come parametri il contenuto della notifica, email dell'autore e del moderatore
	 * @param contenuto String, il contenuto della notifica, esito della moderazione
	 * @param emailAutore String, l'email dell'autore
	 * @param emailModeratore String, l'email del moderatore
	 */
	public Notifica(String contenuto, String emailAutore, String emailModeratore) {
		this.contenuto=contenuto;
		this.emailAutore=emailAutore;
		this.emailModeratore=emailModeratore;
	}
	
	/**
	 * Restituisce l'id associato alla notifica
	 * @return id int, l'id associato alla notifica
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Restituisce il contenuto associato alla notifica
	 * @return contenuto String, il contenuto associato alla notifica
	 */
	public String getContenuto() {
		return contenuto;
	}
	
	/**
	 * Assegno il contenuto alla notifica
	 * @param contenuto String, il contenuto da assegnare alla notifica
	 */
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	
	/**
	 * Restituisce lo stato della notifica
	 * @return Stato, un enum di tipo Stato che corrisponde allo stato attuale della notifica
	 */
	public Stato getStato() {
		return stato;
	}
	
	/**
	 * Modifico lo stato della notifica
	 * @param stato Stato, l'enum che corrisponde allo stato della notifica da settare
	 */
	public void setStato(Stato stato) {
		this.stato=stato;
	}
	
	/**
	 * Restituisce l'email dell'autore associato alla notifica
	 * @return l'email dell'autore associato alla notifica
	 */
	public String getAutoreEmail() {
		return emailAutore;
	}
	
	/**
	 * Permette di associare l'email di un autore alla notifica 
	 * @param email String, email dell'autore
	 */
	public void setAutoreEmail(String email) {
		this.emailAutore = email;
	}
	
	/**
	 * Restituisce l'email del moderatore associato alla notifica
	 * @return l'email del moderatore associato alla notifica
	 */
	public String getModeratoreEmail() {
		return emailModeratore;
	}
	
	/**
	 * Permette di associare l'email di un moderatore alla notifica 
	 * @param email String, email del moderatore
	 */
	public void setModeratoreEmail(String email) {
		this.emailModeratore = email;
	}
}