package test;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.Autore;
import model.manager.AutoreManagement;
import storage.DriverManagerConnectionPool;

public class TestAutoreManagement
{

	public static void main(String[] args)
	{
		//Testing operazioni CRUD utente.
		
		DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
		//Creo il manager
		AutoreManagement manager = new AutoreManagement(pool);
		//Creo un utente per provare le operazioni CRUD del manager di utente
		Autore a = new Autore("autore@author.com", "autore", "Autore", "Autore", "autore30");
		try 
		{
			System.out.println("Provo il metodo doSave(Autore)");
			System.out.println("Inserisco l'autore: " + a.toString());
			manager.doSave(a);
			
			System.out.println("Provo il metodo doRetriveByKey()");
			Autore x = manager.doRetrieveByKey(a.getEmail());
			if(x != null)
				System.out.println("Autore restituito dal db: " + x.toString());
			
			System.out.println("Provo il metodo doRetriveByAll()");
			ArrayList<Autore> autori = (ArrayList<Autore>) manager.doRetrieveAll("nome");
			System.out.println("Lista autori presenti nel db");
			for(Autore autore : autori)
			{
				System.out.println(autore);
			}
			
			System.out.println("Provo il metodo doUpdate(Autore)");
			a.setCognome("Modificato");
			System.out.println("Faccio l'update dell'autore modificato: " + a.toString());
			manager.doUpdate(a);
			x = manager.doRetrieveByKey(a.getEmail());
			if(x != null)
				System.out.println("Autore restituito dal db: " + x.toString());
			
			System.out.println("Provo il metodo doDelete(Autore)");
			System.out.println("Rimuovo l'autore: " + a.toString());
			manager.doDelete(a);
			autori = (ArrayList<Autore>) manager.doRetrieveAll("nome");
			System.out.println("Lista autori presenti nel db");
			for(Autore utente : autori)
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
