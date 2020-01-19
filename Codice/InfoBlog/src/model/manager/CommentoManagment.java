package model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import model.bean.Commento;
import storage.DriverManagerConnectionPool;
/**
 * Classe che implementa i metodi dell'interfaccia CRUD, questi metodi permettono di fare operazioni con
 * il BD come salvataggio, recupero dati, cancellazioni relative ai commenti
 * 
 *
 */
public class CommentoManagment implements ItemModel<Commento,String>
{
	private DriverManagerConnectionPool forConnection;
	private Connection conn;
	private ResultSet set;
	private PreparedStatement statement;
	
	public CommentoManagment(DriverManagerConnectionPool pool)
	{
		forConnection=pool;
	}
	
	/**
	 * Metodo per recuperare un commento tramite il suo identidicatico
	 * @param item_value String, l'identificativo dell commento fornito nel seguente formato idArticolo emailUtente
	 * @return comm Commento, l'oggetto che rappresenta il commento cercato
	 */
	@Override
	public Commento doRetrieveByKey(String item_value) throws SQLException
	{
		String query="SELECT * FROM commento WHERE id=? AND email=?";
		Commento comm=null;
		UserManagement DAOUtente=new UserManagement(new DriverManagerConnectionPool());
		
		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);
			
			statement.setString(1,item_value.substring(0,item_value.indexOf(' ')));
			statement.setString(2,item_value.substring(item_value.indexOf(' ')+1));
			set=statement.executeQuery();
			while(set.next())
			{
				comm=new Commento();
				comm.setContenuto(set.getString("valutazione"));
				comm.setUtente(DAOUtente.doRetrieveByKey(set.getString("email")));
			}
		}
		finally
		{
			try
			{
				if(statement!=null)
					statement.close();
			}
			finally
			{
				forConnection.releaseConnection(conn);
			}
		}
		return comm;
	}
	/**
	 * Metodo per recuperare un insieme di commenti tramite identidicatico di un articolo
	 * @param order String, l'identificativo dell'articolo da cui prelevare i commenti
	 * @return commenti Collection<Commento>, l'oggetto che rappresenta il commento cercato
	 */
	@Override
	public Collection<Commento> doRetrieveAll(String order) throws SQLException
	{
		ArrayList<Commento> commenti=new ArrayList<Commento>();
		String query="SELECT * FROM commento WHERE id=?";
		Commento comm=null;
		UserManagement DAOUtente=new UserManagement(new DriverManagerConnectionPool());
		
		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);
			
			statement.setString(1,order);
			set=statement.executeQuery();
			while(set.next())
			{
				comm=new Commento();
				comm.setContenuto(set.getString("valutazione"));
				comm.setUtente(DAOUtente.doRetrieveByKey(set.getString("email")));
				commenti.add(comm);
			}
		}
		finally
		{
			try
			{
				if(statement!=null)
					statement.close();
			}
			finally
			{
				forConnection.releaseConnection(conn);
			}
		}
		return commenti;
	}

	/**
	 * Metodo per memorizzare un commento
	 * @param item Commento, l'oggetto che rappresenta il commento da memorizzare
	 */
	@Override
	public void doSave(Commento item) throws SQLException 
	{
		String query="INSERT INTO commento(valutazione,id,email) VALUES(?,?,?)";
		
		
		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);
			
			statement.setString(1,item.getContenuto());
			statement.setString(2, String.valueOf(item.getArticolo().getId()));
			statement.setString(3,item.getUtente().getEmail());
			statement.executeUpdate();
			conn.commit();
		}
		finally
		{
			try
			{
				if(statement!=null)
					statement.close();
			}
			finally
			{
				forConnection.releaseConnection(conn);
			}
		}
	}
	/**
	 * Metodo per aggiornare le informazioni di un commento
	 * @param item Commento, l'oggetto che rappresenta il commento con le informazioni aggiornate da memorizzare
	 */
	@Override
	public void doUpdate(Commento item) throws SQLException 
	{
		String query="UPDATE commento SET valutazione=? WHERE id=? AND email=?";
		
		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);
			
			statement.setString(1,item.getContenuto());
			statement.setString(2, String.valueOf(item.getArticolo().getId()));
			statement.setString(3,item.getUtente().getEmail());
			statement.executeUpdate();
			conn.commit();
		}
		finally
		{
			try
			{
				if(statement!=null)
					statement.close();
			}
			finally
			{
				forConnection.releaseConnection(conn);
			}
		}
	}

	@Override
	public boolean doDelete(Commento item) throws SQLException {
		return false;
	}
}
