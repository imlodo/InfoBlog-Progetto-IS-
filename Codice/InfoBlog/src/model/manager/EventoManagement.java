package model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import model.bean.Autore;
import model.bean.Evento;
import storage.DriverManagerConnectionPool;
import java.time.LocalDate;
/**
 * Classe che implementa i metodi dell'interfaccia CRUD, questi metodi permettono di fare operazioni con
 * il BD come salvataggio, recupero dati, cancellazioni relative agli eventi
 * 
 */
public class EventoManagement implements ItemModel<Evento,ArrayList<String>>
{
	private DriverManagerConnectionPool forConnection;
	private Connection conn;
	private ResultSet set;
	private PreparedStatement statement;
	private String doDeleteQuery="DELETE FROM evento WHERE data=? AND via=? AND città=? AND id=?";
	private String doUpdateQuery="UPDATE evento SET data=?,via=?,città=?,nome=?,argomento=? WHERE id=?";
	private String doSaveQuery="INSERT INTO evento(data,via,città,nome,argomento,email) VALUES (?,?,?,?,?,?) ";
	private String doRetrieveByKeyQuery="SELECT * FROM evento WHERE data=? AND via=? AND città=? AND id=?";
	private String doRetrieveAllQuery="SELECT * FROM evento WHERE data>=\""+String.valueOf(java.time.LocalDate.now())+"\"";
	
	public EventoManagement(DriverManagerConnectionPool pool)
	{
		forConnection=pool;
	}
	/**
	 * Metodo per recuperare un evento tramite il suo identidicatico
	 * @param item_value ArrayList<String>, l'identificativo dell'evento secondo il seguente formato data via citta e id
	 * @return event Evento, l'oggetto che rappresenta l'evento cercato
	 */
	@Override
	public Evento doRetrieveByKey(ArrayList<String> item_value) throws SQLException 
	{
		Evento event=null;
		
		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(doRetrieveByKeyQuery);
			
			for(int i=0;i<item_value.size();i++)
				statement.setString(i+1,item_value.get(i));
			set=statement.executeQuery();
			while(set.next())
			{
				event=new Evento();
				
				event.setData(LocalDate.parse(set.getString("data")));
				event.setArgomento(set.getString("argomento"));
				event.setNome(set.getString("nome"));
				event.setVia(set.getString("via"));
				event.setCittà(set.getString("città"));
				Autore scrittore=new AutoreManagement(new DriverManagerConnectionPool()).doRetrieveByKey(set.getString("email"));
				event.setAutore(scrittore);
				event.setIdEvento(set.getInt("id"));
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
		return event;	
	}
	/**
	 * Metodo per recuperare un insieme di eventi
	 * @param order String, contiene il criterio di recupero
	 * @return events ArrayList<Evento> , l'oggetto che rappresenta gli eventi cercati
	 */
	@Override
	public Collection<Evento> doRetrieveAll(String order) throws SQLException
	{
		ArrayList<Evento> events=null;
		String query=this.doRetrieveAllQuery;
		
		if(order.length()!=0)
			query+=" AND email=\""+order+"\" ORDER BY data ASC";
		else
			query+=" ORDER BY data ASC";

		try
		{
			conn=forConnection.getConnection();
			Statement statement=conn.createStatement();
			events=new ArrayList<Evento>();
			set=statement.executeQuery(query);
			while(set.next())
			{
				Evento event=new Evento();
				
				event.setData(LocalDate.parse(set.getString("data")));
				event.setArgomento(set.getString("argomento"));
				event.setNome(set.getString("nome"));
				event.setVia(set.getString("via"));
				event.setCittà(set.getString("città"));
				Autore scrittore=new AutoreManagement(new DriverManagerConnectionPool()).doRetrieveByKey(set.getString("email"));
				event.setAutore(scrittore);
				event.setIdEvento(set.getInt("id"));
				events.add(event);
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
		return events;	
	}
	/**
	 * Metodo per memorizzare la informazioni su un evento
	 * @param item Evento : l'evento da memorizzare
	 * 
	 */
	@Override
	public void doSave(Evento item) throws SQLException
	{
		
		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(doSaveQuery);
			
			
			statement.setString(1,String.valueOf(item.getData()));
			statement.setString(2,item.getVia());
			statement.setString(3,item.getCittà());
			statement.setString(4,item.getNome());
			statement.setString(5,item.getArgomento());
			statement.setString(6,item.getAutore().getEmail());
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
	 * Metodo per aggiornare le informazioni su un evento
	 * @param item Evento : evento contenente le informazioni aggiornate che verranno memorizzate
	 * 
	 */
	@Override
	public void doUpdate(Evento item) throws SQLException
	{
		try
		{
			
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(doUpdateQuery);

			statement.setString(1,String.valueOf(item.getData()));
			statement.setString(2,item.getVia());
			statement.setString(3,item.getCittà());
			statement.setString(4,item.getNome());
			statement.setString(5,item.getArgomento());
			statement.setInt(6,item.getIdEvento());
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
	 * Metodo per cancellare un evento
	 * @param item Evento : evento da cancellare
	 * @return flag : rappresenta l'esito della cancellazione
	 */
	@Override
	public boolean doDelete(Evento item) throws SQLException 
	{
		boolean flag=false;
		
		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(doDeleteQuery);
			
			statement.setString(1,String.valueOf((item.getData())));
			statement.setString(2,item.getVia());
			statement.setString(3,item.getCittà());
			statement.setInt(4,item.getIdEvento());
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
	
	public Evento doRetrieveByKey2(ArrayList<String> item_value) throws SQLException 
	{
		Evento event=null;
		String query="SELECT * FROM evento WHERE data=? AND via=? AND città=? ";
		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);
			
			for(int i=0;i<item_value.size();i++)
				statement.setString(i+1,item_value.get(i));
			set=statement.executeQuery();
			while(set.next())
			{
				event=new Evento();
				
				event.setData(LocalDate.parse(set.getString("data")));
				event.setArgomento(set.getString("argomento"));
				event.setNome(set.getString("nome"));
				event.setVia(set.getString("via"));
				event.setCittà(set.getString("città"));
				Autore scrittore=new AutoreManagement(new DriverManagerConnectionPool()).doRetrieveByKey(set.getString("email"));
				event.setAutore(scrittore);
				event.setIdEvento(set.getInt("id"));
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
		return event;	
	}
}
