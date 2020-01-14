package model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import model.bean.Messagio;
import storage.DriverManagerConnectionPool;

public class MessaggioManagment implements ItemModel<Messagio, String>{

	private DriverManagerConnectionPool forConnection;
	private Connection conn;
	private ResultSet set;
	private PreparedStatement statement;

	public MessaggioManagment(DriverManagerConnectionPool pool)
	{
		forConnection=pool;
	}

	@Override
	public Messagio doRetrieveByKey(String item_value) throws SQLException {
		String query="SELECT *\r\n" + 
				"FROM (select* from risposta union select * from messaggio) as t  \r\n" + 
				"WHERE (mittente=? OR destinatario=?) AND (mittente=? OR destinatario=?)\r\n" + 
				"ORDER BY data DESC\r\n" + 
				"LIMIT 1";
		Messagio mess=new Messagio();
		
		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);

			statement.setString(1,item_value.substring(0,item_value.indexOf(' ')));
			statement.setString(2,item_value.substring(0,item_value.indexOf(' ')));
			statement.setString(3,item_value.substring(item_value.indexOf(' ')+1, item_value.length()));
			statement.setString(4,item_value.substring(item_value.indexOf(' ')+1,item_value.length()));
			set=statement.executeQuery();
			while(set.next())
			{
				mess.setContenuto(set.getString("contenuto"));
				mess.setDestinatario(set.getString("destinatario"));
				mess.setMittente(set.getString("mittente"));
				mess.setIdMessaggio(set.getInt("id"));
				mess.setStato(set.getString("stato"));
				mess.setData(LocalDateTime.parse(set.getString("data").replace(' ','T')));
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
		return mess;
	}

	@Override
	public Collection<Messagio> doRetrieveAll(String order) throws SQLException {
		String query="SELECT *"
				+ " FROM (select* from risposta union select * from messaggio) as t \r\n" + 
				"WHERE (mittente=? OR destinatario=?) and (mittente=? OR destinatario=?)\r\n" + 
				"order by data ASC;";
		ArrayList<Messagio> messaggi=new ArrayList<Messagio>();

		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);

			statement.setString(1,order.substring(0,order.indexOf(' ')));
			statement.setString(2,order.substring(0,order.indexOf(' ')));
			statement.setString(3,order.substring(order.indexOf(' ')+1, order.length()));
			statement.setString(4,order.substring(order.indexOf(' ')+1,order.length()));
			set=statement.executeQuery();
			while(set.next())
			{
				Messagio mess=new Messagio();
				mess.setContenuto(set.getString("contenuto"));
				mess.setDestinatario(set.getString("destinatario"));
				mess.setMittente(set.getString("mittente"));
				mess.setIdMessaggio(set.getInt("id"));
				mess.setStato(set.getString("stato"));
				mess.setData(LocalDateTime.parse(set.getString("data").replace(' ','T')));
				messaggi.add(mess);
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
		return messaggi;
	}

	@Override
	public void doSave(Messagio item) throws SQLException {
		String query="";

		if(item.getTipologia().equals("risposta"))
			query="INSERT INTO risposta(contenuto,mittente,destinatario,data) VALUES (?,?,?,?)";
		else
			query="INSERT INTO messaggio(contenuto,mittente,destinatario,data) VALUES (?,?,?,?)";

		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);

			statement.setString(1,item.getContenuto());
			statement.setString(2, item.getMittente());
			statement.setString(3, item.getDestinatario());
			statement.setString(4,String.valueOf(item.getData()));
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
	public void doUpdate(Messagio item) throws SQLException 
	{
		String query="";

		if(item.getTipologia().equals("risposta"))
			query="UPDATE risposta SET stato=? WHERE id=?";
		else
			query="UPDATE messaggio SET stato=? WHERE id=?";

		try
		{
			conn=forConnection.getConnection();
			statement=conn.prepareStatement(query);

			statement.setString(1,"letto");
			statement.setInt(2,item.getIdMessaggio());
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
	public boolean doDelete(Messagio item) throws SQLException {
		return false;
	}
}	

	/*
	public static void main(String[] args) throws SQLException 
	{
		MessaggioManagment DAOMessaggi=new MessaggioManagment(new DriverManagerConnectionPool());
		Messagio mex=DAOMessaggi.doRetrieveByKey("lauro.francesco123@gmail.com lauro.francesco126@gmail.com");
		System.out.println(mex);
		
		
		
		
		<div class="right">
            <div class="top"><span>To: <span class="name">Dog Woofson</span></span></div>
             <% 
            	for(int t=0;t<conv.size();t++)
            	{
           		 %>	
            <div class="chat" data-chat="<%=contatti2.get(t)%>">
            <div class="conversation-start">
                    <span>Today, 6:48 AM</span>
                </div>
               <%  
               for(int j=0;j<conv.get(t).getMessaggi().size();j++)
            		{
            	%>
                <div class="bubble you">
                    <%=conv.get(t).getMessaggi().get(j).getContenuto() %>
                </div>
                <%}
            		}%>
            </div>
		
		
		
		
		
		String utente="lauro.francesco126@gmail.com lauro.francesco124@gmail.com";
		ArrayList<Messagio> mexs=(ArrayList<Messagio>) DAOMessaggi.doRetrieveAll(utente);
		Conversazione conv =new Conversazione();
		conv.setConvCon("lauro.francesco124@gmail.com");
		for (Messagio mex : mexs )
			conv.setMessaggi(mex);
		
		for(Messagio mex : conv.getMessaggi())
			System.out.println(mex);
		
		System.out.println("testiamo l'inserimento prima delle servlet");
		Messagio mexs=new Messagio();
		mexs.setContenuto("ciao perchè non rispondi");
		mexs.setTipologia("messaggio");
		mexs.setData(LocalDateTime.now());
		mexs.setMittente("lauro.francesco126@gmail.com");
		mexs.setDestinatario("lauro.francesco123@gmail.com");
		DAOMessaggi.doSave(mexs);
	}*/

