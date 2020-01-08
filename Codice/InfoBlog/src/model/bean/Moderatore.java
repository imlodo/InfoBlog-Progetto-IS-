package model.bean;

public class Moderatore 
{
	private String email;
	private String password;
	private String nome;
	private String cognome;
	private String username;
	private String categoria_moderazione;
	
	public Moderatore()
	{
		
	}
	
	public Moderatore(String email, String password, String nome, String cognome, String username, String categoria) 
	{
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.categoria_moderazione = categoria;
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
	
	public String getCategoria_moderazione()
	{
		return categoria_moderazione;
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

	public void setCategoria_moderazione(String categoria_moderazione)
	{
		this.categoria_moderazione = categoria_moderazione;
	}
	
	public String toString()
	{
		return this.getClass().getSimpleName() + "[email = " + email + ", password = " + password +
				", nome = " + nome + ", cognome = " + cognome + ", username = " + username +
				", categoriaModerazione = "+ categoria_moderazione + "]";
		
	}
	
	
}
