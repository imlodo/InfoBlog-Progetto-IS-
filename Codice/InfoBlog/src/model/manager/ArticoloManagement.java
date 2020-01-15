package model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import model.bean.Articolo;
import storage.DriverManagerConnectionPool;

public class ArticoloManagement implements ItemModel<Articolo,String>
{
	private DriverManagerConnectionPool forConnection;
	private Connection conn;
	private ResultSet set;
	private PreparedStatement statement;

	public ArticoloManagement(DriverManagerConnectionPool pool)
	{
		forConnection=pool;
	}

	@Override
	public Articolo doRetrieveByKey(String item_value) throws SQLException {
		Articolo article=null;
		String query="SELECT * FROM articolo WHERE id=?";

		try
		{
			article=new Articolo();
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);
			AutoreManagement DAOAutore =new AutoreManagement(new DriverManagerConnectionPool());


			statement.setInt(1,Integer.parseInt(item_value));
			set=statement.executeQuery();
			while(set.next())
			{
				article.setAutore(DAOAutore.doRetrieveByKey(set.getString("scrittore")));
				article.setCategoria(set.getString("categoria"));
				article.setContenuto(set.getString("contenuto"));
				article.setData(LocalDate.parse(set.getString("dataPubblicazione")));
				article.setId(set.getInt("id"));
				article.setStato(set.getString("stato"));
				article.setTitolo(set.getString("Titolo"));
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
		return article;
	}

	@Override
	public Collection<Articolo> doRetrieveAll(String order) throws SQLException 
	{
		AutoreManagement DAOAutore =new AutoreManagement(new DriverManagerConnectionPool());
		String query="SELECT * FROM articolo";
		ArrayList<Articolo> articles=new ArrayList<Articolo>();

		if(order.indexOf("a:")!=-1)
			query+=" WHERE scrittore=?";
		else
			if(order.indexOf("m:")!=-1)
				query+=" WHERE categoria=? AND stato=\"daPubblicare\"";
			else
				if(order.indexOf("u:")!=-1)
					query+=" WHERE stato=\"pubblicato\"";
				else
					if(order.indexOf("e:")!=-1)
						query+=" WHERE stato=\"pubblicato\" ORDER BY dataPubblicazione DESC";
		
				

		try 
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);
			if(order.indexOf("a:")!=-1)
				statement.setString(1,order.substring(order.indexOf("a:")+2));
			else 
				if(order.indexOf("m:")!=-1)
					statement.setString(1,order.substring(order.indexOf("m:")+2));
			set=statement.executeQuery();
			while(set.next())
			{
				Articolo article=new Articolo();
				article.setAutore(DAOAutore.doRetrieveByKey(set.getString("scrittore")));
				article.setCategoria(set.getString("categoria"));
				article.setContenuto(set.getString("contenuto"));
				article.setData(LocalDate.parse(set.getString("dataPubblicazione")));
				article.setId(set.getInt("id"));
				article.setStato(set.getString("stato"));
				article.setTitolo(set.getString("Titolo"));
				articles.add(article);
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
		return articles;
	}

	@Override
	public void doSave(Articolo item) throws SQLException 
	{
		String query="INSERT INTO articolo(Titolo,categoria,contenuto,dataPubblicazione,scrittore) VALUES (?,?,?,?,?)";

		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);

			statement.setString(1, item.getTitolo());
			statement.setString(2, item.getCategoria());
			statement.setString(3, item.getContenuto());
			statement.setString(4,String.valueOf(LocalDate.now()));
			statement.setString(5, item.getAutore().getEmail());
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
	public void doUpdate(Articolo item) throws SQLException 
	{
		String query="UPDATE articolo SET Titolo=?,categoria=?,contenuto=?,dataPubblicazione=?,stato=?,moderatore=? WHERE id=?";

		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);

			statement.setString(1, item.getTitolo());
			statement.setString(2, item.getCategoria());
			statement.setString(3, item.getContenuto());
			statement.setString(4,String.valueOf(item.getData()));
			statement.setString(5,item.getStato());
			statement.setString(5, item.getModeratore().getEmail());
			statement.setInt(7, item.getId());
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
	public boolean doDelete(Articolo item) throws SQLException
	{
		String query="DELETE FROM articolo WHERE id=?";
		Boolean flag=false;
		
		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);

			statement.setInt(1,item.getId());
			flag=statement.executeUpdate()>0;
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
		return flag;
	}
}
