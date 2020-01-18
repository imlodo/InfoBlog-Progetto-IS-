package model.bean;

import java.time.LocalDate;

public class Evento 
{
	private String via,città,nome,argomento;
	private LocalDate data;
	private int idEvento;
	private Autore autore;  // sostituire con la classe;

	public Evento(String via, String città, String nome, String argomento, LocalDate data, int idEvento,Autore autore)
	{
		super();
		this.via = via;
		this.città = città;
		this.nome = nome;
		this.argomento = argomento;
		this.data = data;
		this.idEvento = idEvento;
		this.autore = autore;
	}
	public Evento(){}
	
	public int getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public String getVia() 
	{
		return via;
	}
	public void setVia(String via)
	{
		this.via = via;
	}
	public String getCittà() 
	{
		return città;
	}
	public void setCittà(String città) 
	{
		this.città = città;
	}
	public String getNome() 
	{
		return nome;
	}
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	public String getArgomento()
	{
		return argomento;
	}
	public void setArgomento(String argomento) 
	{
		this.argomento = argomento;
	}
	public LocalDate getData()
	{
		return data;
	}
	public void setData(LocalDate data)
	{
		this.data = data;
	}
	public Autore getAutore() {
		return autore;
	}
	public void setAutore(Autore autore) {
		this.autore = autore;
	}
}
