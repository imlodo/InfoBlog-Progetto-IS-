package model.manager;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import model.bean.Allegato;
import storage.DriverManagerConnectionPool;

/**
 * Classe che implementa i metodi dell'interfaccia CRUD, questi metodi permettono di fare operazioni con
 * il BD come salvataggio, recupero dati, cancellazioni.
 * @author Federico
 *
 */
public class AllegatoManagement implements ItemModel<Allegato, String>{

	private static final String TABLE_NAME="allegato";
	private DriverManagerConnectionPool driver=null;	
	
	public AllegatoManagement(DriverManagerConnectionPool driver) {
		this.driver=driver;
	}
	
	/**
	 * Permette di recuperare un allegato tramite la sua path
	 * @param key String, la path dell'allegato interessato
	 * @return allegato, l'oggetto l'allegato noi interessato
	 */
	@Override
	public Allegato doRetrieveByKey(String key) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Allegato allegato=new Allegato();
		
		String selectSQL="SELECT * FROM "+ AllegatoManagement.TABLE_NAME +" WHERE percorsoFile= ?";
		
		try {
			connection=driver.getConnection();
			preparedStatement=connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, key);
			
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				allegato.setPercorsoFile(resultSet.getString("percorsoFile"));
				allegato.setId(resultSet.getInt("id"));
			}
		}finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
              driver.releaseConnection(connection);
            }
        }
        return allegato;
	}
	
	/**
	 * Permette di recuperare tutti gli allegati associati ad un certo articolo
	 * @param id int, dell'articolo interessato
	 * @return Collection di tipo Allegato, una collezione di tutti gli allegati interessati 
	 */
	public Collection<Allegato> doRetrieveByID(int id) throws SQLException {
		Connection connection  = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        Collection<Allegato> allegati = new ArrayList<Allegato>();

        String selectSQL = "SELECT * FROM " + AllegatoManagement.TABLE_NAME+ " WHERE id= ?";
        
        try{
            connection = driver.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
            	Allegato allegato=new Allegato();
            	
            	allegato.setPercorsoFile(resultSet.getString("percorsoFile"));
				allegato.setId(resultSet.getInt("id"));
				
				allegati.add(allegato);
            }
        }finally {
            try{
                if(preparedStatement!=null)
                    preparedStatement.close();
            }finally {
                driver.releaseConnection(connection);
            }
        }
        return allegati;
	}
		
	/**
	 * Restituisce tutti gli allegati secondo un certo ordine
	 * @param order String, tipo di ordine da effettuare
	 * @return Collection di tipo Allegato, una collezione di tutti gli allegati ordinati 
	 */
	@Override
	public Collection<Allegato> doRetrieveAll(String order) throws SQLException {
		Connection connection  = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        Collection<Allegato> allegati = new ArrayList<Allegato>();

        String selectSQL = "SELECT * FROM " + AllegatoManagement.TABLE_NAME;
        
        if(order!=null && !order.equals("")){
            selectSQL+=" ORDER BY " + order;
        }
        try{
            connection = driver.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
            	Allegato allegato=new Allegato();
            	
            	allegato.setPercorsoFile(resultSet.getString("percorsoFile"));
				allegato.setId(resultSet.getInt("id"));
				
				allegati.add(allegato);
            }
        }finally {
            try{
                if(preparedStatement!=null)
                    preparedStatement.close();
            }finally {
                driver.releaseConnection(connection);
            }
        }
        return allegati;
	}

	/**
	 * Permette di salvare un allegato nel DB
	 * @param allegato l'allegato da salvare nel DB
	 */
	@Override
	public void doSave(Allegato allegato) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String insertSQL=" INSERT INTO "+ AllegatoManagement.TABLE_NAME +
				" (percorsoFile, id) VALUES (?,?)";
		try{
			connection=driver.getConnection();
			preparedStatement =  connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, allegato.getPercorsoFile());
			if(allegato.getId()!=0)
				preparedStatement.setInt(2, allegato.getId());
			else
				preparedStatement.setNull(2, java.sql.Types.INTEGER);
				
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
	 * Non implementato, in quanto non è ritenuto utile aggiornare informazioni di un allegato,
	 * utilizzare doDelete per cancellare l'allegato e doSave per salvare un allegato.
	 * il metodo non è implementato e di conseguenza non deve essere utilizzato
	 * @throws UnsupportedOperationException Il metodo non è implementato, da non utilizzare!
	 */
	@Override
	public void doUpdate(Allegato allegato) throws SQLException {
		throw new UnsupportedOperationException("Il metodo non è implementato, da non utilizzare!");
	}
	
	/**
	 * Permette la cancellazione di un allegato
	 * @param allegato l'allegato da cancellare	
	 * @return true se cancellazione è andata a buon fine, falso altrimenti.
	 */
	@Override
	public boolean doDelete(Allegato allegato) throws SQLException {
		Connection connection =null;
		PreparedStatement preparedStatement = null;

		int result  = 0;

		String deleteSQL="DELETE FROM " + AllegatoManagement.TABLE_NAME + " WHERE percorsoFile= ?";

		try{
			connection = driver.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1,allegato.getPercorsoFile());

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
}