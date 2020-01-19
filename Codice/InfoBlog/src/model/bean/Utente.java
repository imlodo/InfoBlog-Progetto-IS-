package model.bean;

public class Utente 
{
	private String email;
	private String password;
	private String nome;
	private String cognome;
	private String username;
	/**
	 * Costruttore vuoto
	 */
	public Utente()
	{}
	/**
	 * Costrutture di utente
	 * @param email String : Rappresenta l'email dell'autore
	 * @param password String : Rappresenta la passoword utilizzata per accedere alla piattaforma dell'autore
	 * @param nome String : Rappresenta il nome dell'autore
	 * @param cognome String : Rappresente il cognome dell'autore
	 * @param username String : Rappresenta l'username dell'autore all'interno della piattaforma
	 */
	public Utente(String email, String password, String nome, String cognome, String username) 
	{
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
	}

	/**
	 * Restituisce la mail dell'utente
	 * @return email String : rappresenta la mail dell'utente
	 */
	public String getEmail() 
	{
		return email;
	}
	/**
	 * Restituisce la password dell'utente
	 * @return password String : rappresenta la password dell'utente per accedere alla piattaforma
	 */
	public String getPassword() 
	{
		return password;
	}
	/**
	 * Restituisce il nome dell'utente
	 * @return nome String : rappresenta il nome dell'utente
	 */
	public String getNome() 
	{
		return nome;
	}
	/**
	 * Restituisce il cognome dell'utente
	 * @return cognome String : rappresenta il cognome dell'utente
	 */
	public String getCognome() 
	{
		return cognome;
	}
	/**
	 * Restituisce l username dell'utente
	 * @return username String : rappresenta l'username dell'utente all'interno della piattaforma
	 */
	public String getUsername() 
	{
		return username;
	}
	/**
	 * Imposta la mail dell'utente
	 * @param email : rappresenta la mail dell'utente
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Imposta la password dell'utente
	 * @param password : rappresenta la password dell'utente per accedere alla piattaforma
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}
	/**
	 * Imposta il nome dell'utente
	 * @param nome : rappresenta il nome dell'utente
	 */
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	/**
	 * Imposta il cognome dell'utente
	 * @param cognome : rappresenta il cognome dell'utente
	 */

	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}
	/**
	 * Imposta l username dell'utente
	 * @param username : rappresenta l'username dell'utente all'interno della piattaforma
	 */
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	
}
