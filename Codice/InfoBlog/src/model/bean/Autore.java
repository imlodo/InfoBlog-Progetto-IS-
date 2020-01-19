package model.bean;



/**
 * Classe Autore, ha get e set per ottenere le informazioni dell'autore: Email,Password,Nome,Cognome,Username
 *
 * 
 */
public class Autore 
{
	private String email;
	private String password;
	private String nome;
	private String cognome;
	private String username;
	/**
	 * Costruttore vuoto
	 */
	public Autore()
	{
		
	}
	/**
	 * Costrutture di autore
	 * @param email String : Rappresenta l'email dell'autore
	 * @param password String : Rappresenta la passoword utilizzata per accedere alla piattaforma dell'autore
	 * @param nome String : Rappresenta il nome dell'autore
	 * @param cognome String : Rappresente il cognome dell'autore
	 * @param username String : Rappresenta l'username dell'autore all'interno della piattaforma
	 */
	public Autore(String email, String password, String nome, String cognome, String username) 
	{
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
	}

	/**
	 * Restituisce la mail dell'autore
	 * @return email String : rappresenta la mail dell'autore
	 */
	public String getEmail() 
	{
		return email;
	}
	/**
	 * Restituisce la password dell'autore
	 * @return password String: rappresenta la password dell'autore per accedere alla piattaforma
	 */
	public String getPassword() 
	{
		return password;
	}
	/**
	 * Restituisce il nome dell'autore
	 * @return nome String : rappresenta il nome dell'autore
	 */
	public String getNome() 
	{
		return nome;
	}
	/**
	 * Restituisce il cognome dell'autore
	 * @return cognome String : rappresenta il cognome dell'autore
	 */
	public String getCognome() 
	{
		return cognome;
	}
	/**
	 * Restituisce l username dell'autore
	 * @return username String : rappresenta l'username dell'autore all'interno della piattaforma
	 */
	public String getUsername() 
	{
		return username;
	}
	/**
	 * Imposta la mail dell'autore
	 * @param email : rappresenta la mail dell'autore
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Imposta la password dell'autore
	 * @param password : rappresenta la password dell'autore per accedere alla piattaforma
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}
	/**
	 * Imposta il nome dell'autore
	 * @param nome : rappresenta il nome dell'autore
	 */
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	/**
	 * Imposta il cognome dell'autore
	 * @param cognome : rappresenta il cognome dell'autore
	 */

	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}
	/**
	 * Imposta l username dell'autore
	 * @param username : rappresenta l'username dell'autore all'interno della piattaforma
	 */
	public void setUsername(String username) 
	{
		this.username = username;
	}
}
