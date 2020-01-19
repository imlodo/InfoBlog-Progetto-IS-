package model.bean;


/**
 * Classe Moderatore, ha get e set per ottenere le informazioni dell'autore: Email,Password,Nome,Cognome,Username,CategoriaModerazione
 *
 * 
 */
public class Moderatore 
{
	private String email;
	private String password;
	private String nome;
	private String cognome;
	private String username;
	private String categoria_moderazione;


	/**
	 * Costruttore vuoto
	 */
	public Moderatore()
	{}
	/**
	 * Costrutture di moderatore
	 * @param email String : Rappresenta l'email del moderatore
	 * @param password String : Rappresenta la passoword utilizzata per accedere alla piattaforma dal moderatore
	 * @param nome String : Rappresenta il nome del moderatore
	 * @param cognome String : Rappresente il cognome del moderatoree
	 * @param username String : Rappresenta l'username del moderatore all'interno della piattaforma
	 * @param categoria :String  Rappresenta la categoria di moderazione del moderatore
	 */
	public Moderatore(String email, String password, String nome, String cognome, String username, String categoria) 
	{
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.categoria_moderazione = categoria;
	}
	/**
	 * Restituisce la mail del moderatore
	 * @return email String : rappresenta la mail del moderatore
	 */
	public String getEmail() 
	{
		return email;
	}
	/**
	 * Restituisce la password del moderatore
	 * @return password String : rappresenta la password del moderatore per accedere alla piattaforma
	 */
	public String getPassword() 
	{
		return password;
	}
	/**
	 * Restituisce il nome del moderatore
	 * @return nome String : rappresenta il nome del moderatoree
	 */
	public String getNome() 
	{
		return nome;
	}
	/**
	 * Restituisce il cognome del moderatore
	 * @return cognome String : rappresenta il cognome del moderatore
	 */
	public String getCognome() 
	{
		return cognome;
	}
	/**
	 * Restituisce l username  del moderatore
	 * @return username String : rappresenta l'username  del moderatore all'interno della piattaforma
	 */
	public String getUsername() 
	{
		return username;
	}
	/**
	 * Restituisce la categoria di moderazione  del moderatore
	 * @return categoria_moderazione String : rappresenta la categoria di moderazione  del moderatore
	 */	
	public String getCategoria_moderazione()
	{
		return categoria_moderazione;
	}
	/**
	 * Imposta la mail del moderatore
	 * @param email : rappresenta la mail del moderatore
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Imposta la password del moderatore
	 * @param password : rappresenta la password del moderatore per accedere alla piattaforma
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}
	/**
	 * Imposta il nome del moderatore
	 * @param nome : rappresenta il nome del moderatoree
	 */
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	/**
	 * Imposta il cognome del moderatore
	 * @param cognome : rappresenta il cognome del moderatore
	 */
	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}
	/**
	 * Imposta l username  del moderatore
	 * @param username : rappresenta l'username  del moderatore all'interno della piattaforma
	 */
	public void setUsername(String username) 
	{
		this.username = username;
	}
	/**
	 * Imposta la categoria di moderazione  del moderatore
	 * @param categoria_moderazione : rappresenta la categoria di moderazione  del moderatore
	 */	
	public void setCategoria_moderazione(String categoria_moderazione)
	{
		this.categoria_moderazione = categoria_moderazione;
	}
	
}
