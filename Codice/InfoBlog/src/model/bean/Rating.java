package model.bean;

public class Rating
{
	private float numeroStelle;
	private Articolo articolo;
	private Utente utente;
	
	public Rating()
	{}
	

	public Rating(float numeroStelle, Articolo articolo, Utente utente) {
		super();
		this.numeroStelle = numeroStelle;
		this.articolo = articolo;
		this.utente = utente;
	}


	public float getNumeroStelle() {
		return numeroStelle;
	}

	public void setNumeroStelle(float numeroStelle) {
		this.numeroStelle = numeroStelle;
	}

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	@Override
	public String toString() {
		return "Rating [numeroStelle=" + numeroStelle + ", articolo=" + articolo + ", utente=" + utente + "]";
	}
	
}
