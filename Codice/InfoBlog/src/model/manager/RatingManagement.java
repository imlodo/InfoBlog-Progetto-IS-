package model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import model.bean.Rating;
import storage.DriverManagerConnectionPool;
/**
 * Classe che implementa i metodi dell'interfaccia CRUD, questi metodi permettono di fare operazioni con
 * il BD come salvataggio, recupero dati, cancellazioni relative al rating di un articolo
 * 
 *
 */
public class RatingManagement implements ItemModel<Rating,String>
{
	private DriverManagerConnectionPool forConnection;
	private Connection conn;
	private ResultSet set;
	private PreparedStatement statement;

	public RatingManagement(DriverManagerConnectionPool pool)
	{
		forConnection=pool;
	}
	/**
	 * Metodo per recuperare le informazioni relative al rating fornito da un utente ad un articolo
	 * @param item_value String :  chiave fornita nel seguente fomato idArticolo emailUtente
	 * @return  rat Rating l'oggetto rappresenta il rating fornito da un utente
	 */
	@Override
	public Rating doRetrieveByKey(String item_value) throws SQLException 
	{
		String query="SELECT numeroStelle FROM rating WHERE id=? AND email=?";
	    Rating rat=null;
	
	    try
	    {
	        conn=forConnection.getConnection();
	        statement=conn.prepareStatement(query);
	        statement.setString(1,item_value.substring(0,item_value.indexOf(' ')));
	        statement.setString(2,item_value.substring(item_value.indexOf(' ')+1));
	        set=statement.executeQuery();
	        while(set.next())
	        {
	            rat=new Rating();
	            rat.setNumeroStelle(Float.parseFloat(set.getString("numeroStelle")));
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
	    return rat;
	}

	/**
	 * Metodo per recuperare le informazioni sul rating di un articolo
	 * @param order String : identificativo dell'articolo
	 * @return  rat Collection<Rating> : l'oggetto rappresenta il rating medio dell'articolo
	 */
	@Override
	public Collection<Rating> doRetrieveAll(String order) throws SQLException {
		String query="SELECT avg(numeroStelle) as stelle FROM rating WHERE id=?";
	    ArrayList<Rating> rat=new ArrayList<Rating>();
	    Rating rating;
	    
	    try
	    {
	        conn=forConnection.getConnection();
	        statement=conn.prepareStatement(query);
	        statement.setString(1,order);
	        ResultSet set=statement.executeQuery();
	        while(set.next())
	        {
	            rating=new Rating();
	            rating.setNumeroStelle(set.getFloat(("stelle")));
	            rat.add(rating);
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
	    return rat;
	}

	/**
	 * Metodo per memorizzare le informazioni sul rating 
	 * @param item Rating rating da memorizzare
	 *
	 */
	@Override
	public void doSave(Rating item) throws SQLException 
	{
		String query="INSERT INTO rating(numeroStelle,id,email) VALUES(?,?,?)";

		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);

			statement.setString(1,String.valueOf(item.getNumeroStelle()));
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
	 * Metodo per aggiornare le informazione sul rating
	 * @param item Rating rating contenente le informazioni aggiornate che verranno memorizzate
	 *
	 */
	@Override
	public void doUpdate(Rating item) throws SQLException
	{
		String query="UPDATE rating SET numeroStelle=? WHERE id=? AND email=?";

		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);

			statement.setString(1,String.valueOf(item.getNumeroStelle()));
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
	public boolean doDelete(Rating item) throws SQLException {
		return false;
	}
}
