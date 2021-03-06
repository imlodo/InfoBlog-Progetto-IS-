package model.manager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import model.bean.Moderatore;
import storage.DriverManagerConnectionPool;
/**
 * Classe che implementa i metodi dell'interfaccia CRUD, questi metodi permettono di fare operazioni con
 * il BD come salvataggio, recupero dati, cancellazioni relative ai moderatori
 * 
 *
 */
public class ModeratoreManagement implements ItemModel<Moderatore, String>
{
	private DriverManagerConnectionPool pool;
	public ModeratoreManagement(DriverManagerConnectionPool pool)
	{
		this.pool = pool;
	}
	/**
	 * Metodo per recuperare le informazioni di un moderatore
	 * @param email String , email del moderatore
	 * @return  moderatore Moderatore l'oggetto rappresenta il moderatore ricercato
	 */
	@Override
	public Moderatore doRetrieveByKey(String email) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL =
						"SELECT * "  
						+"FROM moderatore " 
						+"WHERE email = ? ;";
		Moderatore moderatore;
		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			//riempio il parametro selectSQL = ?
			preparedStatement.setString(1, email);

			//System.out.println("doRetrieveAll: " + preparedStatement.toString());
			moderatore = new Moderatore();
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				moderatore.setEmail(rs.getString("email"));
				moderatore.setPassword(rs.getString("password"));
				moderatore.setNome(rs.getString("nome"));
				moderatore.setCognome(rs.getString("cognome"));
				moderatore.setUsername(rs.getString("username"));
				moderatore.setCategoria_moderazione(rs.getString("categoriaModerazione"));
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
		return moderatore;
	}
	/**
	 * Metodo per recuperare le informazioni di pi� moderatori in un determinato ordine
	 * @param order String criterio di ordinamento
	 * @return  moderatori Collection<Moderatore> l'oggetto rappresenta l'insieme dei moderatori ricercati
	 */
	@Override
	public Collection<Moderatore> doRetrieveAll(String order) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Moderatore> moderatori = new ArrayList<Moderatore>();
		String selectSQL = "SELECT * FROM moderatore";

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
				Moderatore moderatore = new Moderatore();
				moderatore.setEmail(rs.getString("email"));
				moderatore.setPassword(rs.getString("password"));
				moderatore.setNome(rs.getString("nome"));
				moderatore.setCognome(rs.getString("cognome"));
				moderatore.setUsername(rs.getString("username"));
				moderatore.setCategoria_moderazione(rs.getString("categoriaModerazione"));
				moderatori.add(moderatore);
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

		return moderatori;
	}
	/**
	 * Metodo per memorizzare le informazione di un moderatore
	 * @param moderatore Moderatore moderatore da memorizzare
	 *
	 */
	@Override
	public void doSave(Moderatore moderatore) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;


		String insertSQL = "INSERT INTO moderatore " + 
				" (email, password, nome, cognome, username, categoriaModerazione) " +
				" VALUES(?,?,?,?,?,?)";

		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setString(1, moderatore.getEmail());
			preparedStatement.setString(2, moderatore.getPassword());
			preparedStatement.setString(3, moderatore.getNome());
			preparedStatement.setString(4, moderatore.getCognome());
			preparedStatement.setString(5, moderatore.getUsername());
			preparedStatement.setString(6, moderatore.getCategoria_moderazione());

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
	 * Metodo per aggiornare le informazione di un moderatore
	 * @param moderatore Moderatore moderatore contenente le informazioni aggiornate che verranno memorizzate
	 *
	 */
	@Override
	public void doUpdate(Moderatore moderatore) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE moderatore SET " + 
				" password = ?,  nome = ?, cognome = ?, username = ?, categoriaModerazione = ?" +
				" WHERE email = ?";

		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, moderatore.getPassword());
			preparedStatement.setString(2, moderatore.getNome());
			preparedStatement.setString(3, moderatore.getCognome());
			preparedStatement.setString(4, moderatore.getUsername());
			preparedStatement.setString(5, moderatore.getCategoria_moderazione());
			preparedStatement.setString(6, moderatore.getEmail());

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
	 * Metodo per cancellare le informazione di un moderatore
	 * @param moderatore Moderatore:  moderatore contenente le informazioni che devono essere cancellate
	 * 
	 */
	@Override
	public boolean doDelete(Moderatore moderatore) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String deleteSQL = "DELETE FROM moderatore WHERE email = ?";
		int result = 0;
		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, moderatore.getEmail());
			
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
