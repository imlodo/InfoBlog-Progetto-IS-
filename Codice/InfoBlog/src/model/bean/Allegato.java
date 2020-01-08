package model.bean;

import java.io.Serializable;

/**
 * Classe allegato, ha get e set per percorso file e un possibile id di un articolo
 *
 * @author Federico
 *
 */
public class Allegato implements Serializable{
	private static final long serialVersionUID = 1L;
	private String percorsoFile;
	private int id;
	
	/**
	 * Costruttore vuoto
	 */
	public Allegato() {
		
	}
	/**
	 * Costrutture di allegato che prende la path del file come parametro
	 * @param path String, path del file
	 */
	public Allegato(String path) {
		this.percorsoFile=path;
	}
	/**
	 * Restituisce il path del allegato a cui è associato
	 * @return percorsoFile String del percorso del file associato all'allegato
	 */
	public String getPercorsoFile() {
		return percorsoFile;
	}

	/**
	 * Permette di associare un percorsoFile all'allegato
	 * @param percorsoFile String del path da associare all'allegato
	 */
	public void setPercorsoFile(String percorsoFile) {
		this.percorsoFile = percorsoFile;
	}

	/**
	 * Restituisce l'id dell'articolo associato all'allegato
	 * @return int id dell'articolo a cui è associato l'allegato
	 */
	public int getId() {
		return id;
	}

	/**
	 * Permette di associare un id di un articolo  all'allegato
	 * @param id int da associare all'allegato
	 */
	public void setId(int id) {
		this.id = id;
	}
}