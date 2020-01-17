package model.bean;

public class Seguace 
{
	private String autore;
	private String utente;
	
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	@Override
	public String toString() {
		return "Seguace [autore=" + autore + ", utente=" + utente + "]";
	}
	
	
}
