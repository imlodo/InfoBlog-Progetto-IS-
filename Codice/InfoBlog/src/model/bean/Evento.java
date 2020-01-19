package model.bean;

import java.time.LocalDate;

/**
 * Classe Articolo, ha get e set per ottenere le informazioni dell'evento: VIA,CITTA,NOME,ARGOMENTO,DATA,IDENTIFICATIVO
 * EVENTO.AUTORE.
 *
 * 
 */
public class Evento 
{
	private String via,citt�,nome,argomento;
	private LocalDate data;
	private int idEvento;
	private Autore autore; 

	/**
	 * Costruttore vuoto
	 */
	public Evento() {}
	/**
	 * Costrutture di evento
	 * @param via String : Rappresenta la strada dove si svolger� l'evento
	 * @param citt� String : Rappresenta la citt� dove si svolger� l'evento
	 * @param nome String :Rappresente il titolo dell'evento
	 * @param argomento String : Rappresenta l'argomento trattato durante l'evento
	 * @param data LocalDate : Rappresenta la data in cui si svolger� l'evento
	 * @param autore Autore: Rappresenta l'autore che ha organizzato l'evento
	 * @param idEvento Int: rappresenta l'identificativo dell'evento
	 */
	public Evento(String via, String citt�, String nome, String argomento, LocalDate data, int idEvento,Autore autore)
	{
		this.via = via;
		this.citt� = citt�;
		this.nome = nome;
		this.argomento = argomento;
		this.data = data;
		this.idEvento = idEvento;
		this.autore = autore;
	}
	
	/**
	 * Costrutture di evento
	 * @param via String : Rappresenta la strada dove si svolger� l'evento
	 * @param citt� String : Rappresenta la citt� dove si svolger� l'evento
	 * @param nome String :Rappresente il titolo dell'evento
	 * @param argomento String : Rappresenta l'argomento trattato durante l'evento
	 * @param data LocalDate : Rappresenta la data in cui si svolger� l'evento
	 * @param autore Autore: Rappresenta l'autore che ha organizzato l'evento
	 */
	public Evento(String via, String citt�, String nome, String argomento, LocalDate data,Autore autore)
	{
		this.via = via;
		this.citt� = citt�;
		this.nome = nome;
		this.argomento = argomento;
		this.data = data;
		this.autore = autore;
	}
	/**
	 * Restituisce l'identificativo dell'evento
	 * @return idEvento Int : Rappresenta l'identificativo dell'evento
	 */
	public int getIdEvento() {
		return idEvento;
	}
	/**
	 * Imposta l'identificativo dell'evento
	 * @param idEvento : Rappresenta l'identificativo dell'evento
	 */
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}
	/**
	 * Restituisce la stada dove si svolge dell'evento
	 * @return via String : Rappresenta la stada dove si svolge dell'evento
	 */
	public String getVia() 
	{
		return via;
	}
	/**
	 * Imposta la stada dove si svolge dell'evento
	 * @param via : Rappresenta la stada dove si svolge dell'evento
	 */
	public void setVia(String via)
	{
		this.via = via;
	}
	/**
	 * Restituisce la citt� dove si svolge dell'evento
	 * @return citt� String : Rappresenta la citt� dove si svolge dell'evento
	 */
	public String getCitt�() 
	{
		return citt�;
	}
	/**
	 * Imposta la citt� dove si svolge dell'evento
	 * @param citt� : Imposta la citt� dove si svolge dell'evento
	 */
	public void setCitt�(String citt�) 
	{
		this.citt� = citt�;
	}
	/**
	 * Restituisce il nome dell'evento
	 * @return nome String  : Rappresenta il nome dell'evento
	 */
	public String getNome() 
	{
		return nome;
	}
	/**
	 * Imposta il nome dell'evento
	 * @param nome : Rappresenta il nome dell'evento
	 */
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	/**
	 * Restituisce l'argomento trattato durante l'evento
	 * @return argomento String : Rappresenta il nome dell'evento
	 */
	public String getArgomento()
	{
		return argomento;
	}
	/**
	 * Imposta l'argomento trattato durante l'evento
	 * @param argomento : Rappresenta il nome dell'evento
	 */
	public void setArgomento(String argomento) 
	{
		this.argomento = argomento;
	}
	/**
	 * Restituisce la data di quando si svolge l'evento
	 * @return data LocalDate : Rappresenta la data di quando si svolge l'evento
	 */
	public LocalDate getData()
	{
		return data;
	}
	/**
	 * Imposta la data di quando si svolge l'evento
	 * @param data : Rappresenta la data di quando si svolge l'evento
	 */
	public void setData(LocalDate data)
	{
		this.data = data;
	}
	/**
	 * Restituisce l'autore che ha organizzato l'evento
	 * @return autore Autore : Rappresenta l'autore che ha organizzato l'evento
	 */
	public Autore getAutore() {
		return autore;
	}
	/**
	 * Imposta l'autore che ha organizzato l'evento
	 * @param autore : Rappresenta l'autore che ha organizzato l'evento
	 */
	public void setAutore(Autore autore) {
		this.autore = autore;
	}
}
