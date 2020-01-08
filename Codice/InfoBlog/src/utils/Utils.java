package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class Utils
{
	public static String checkLogin(HttpSession session, Cookie[] cookies)
	{
		boolean trovato = false;
		String emailUtente="";
		String emailAutore="";
		String emailModeratore="";
		if(session != null)
		{	
			//Cerco nei cookies
			if(cookies != null)
			{
				for(Cookie cookie : cookies)
				{
					switch(cookie.getName())
					{
						case "Utente":
						{
							emailUtente = cookie.getValue();
							trovato = true;	
						}break;
						
						case "Autore":
						{
							emailAutore = cookie.getValue();
							trovato = true;	
						}break;
						
						case "Moderatore":
						{
							emailModeratore = cookie.getValue();
							trovato = true;	
						}break;
						
						default:
						{
							trovato = false;
						}break;
					}
				}
			}
			//Cerco nella sessione
			if(trovato == false)
			{
				emailUtente = (String) session.getAttribute("Utente");
				emailAutore = (String) session.getAttribute("Autore");
				emailModeratore = (String) session.getAttribute("Moderatore");
				if(emailUtente != null || emailAutore != null || emailModeratore != null)
					trovato = true;
			}
		}
		if(emailUtente != null)
			return "u"+emailUtente;
		if(emailAutore != null)
			return "a"+emailAutore;
		if(emailModeratore != null)
			return "m"+emailModeratore;
		return null;
	}
}
