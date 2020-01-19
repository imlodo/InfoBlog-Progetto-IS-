package model.bean;

import java.time.LocalDateTime;
/**
 * Classe Messaggio, ha get e set per ottenere le informazioni di un messaggio: CONTENUTO, IDENTIFICATIVO DEL MESSAGGIO,
 * MITTENTE, DESTINATARIO, TIPOLOGIA, STATO DEL MESSAGGIO, DATA INVIO DEL MESSAGGIO
 *
 * 
 */
public class Messagio
{
	private String contenuto;
	private int idMessaggio;
	private String mittente,destinatario;
	private String tipologia;
	private String stato;
	private LocalDateTime data;


	/**
	 * Costrutture di Messaggio
	 * @param contenuto String : Rappresenta il contenuto del messaggio
	 * @param idMessaggio Int : Rappresenta l'identificativo del messaggio
	 * @param mittente String : Rappresenta il mittente del messaggio
	 * @param destinatario String : Rappresenta il destinatario del messaggio
	 * @param tipologia String : Rappresenta la tipologia del messaggio
	 * @param stato String : Rappresenta lo stato in cui si trova il messaggio
	 * @param data LocalDataTime: Rappresenta le informazioni relative all'orario di invio del messaggio
	 */
	public Messagio(String contenuto, int idMessaggio, String mittente, String destinatario, String tipologia,
			String stato, LocalDateTime data)
	{
		this.contenuto = contenuto;
		this.idMessaggio = idMessaggio;
		this.mittente = mittente;
		this.destinatario = destinatario;
		this.tipologia = tipologia;
		this.stato = stato;
		this.data = data;
	}
	/**
	 * Costruttore che imposta il contenuto a stringa vuota
	 */
	public Messagio()
	{
		this.contenuto = "";
	}
	/**
	 * Restituisce lo stato in cui si trova il messaggio
	 * @return stato String : Rappresenta lo stato in cui si trova il messaggio
	 */
	public String getStato() {
		return stato;
	}
	/**
	 * Imposta lo stato del messaggio
	 * @param stato : Rappresenta lo stato in cui si troverà il messaggio
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}
	/**
	 * Restituisce le informazioni sull'orario del messaggio
	 * @return data LocalDataTime: Rappresenta le informazioni sull'orario del messaggio
	 */
	public LocalDateTime getData() {
		return data;
	}
	/**
	 * Imposta le informazioni sull'orario del messaggio
	 * @param data : Rappresenta le informazioni sull'orario del messaggio
	 */
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	/**
	 * Restituisce la tipologia del messaggio
	 * @return tipologia String : Rappresenta la tipologia del messaggio
	 */
	public String getTipologia() {
		return tipologia;
	}
	/**
	 * Imposta la tipologia del messaggio
	 * @param tipologia : Rappresenta la tipologia del messaggio
	 */
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	/**
	 * Restituisce il mittente del messaggio
	 * @return mittente String : Rappresenta il mittente del messaggio
	 */
	public String getMittente() {
		return mittente;
	}
	/**
	 * Imposta il mittente del messaggio
	 * @param mittente : Rappresenta il mittente del messaggio
	 */
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	/**
	 * Restituisce il destinatario del messaggio
	 * @return destinatario String : Rappresenta il destinatario del messaggio
	 */
	public String getDestinatario() {
		return destinatario;
	}
	/**
	 * Imposta il destinatario del messaggio
	 * @param destinatario : Rappresenta il destinatario del messaggio
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	/**
	 * Restituisce il contenuto del messaggio
	 * @return contenuto String : Rappresenta il contenuto del messaggio
	 */
	public String getContenuto() {
		return contenuto;
	}
	/**
	 * Imposta il contenuto del messaggio
	 * @param contenuto : Rappresenta il contenuto del messaggio
	 */
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	/**
	 * Restituisce l'identificativo del messaggio
	 * @return idMessaggio Int : Rappresenta l'identificativo del messaggio
	 */
	public int getIdMessaggio() {
		return idMessaggio;
	}
	/**
	 * Imposta l'identificativo del messaggio
	 * @param idMessaggio : Rappresenta l'identificativo del messaggio
	 */
	public void setIdMessaggio(int idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

}
