package model.bean;

import java.time.LocalDate;

public class Evento 
{
	private String via,citt�,nome,argomento;
	private LocalDate data;
	private int idEvento;
	private Autore autore;  // sostituire con la classe;
	
	public Evento(String via, String citt�, String nome, String argomento, LocalDate data,Autore autore)
	{
		this.via = via;
		this.citt� = citt�;
		this.nome = nome;
		this.argomento = argomento;
		this.data = data;
		this.autore=autore;
		this.idEvento=0;
	}
	public int getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}
	public Evento()
	{
		via="";
		citt�="";
		nome="";
		argomento="";
		data=null;
		autore=null;
		idEvento=0;
	}
	public String getVia() 
	{
		return via;
	}
	public void setVia(String via)
	{
		this.via = via;
	}
	public String getCitt�() 
	{
		return citt�;
	}
	public void setCitt�(String citt�) 
	{
		this.citt� = citt�;
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
	@Override
	public String toString() {
		return "Evento [via=" + via + ", citt�=" + citt� + ", nome=" + nome + ", argomento=" + argomento + ", data="
				+ data + ", idEvento=" + idEvento + ", autore=" + autore + "]";
	}
}