package model.bean;

public class Commento {
	private String contenuto;
	private Articolo articolo;
	private Utente utente;
	
	public Commento()
	{}

	
	public Commento(String contenuto, Articolo articolo, Utente utente) {
		this.contenuto = contenuto;
		this.articolo = articolo;
		this.utente = utente;
	}


	public String getContenuto() 
	{
		return contenuto;
	}

	public void setContenuto(String contenuto) 
	{
		this.contenuto = contenuto;
	}

	public Articolo getArticolo() 
	{
		return articolo;
	}

	public void setArticolo(Articolo articolo)
	{
		this.articolo = articolo;
	}

	public Utente getUtente() 
	{
		return utente;
	}

	public void setUtente(Utente utente) 
	{
		this.utente = utente;
	}

	@Override
	public String toString() 
	{
		return "Commento [contenuto=" + contenuto + ", articolo=" + articolo + ", utente=" + utente + "]";
	}
	
}
