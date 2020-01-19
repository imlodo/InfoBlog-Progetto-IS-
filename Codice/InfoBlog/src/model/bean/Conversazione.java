package model.bean;

import java.util.ArrayList;

/**
 * Classe Conversazione, ha get e set per ottenere le informazioni di una conversazione: messaggi, Destinatario Conversazione
 * 
 */
public class Conversazione 
{
	private ArrayList<Messagio> messaggi;
	private String convCon;
	
	
	/**
	 * Costrutture di conversazione
	 */
	public Conversazione()
	{
		messaggi=new ArrayList<Messagio>();
		convCon="";
	}
	/**
	 * Restituisce il destinatario della conversazione
	 * @return convCon String : Rappresenta il destinatario della conversazione
	 */
	public String getConvCon() {
		return convCon;
	}
	/**
	 * Imposta il destinatario della conversazione
	 * @param convCon: Rappresenta il destinatario della conversazione
	 */
	public void setConvCon(String convCon) {
		this.convCon = convCon;
	}
	/**
	 * Restituisce i messaggi della conversazione
	 * @return messaggi ArrayList : Rappresentano i messaggi della conversazione
	 */
	public ArrayList<Messagio> getMessaggi() {
		return messaggi;
	}
	/**
	 * Imposta i messaggi della conversazione
	 * @param messaggi: Rappresentano i messaggi della conversazione
	 */
	public void setMessaggi(Messagio messaggi) 
	{
		this.messaggi.add(messaggi);
	}
	
	
	/*public static Conversazione createConversazione(ArrayList<Messagio> mex,int inizio,int fine)
	{
		Conversazione conv=new Conversazione();
		conv.setConvCon(mex.get(inizio).getMittente());
		
		for(;inizio<fine;inizio++)
			conv.setMessaggi(mex.get(inizio));
		return conv;
	}*/
}
