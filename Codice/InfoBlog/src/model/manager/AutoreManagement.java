package model.manager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import model.bean.Autore;
import storage.DriverManagerConnectionPool;
/**
 * Classe che implementa i metodi dell'interfaccia CRUD, questi metodi permettono di fare operazioni con
 * il BD come salvataggio, recupero dati, cancellazioni relative agli autore
 * 
 *
 */
public class AutoreManagement implements ItemModel<Autore, String>
{
	private DriverManagerConnectionPool pool;
	public AutoreManagement(DriverManagerConnectionPool pool)
	{
		this.pool = pool;
	}
	
	/**
	 * Metodo per recuperare le informazioni di un autore
	 * @param email String , email dell'autore
	 * @return  autore Autore l'oggetto rappresenta l'autore ricercato
	 */
	@Override
	public Autore doRetrieveByKey(String email) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL =
						"SELECT * "  
						+"FROM autore " 
						+"WHERE email = ? ;";
		Autore autore=null;
		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			//riempio il parametro selectSQL = ?
			preparedStatement.setString(1, email);

			//System.out.println("doRetrieveAll: " + preparedStatement.toString());
			autore = new Autore();
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				autore.setEmail(rs.getString("email"));
				autore.setPassword(rs.getString("password"));
				autore.setNome(rs.getString("nome"));
				autore.setCognome(rs.getString("cognome"));
				autore.setUsername(rs.getString("username"));
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
		return autore;
	}
	/**
	 * Metodo per recuperare le informazioni di più autori
	 * @param email String , email dell'autore
	 * @return  autori Collection<Autore> l'oggetto rappresenta l'insieme degli autori ricercati
	 */
	@Override
	public Collection<Autore> doRetrieveAll(String order) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Autore> autori = new ArrayList<Autore>();
		String selectSQL = "SELECT * FROM autore";

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
				Autore autore = new Autore();
				autore.setEmail(rs.getString("email"));
				autore.setPassword(rs.getString("password"));
				autore.setNome(rs.getString("nome"));
				autore.setCognome(rs.getString("cognome"));
				autore.setUsername(rs.getString("username"));
				autori.add(autore);
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

		return autori;
	}
	/**
	 * Metodo per memorizzare le informazione di un autore
	 * @param autore Autore autore da memorizzare
	 *
	 */
	@Override
	public void doSave(Autore autore) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;


		String insertSQL = "INSERT INTO autore " + 
				" (email, password, nome, cognome, username) " +
				" VALUES(?,?,?,?,?)";

		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setString(1, autore.getEmail());
			preparedStatement.setString(2, autore.getPassword());
			preparedStatement.setString(3, autore.getNome());
			preparedStatement.setString(4, autore.getCognome());
			preparedStatement.setString(5, autore.getUsername());

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
	 * Metodo per aggiornare le informazione di un autore
	 * @param autore Autore autore contenente le informazioni aggiornate che verranno memorizzate
	 *
	 */
	@Override
	public void doUpdate(Autore autore) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE autore SET " + 
				" password = ?,  nome = ?, cognome = ?, username = ?" +
				" WHERE email = ?";

		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, autore.getPassword());
			preparedStatement.setString(2, autore.getNome());
			preparedStatement.setString(3, autore.getCognome());
			preparedStatement.setString(4, autore.getUsername());
			preparedStatement.setString(5, autore.getEmail());

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
	 * Metodo per cancellare le informazione di un autore
	 * @param autore Autore autore contenente le informazioni che devono essere cancellate
	 * 
	 */

	@Override
	public boolean doDelete(Autore autore) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String deleteSQL = "DELETE FROM autore WHERE email = ?";
		int result = 0;
		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, autore.getEmail());
			
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
	/**
	 * Permette di prelevare dal DB tutti gli autori che hanno come sottostringa il parametro text nel proprio username
	 * @param text String, la sottostringa da controllare
	 * @return Una Collection di tipo Autore con il suo interno tutti gli autori che hanno la sottostringa text nel proprio username
	 * @throws SQLException
	 * @throws AutoreNonPresenteException 
	 */
	public Collection<Autore> doRetrieveByUsernameAutore(String text) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Autore> autori = new ArrayList<Autore>();
		String selectSQL = "SELECT * FROM autore WHERE username LIKE ?";
		
		try
		{
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, "%"+text+"%");
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				Autore autore = new Autore();
				autore.setEmail(rs.getString("email"));
				autore.setPassword(rs.getString("password"));
				autore.setNome(rs.getString("nome"));
				autore.setCognome(rs.getString("cognome"));
				autore.setUsername(rs.getString("username"));
				autori.add(autore);
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
		return autori;
	}
}