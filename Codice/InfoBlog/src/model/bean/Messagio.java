package model.bean;

import java.time.LocalDateTime;

public class Messagio
{
	private String contenuto;
	private int idMessaggio;
	private String mittente,destinatario;
	private String tipologia;
	private String stato;
	private LocalDateTime data;
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
		
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public Messagio()
	{
		this.contenuto = "";
	}
	public Messagio(String contenuto)
	{
		this.contenuto = contenuto;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public int getIdMessaggio() {
		return idMessaggio;
	}
	public void setIdMessaggio(int idMessaggio) {
		this.idMessaggio = idMessaggio;
	}
	@Override
	public String toString() {
		return "Messagio [contenuto=" + contenuto + ", idMessaggio=" + idMessaggio + ", mittente=" + mittente
				+ ", destinatario=" + destinatario + ", tipologia=" + tipologia + ", stato=" + stato + ", data=" + data
				+ "]";
	}
}
