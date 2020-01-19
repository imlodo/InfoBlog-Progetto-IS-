package model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import storage.DriverManagerConnectionPool;

public class ConversazioniManagment 
{	
	/**
	 * Metodo per recuperare i destinatari di un utente
	 * @param idUtente String identificativo dell'utente secondo questa convenzione M per messaggio R per risposta
	 * @return nomi ArrayList<String> insieme dei contatti dell'utente
	 */
	public static ArrayList<String> getUtenti(String idUtente) throws SQLException 
	{
		ArrayList<String> nomi=new ArrayList<String>();
		String query="";
		DriverManagerConnectionPool forConnection=null;
		Connection conn=null;
		PreparedStatement statement=null;
		ResultSet set;
		
		if(idUtente.indexOf("M:")!=-1)
			query="select distinct(destinatario)\r\n" + 
					"from messaggio \r\n" + 
					"where mittente=?";
		else
			query="select distinct(mittente)\r\n" + 
					"from messaggio \r\n" + 
					"where destinatario=?";
		
		try
		{
			forConnection=new DriverManagerConnectionPool();
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);
			
			statement.setString(1,idUtente.substring(idUtente.indexOf(':')+1));
			set=statement.executeQuery();
			while(set.next())	
				nomi.add(set.getString(1));
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
		return nomi;
	}	
}
