package model.manager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import model.bean.Utente;
import storage.DriverManagerConnectionPool;
/**
 * Classe che implementa i metodi dell'interfaccia CRUD, questi metodi permettono di fare operazioni con
 * il BD come salvataggio, recupero dati, cancellazioni relative agli utenti
 * 
 */
public class UserManagement implements ItemModel<Utente, String>
{
	private DriverManagerConnectionPool pool;
	public UserManagement(DriverManagerConnectionPool pool)
	{
		this.pool = pool;
	}
	/**
	 * Metodo per recuperare le informazioni di un utente
	 * @param email String , email dell'utente
	 * @return  utente Utente l'oggetto rappresenta l'utente ricercato
	 */
	@Override
	public Utente doRetrieveByKey(String email) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL =
						"SELECT * "  
						+"FROM utente " 
						+"WHERE email = ? ;";
		Utente utente=null;
		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			//riempio il parametro selectSQL = ?
			preparedStatement.setString(1, email);

			//System.out.println("doRetrieveAll: " + preparedStatement.toString());
			
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				utente = new Utente();
				utente.setEmail(rs.getString("email"));
				utente.setPassword(rs.getString("password"));
				utente.setNome(rs.getString("nome"));
				utente.setCognome(rs.getString("cognome"));
				utente.setUsername(rs.getString("username"));
			}
		}
		finally
		{
			try
			{
				if(preparedStatement != null)
					preparedStatement.close();
			}
			finally
			{
				pool.releaseConnection(connection);
			}
		}
		return utente;
	}
	/**
	 * Metodo per recuperare le informazioni di più utenti in un determinato ordine
	 * @param order String : criterio di ordinamento
	 * @return  utenti Collection<Utente> l'oggetto rappresenta l'insieme degli utenti ricercati
	 */
	@Override
	public Collection<Utente> doRetrieveAll(String order) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Utente> utenti = new ArrayList<Utente>();
		String selectSQL = "SELECT * FROM utente";

		if(order != null && !order.equals(""))
		{
			selectSQL += " ORDER BY " + order;
		}

		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			//System.out.println("doRetrieveAll: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while(rs.next())
			{
				Utente utente = new Utente();
				utente.setEmail(rs.getString("email"));
				utente.setPassword(rs.getString("password"));
				utente.setNome(rs.getString("nome"));
				utente.setCognome(rs.getString("cognome"));
				utente.setUsername(rs.getString("username"));
				utenti.add(utente);
			}
		}
		finally
		{
			try
			{
				if(preparedStatement != null)
					preparedStatement.close();
			}
			finally
			{
				pool.releaseConnection(connection);
			}
		}

		return utenti;
	}

	/**
	 * Metodo per memorizzare le informazione di un utente
	 * @param utente Utente utente da memorizzare
	 *
	 */
	@Override
	public void doSave(Utente utente) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;


		String insertSQL = "INSERT INTO utente " + 
				" (email, password, nome, cognome, username) " +
				" VALUES(?,?,?,?,?)";

		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setString(1, utente.getEmail());
			preparedStatement.setString(2, utente.getPassword());
			preparedStatement.setString(3, utente.getNome());
			preparedStatement.setString(4, utente.getCognome());
			preparedStatement.setString(5, utente.getUsername());
			
			//System.out.println("doSave: " + preparedStatement.toString());
			preparedStatement.executeUpdate();
			
			//Solo in questo momento la modifica viene resa visibile a tutti
			connection.commit();
			
		}
		finally
		{
			try
			{
				if(preparedStatement != null)
					preparedStatement.close();
			}
			finally
			{
				pool.releaseConnection(connection);
			}
		}
		
	}
	/**
	 * Metodo per aggiornare le informazione di un utente
	 * @param utente Utente utente contenente le informazioni aggiornate che verranno memorizzate
	 *
	 */
	@Override
	public void doUpdate(Utente utente) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE utente SET " + 
				" password = ?,  nome = ?, cognome = ?, username = ?" +
				" WHERE email = ?";

		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, utente.getPassword());
			preparedStatement.setString(2, utente.getNome());
			preparedStatement.setString(3, utente.getCognome());
			preparedStatement.setString(4, utente.getUsername());
			preparedStatement.setString(5, utente.getEmail());
			
			//System.out.println("doUpdate: " + preparedStatement.toString());
			preparedStatement.executeUpdate();
			//Solo in questo momento la modifica viene resa visibile a tutti
			connection.commit();
		}
		finally
		{
			try
			{
				if(preparedStatement != null)
					preparedStatement.close();
			}
			finally
			{
				pool.releaseConnection(connection);
			}
		}
	}
	/**
	 * Metodo per cancellare le informazione di un utente
	 * @param utente Utente utente contenente le informazioni che devono essere cancellate
	 * 
	 */
	@Override
	public boolean doDelete(Utente utente) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String deleteSQL = "DELETE FROM utente WHERE email = ?";
		int result = 0;
		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, utente.getEmail());
			
			//System.out.println("doUpdate: " + preparedStatement.toString());
			result = preparedStatement.executeUpdate();
			connection.commit();
		}
		finally
		{
			try
			{
				if(preparedStatement != null)
					preparedStatement.close();
			}
			finally
			{
				pool.releaseConnection(connection);
			}
		}
		return (result == 0 ? false : true);
	}

}
