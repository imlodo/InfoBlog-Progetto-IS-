package storage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DriverManagerConnectionPool  {

	private List<Connection> freeDbConnections;

	static {	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("DB driver not found:"+ e.getMessage());
		} 
	}
	
	public DriverManagerConnectionPool() {
		freeDbConnections = new LinkedList<Connection>();
	}
	
	private synchronized Connection createDBConnection() throws SQLException {
		Connection newConnection = null;
		
		
		String url = "jdbc:mysql://localhost:3306/InfoBlog?useSSL=false&serverTimezone=UTC&requireSSL=false&allowPublicKeyRetrieval=true";
		

		newConnection = DriverManager.getConnection(url,"dbUser","InfoBlog");

		newConnection.setAutoCommit(false);
		return newConnection;
	}


	public synchronized Connection getConnection() throws SQLException {
		Connection connection;

		if (!freeDbConnections.isEmpty()) {
			connection = freeDbConnections.get(0);
			freeDbConnections.remove(0);

			try {
				if (connection.isClosed())
					connection = getConnection();
			} catch (SQLException e) {
				connection.close();
				connection = getConnection();
			}
		} else {
			connection = createDBConnection();		
		}

		return connection;
	}

	public synchronized void releaseConnection(Connection connection) throws SQLException {
		if(connection != null) freeDbConnections.add(connection);
	}
}