package test;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.Moderatore;
import model.manager.ModeratoreManagement;
import storage.DriverManagerConnectionPool;

public class TestModeratoreManagement
{

	public static void main(String[] args)
	{
		//Testing operazioni CRUD moderatore.
		
		DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
		//Creo il manager
		ModeratoreManagement manager = new ModeratoreManagement(pool);
		//Creo un moderatore per provare le operazioni CRUD del manager di utente
		Moderatore m = new Moderatore("mod@moderatore.com", "moderatore", "Moderatore", "Mod", "mod30","JavaEE");
		try 
		{
			System.out.println("Provo il metodo doSave(Moderatore)");
			System.out.println("Inserisco il moderatore: " + m.toString());
			manager.doSave(m);
			
			System.out.println("Provo il metodo doRetriveByKey()");
			Moderatore x = manager.doRetrieveByKey(m.getEmail());
			if(x != null)
				System.out.println("Moderatore restituito dal db: " + x.toString());
			
			System.out.println("Provo il metodo doRetriveByAll()");
			ArrayList<Moderatore> moderatori = (ArrayList<Moderatore>) manager.doRetrieveAll("nome");
			System.out.println("Lista moderatori presenti nel db");
			for(Moderatore moderatore : moderatori)
			{
				System.out.println(moderatore);
			}
			
			System.out.println("Provo il metodo doUpdate(Moderatore)");
			m.setCognome("CognomeModificato");
			System.out.println("Faccio l'update del moderatore modificato: " + m.toString());
			manager.doUpdate(m);
			x = manager.doRetrieveByKey(m.getEmail());
			if(x != null)
				System.out.println("Moderatore restituito dal db: " + x.toString());
			
			System.out.println("Provo il metodo doDelete(Moderatore)");
			System.out.println("Rimuovo il moderatore: " + m.toString());
			manager.doDelete(m);
			moderatori = (ArrayList<Moderatore>) manager.doRetrieveAll("nome");
			System.out.println("Lista moderatori presenti nel db");
			for(Moderatore moderatore : moderatori)
			{
				System.out.println(moderatore);
			}
			
		}	
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}

}
