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
/**
 * Classe che implementa i metodi dell'interfaccia CRUD, questi metodi permettono di fare operazioni con
 * il BD come salvataggio, recupero dati, cancellazioni relative all'articolo
 * 
 *
 */
public class ArticoloManagement implements ItemModel<Articolo,String>
{
	private DriverManagerConnectionPool forConnection;
	private Connection conn;
	private ResultSet set;
	private PreparedStatement statement;

	/**
	 * Costruttore
	 * @param pool DriverManagerConnectionPool
	 */
	public ArticoloManagement(DriverManagerConnectionPool pool)
	{
		forConnection=pool;
	}
	/**
	 * Metodo per recuperare un articolo tramite il suo identidicatico
	 * @param item_value String, l'identificativo dell'articolo
	 * @return article Articolo, l'oggetto che rappresenta l'articolo cercato
	 */
	@Override
	public Articolo doRetrieveByKey(String item_value) throws SQLException {
		Articolo article=null;
		String query="SELECT * FROM articolo WHERE id=?";

		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);
			AutoreManagement DAOAutore =new AutoreManagement(new DriverManagerConnectionPool());
			ModeratoreManagement DAOModeratore= new ModeratoreManagement(new DriverManagerConnectionPool());

			statement.setInt(1,Integer.parseInt(item_value));
			set=statement.executeQuery();
			while(set.next())
			{
				article=new Articolo();
				article.setAutore(DAOAutore.doRetrieveByKey(set.getString("scrittore")));
				article.setCategoria(set.getString("categoria"));
				article.setContenuto(set.getString("contenuto"));
				article.setData(LocalDate.parse(set.getString("dataPubblicazione")));
				article.setId(set.getInt("id"));
				article.setStato(set.getString("stato"));
				article.setTitolo(set.getString("Titolo"));
				String moderatore=set.getString("moderatore");
				if(moderatore!=null)
					article.setModeratore(DAOModeratore.doRetrieveByKey(moderatore));
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
	/**
	 * Metodo per recuperare gli articoli articoli mediate le seguenti convenzioni:
	 * a:email autore ottieni tutti gli articoli di un dato autore
	 * m:categoriaModerazione per ottenere gli articoli da moderare per una categoria
	 * u: per visualizzare gli articoli pubblicati
	 * e: per visualizzare gli articoli recentemente pubblicati
	 * Stringa vuora per visualizare tutti gli articoli
	 * @param order String, contiene il criterio di recupero
	 * @return articles Collection<Articolo> , l'oggetto che rappresenta gli articoli cercati
	 */
	@Override
	public Collection<Articolo> doRetrieveAll(String order) throws SQLException 
	{
		AutoreManagement DAOAutore =new AutoreManagement(new DriverManagerConnectionPool());
		String query="SELECT * FROM articolo";
		ArrayList<Articolo> articles=new ArrayList<Articolo>();
		ModeratoreManagement DAOModeratore= new ModeratoreManagement(new DriverManagerConnectionPool());
		
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
				String moderatore=set.getString("moderatore");
				if(moderatore!=null)
					article.setModeratore(DAOModeratore.doRetrieveByKey(moderatore));
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
	/**
	 * Metodo per memorizzare la informazioni sull'articolo
	 * @param item Articolo : articolo da memorizzare
	 * 
	 */
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
			System.out.println(statement.toString());
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
	 * Metodo per aggiornare le informazioni sull'articolo
	 * @param item Articolo : articolo contenente le informazioni aggiornate che verranno memorizzate
	 * 
	 */
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
			if(item.getModeratore()==null)
				statement.setNull(6, java.sql.Types.NULL);
			else
				statement.setString(6, item.getModeratore().getEmail());
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
	/**
	 * Metodo per recuperare gli articoli in base al titolo
	 * @param text String : titolo dell'articolo
	 * 
	 */
	public Collection<Articolo> doRetrieveByTitolo(String text) throws SQLException
	{
		Collection<Articolo> articoli = new ArrayList<Articolo>();
		String selectSQL = "SELECT * FROM articolo WHERE titolo LIKE ?";
		AutoreManagement DAOAutore=new AutoreManagement(forConnection); 
		
		try
		{
			conn = forConnection.getConnection();
			statement = conn.prepareStatement(selectSQL);
			statement.setString(1, "%"+text+"%");
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next())
			{
				Articolo articolo = new Articolo();
				articolo.setTitolo(rs.getString("Titolo"));
				articolo.setId(rs.getInt("id"));
				articolo.setCategoria(rs.getString("categoria"));
				articolo.setStato(rs.getString("stato"));
				articolo.setData(rs.getDate("dataPubblicazione").toLocalDate());
				articolo.setAutore(DAOAutore.doRetrieveByKey(rs.getString("scrittore")));
				articoli.add(articolo);
			}
		}
		finally
		{
			try
			{
				if(statement != null)
					statement.close();
			}
			finally
			{
				forConnection.releaseConnection(conn);
			}
		}
		return articoli;
	}
	/**
	 * Metodo per cancellare un articolo
	 * @param item Articolo : articolo da cancellare
	 * @return flag : rappresenta l'esito della cancellazione
	 */
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
