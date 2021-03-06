package model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import model.bean.Seguace;
import storage.DriverManagerConnectionPool;

/**
 * Classe che implementa i metodi dell'interfaccia CRUD, questi metodi permettono di fare operazioni con
 * il BD come salvataggio, recupero dati, cancellazioni relative ai seguaci
 * @author Federico
 *
 */
public class SeguiManagement implements ItemModel<Seguace, String>
{

	private DriverManagerConnectionPool forConnection;
	private Connection conn;
	private ResultSet set;
	private PreparedStatement statement;
	
	public SeguiManagement(DriverManagerConnectionPool pool) {
		this.forConnection=pool;
	}
	
	@Override
	public Seguace doRetrieveByKey(String item_value) throws SQLException {
		return null;
	}
	/**
	 * Metodo per recuperare le informazioni sui seguaci di un determinato utente
	 * @param order String: l'email dell'utente
	 * @return seguaci Collection<Seguace> : oggetto che rappresenta l'insieme degli autori seguiti dall'utente
	 *
	 */
	@Override
	public Collection<Seguace> doRetrieveAll(String order) throws SQLException {
		String query="SELECT * FROM seguace WHERE utente=?";
		ArrayList<Seguace> seguaci=new ArrayList<Seguace>();

		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);
			
			statement.setString(1, order);
			set=statement.executeQuery();
			while(set.next())
			{
				Seguace seg=new Seguace();
				seg.setAutore(set.getString("autore"));;
				seguaci.add(seg);
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
		return seguaci;
	}
	/**
	 * Metodo per memorizzare le informazioni sui seguaci di un determinato utente
	 * @param item Seguace: oggetto contenente l'autore e l'utente da memorizzare
	 *
	 */
	@Override
	public void doSave(Seguace item) throws SQLException {
		String query="INSERT INTO seguace(autore,utente) VALUES(?,?)";
		
		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);
			statement.setString(1,item.getAutore());
			statement.setString(2, item.getUtente());
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
	public void doUpdate(Seguace item) throws SQLException {
		return;
	}

	@Override
	public boolean doDelete(Seguace item) throws SQLException {
		return false;
	}

}
