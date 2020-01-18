package model.bean;

public class Autore 
{
	private String email;
	private String password;
	private String nome;
	private String cognome;
	private String username;
	
	public Autore()
	{
		
	}
	
	public Autore(String email, String password, String nome, String cognome, String username) 
	{
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
	}

	public String getEmail() 
	{
		return email;
	}

	public String getPassword() 
	{
		return password;
	}

	public String getNome() 
	{
		return nome;
	}

	public String getCognome() 
	{
		return cognome;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}
}
