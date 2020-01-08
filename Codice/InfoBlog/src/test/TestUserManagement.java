package test;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.Utente;
import model.manager.UserManagement;
import storage.DriverManagerConnectionPool;

public class TestUserManagement
{

	public static void main(String[] args)
	{
		//Testing operazioni CRUD utente.
		
		DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
		//Creo il manager
		UserManagement manager = new UserManagement(pool);
		//Creo un utente per provare le operazioni CRUD del manager di utente
		Utente u = new Utente("tonino2@live.it", "toto30", "Tony", "Lodo", "lodo30");
		try 
		{
			System.out.println("Provo il metodo doSave(Utente)");
			System.out.println("Inserisco l'utente: " + u.toString());
			manager.doSave(u);
			
			System.out.println("Provo il metodo doRetriveByKey()");
			Utente x = manager.doRetrieveByKey(u.getEmail());
			if(x != null)
				System.out.println("Utente restituito dal db: " + x.toString());
			
			System.out.println("Provo il metodo doRetriveByAll()");
			ArrayList<Utente> utenti = (ArrayList<Utente>) manager.doRetrieveAll("nome");
			System.out.println("Lista utenti presenti nel db");
			for(Utente utente : utenti)
			{
				System.out.println(utente);
			}
			
			System.out.println("Provo il metodo doUpdate(Utente)");
			u.setCognome("Migliaro");
			System.out.println("Faccio l'update dell'utente modificato: " + u.toString());
			manager.doUpdate(u);
			x = manager.doRetrieveByKey(u.getEmail());
			if(x != null)
				System.out.println("Utente restituito dal db: " + x.toString());
			
			System.out.println("Provo il metodo doDelete(Utente)");
			System.out.println("Rimuovo l'utente: " + u.toString());
			manager.doDelete(u);
			utenti = (ArrayList<Utente>) manager.doRetrieveAll("nome");
			System.out.println("Lista utenti presenti nel db");
			for(Utente utente : utenti)
			{
				System.out.println(utente);
			}
			
		}	
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}

}
