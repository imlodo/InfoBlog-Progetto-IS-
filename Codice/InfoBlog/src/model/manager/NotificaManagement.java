package model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import model.bean.Notifica;
import model.bean.Stato;
import storage.DriverManagerConnectionPool;

public class NotificaManagement implements ItemModel<Notifica, Integer> {

	private static final String TABLE_NAME="notifica";
	private DriverManagerConnectionPool driver=null;	
	
	public NotificaManagement(DriverManagerConnectionPool driver){
		this.driver=driver;
	}
		
	/**
	 * Permette di recuperare un allegato tramite la sua path
	 * @param id int, l'id della notifica interessata
	 * @return notifica, l'oggetto notifica noi interessato
	 */
	@Override
	public Notifica doRetrieveByKey(Integer id) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Notifica notifica=new Notifica();
		
		String selectSQL="SELECT * FROM "+ NotificaManagement.TABLE_NAME +" WHERE id= ?";
		
		try {
			connection=(Connection)driver.getConnection();
			preparedStatement=connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				notifica.setId(resultSet.getInt("id"));
				notifica.setContenuto(resultSet.getString("contenuto"));
				try {
					notifica.setStato(Stato.valueOf(resultSet.getString("stato")));
				}
				catch(IllegalArgumentException e) {
					System.err.println("Ops, errore nel set dello stato in Notifica Management!");
					e.printStackTrace();
				}
				notifica.setAutoreEmail(resultSet.getString("moderatore"));
				notifica.setModeratoreEmail(resultSet.getString("autore"));
			}
		}finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
              driver.releaseConnection(connection);
            }
        }
        return notifica;
	}

	/**
	 * Restituisce tutti gli allegati secondo un certo ordine
	 * @param order String, tipo di ordine da effettuare
	 * @return Collection di tipo Notifiche, una collezione di tutti le notifiche ordinati 
	 */
	@Override
	public Collection<Notifica> doRetrieveAll(String order) throws SQLException {
		Connection connection  = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        ArrayList<Notifica> notifiche = new ArrayList<Notifica>();

        String selectSQL = "SELECT * FROM " + NotificaManagement.TABLE_NAME;
        
        if(order!=null && !order.equals("")){
            selectSQL+=" ORDER BY " + order;
        }
        try{
            connection = (Connection)driver.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
            	Notifica notifica=new Notifica();
            	
            	notifica.setId(resultSet.getInt("id"));
				notifica.setContenuto(resultSet.getString("contenuto"));
				try {
					notifica.setStato(Stato.valueOf(resultSet.getString("stato")));
				}
				catch(IllegalArgumentException e) {
					System.err.println("Ops, errore nel set dello stato in Notifica Management!");
					e.printStackTrace();
				}
				notifica.setAutoreEmail(resultSet.getString("moderatore"));
				notifica.setModeratoreEmail(resultSet.getString("autore"));
				
				notifiche.add(notifica);
            }
        }finally {
            try{
                if(preparedStatement!=null)
                    preparedStatement.close();
            }finally {
                driver.releaseConnection(connection);
            }
        }
        return notifiche;
	}

	/**
	 * Permette di salvare una notifica nel DB
	 * @param notifica la notifica da salvare nel DB
	 */
	@Override
	public void doSave(Notifica notifica) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String insertSQL=" INSERT INTO "+ NotificaManagement.TABLE_NAME +
				" (contenuto, id, stato, autore, moderatore) VALUES (?,?,?,?,?)";
		try{
			connection=(Connection)driver.getConnection();
			preparedStatement =  connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, notifica.getContenuto());
			preparedStatement.setInt(2, notifica.getId());
			preparedStatement.setString(3, notifica.getStato().toString());
			preparedStatement.setString(4, notifica.getAutoreEmail());
			preparedStatement.setString(5, notifica.getModeratoreEmail());	
			
			preparedStatement.executeUpdate();
			connection.commit();
		}finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            }finally{
                driver.releaseConnection(connection);
            }
		}
	}

	/**
	 * Permette di modificare una notifica salvata nel DB
	 * @param notifica Notifica, la notifica da cui prendere i dati da aggiungere nel BD
	 */
	@Override
	public void doUpdate(Notifica notifica) throws SQLException {
		PreparedStatement prepSt=null;
		Connection connection=driver.getConnection();

		String update="UPDATE notifica SET contenuto=?,stato=?,autore=?,moderatore=? WHERE id=?";
		try {
			connection=(Connection)driver.getConnection();
			prepSt=connection.prepareStatement(update);

			prepSt.setString(1, notifica.getContenuto());
			prepSt.setString(2, notifica.getStato().toString());
			prepSt.setString(3, notifica.getAutoreEmail());
			prepSt.setString(4, notifica.getModeratoreEmail());
			prepSt.setInt(5, notifica.getId());
			
			prepSt.executeUpdate();
			connection.commit();
		}finally {
			try {
				if (prepSt != null)
					prepSt.close();
			}finally{
				driver.releaseConnection(connection);
			}
		}
	}

	/**
	 * Permette la cancellazione di un notifica nel DB
	 * @param notifica la notifica da cancellare	
	 * @return true se cancellazione è andata a buon fine, falso altrimenti.
	 */
	@Override
	public boolean doDelete(Notifica notifica) throws SQLException {
		Connection connection =null;
		PreparedStatement preparedStatement = null;

		int result  = 0;

		String deleteSQL="DELETE FROM " + NotificaManagement.TABLE_NAME + " WHERE id= ?";

		try{
			connection = (Connection)driver.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1,notifica.getId());

			result = preparedStatement.executeUpdate();
			connection.commit();
		}finally {
			try{
				if(preparedStatement!=null)
					preparedStatement.close();
			}finally {
				driver.releaseConnection(connection);
			}
		}
		return (result!=0);
	}
	
	/**
	 * Cambia lo stato di un notifica da visto inviato a letto
	 */
	public void leggiNotifica(int id) throws SQLException{
		PreparedStatement prepSt=null;
		Connection connection=driver.getConnection();

		String update="UPDATE notifica SET stato=? WHERE id=?";
		try {
			connection=(Connection)driver.getConnection();
			prepSt=connection.prepareStatement(update);

			prepSt.setString(1,Stato.letto.toString());
			prepSt.setInt(2, id);
			
			prepSt.executeUpdate();
			connection.commit();
		}finally {
			try {
				if (prepSt != null)
					prepSt.close();
			}finally{
				driver.releaseConnection(connection);
			}
		}
	}
	public Collection<Notifica> doRetrieveByEmail(String email) throws SQLException{
		PreparedStatement prepSt=null;
		Connection connection=null;
		ResultSet resultSet = null;
		ArrayList<Notifica> notifiche = new ArrayList<Notifica>();
		
		String selectSQL="SELECT * FROM "+ NotificaManagement.TABLE_NAME +" WHERE autore=? OR moderatore=?";
		try {
			connection = driver.getConnection();
			prepSt = connection.prepareStatement(selectSQL);
			prepSt.setString(1, email);
			prepSt.setString(2, email);
            resultSet = prepSt.executeQuery();

            while(resultSet.next()) {
				Notifica notifica=new Notifica();
	        	
	        	notifica.setId(resultSet.getInt("id"));
				notifica.setContenuto(resultSet.getString("contenuto"));
				notifica.setStato(Stato.valueOf(resultSet.getString("stato")));
				notifica.setAutoreEmail(resultSet.getString("moderatore"));
				notifica.setModeratoreEmail(resultSet.getString("autore"));
				
				notifiche.add(notifica);
            }
		}finally {
			try {
				if (prepSt != null)
					prepSt.close();
			}finally{
				driver.releaseConnection(connection);
			}
		}
		return notifiche;
	}
}
