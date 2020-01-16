package model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import model.bean.Rating;
import storage.DriverManagerConnectionPool;

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

	@Override
	public Rating doRetrieveByKey(String item_value) throws SQLException 
	{
		String query="SELECT avg(numeroStelle) as stelle FROM rating WHERE id=?";
		Rating rat=null;

		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);

			statement.setString(1,item_value);
			set=statement.executeQuery();
			while(set.next())
			{
				rat=new Rating();
				rat.setNumeroStelle(Float.parseFloat(set.getString("stelle")));
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

	@Override
	public Collection<Rating> doRetrieveAll(String order) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

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
