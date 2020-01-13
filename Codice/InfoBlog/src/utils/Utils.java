package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static boolean controlTypeUser(String text)
	{
		if(text == null)
		{
			return false;
		}
		switch(text)
		{
		case "Utente": return true;
		case "Autore": return true;
		case "Moderatore": return true;
		default: return false;
		}
	}
	
	public static boolean controlPassword(String text)
	{	
		if(text == null)
		{
			return false;
		}
		String regexp = "([\\wA-Z\\d]*(\\w|[A-Z]|\\d)[!%]*).{8,30}$";
		// in javascript vanno inserite tra /regexrp/ in java NO
		Pattern pt = Pattern.compile(regexp);
		Matcher mt = pt.matcher(text);
		boolean resultmatch = mt.matches();	
		return resultmatch;
	}

	public static boolean checkEmail(String text)
	{
		if(text == null)
		{
			return false;
		}
		String regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		// in javascript vanno inserite tra /regexrp/ in java NO

		Pattern pt = Pattern.compile(regexp);
		Matcher mt = pt.matcher(text);

		boolean resultmatch = mt.matches();

		return resultmatch;

	}
	
	public static boolean checkName(String text)
	{
		if(text == null)
		{
			return false;
		}
		String regexp = "^[A-Za-z]{1,20}+$";
		// in javascript vanno inserite tra /regexrp/ in java NO
		Pattern pt = Pattern.compile(regexp);
		Matcher mt = pt.matcher(text);
		boolean resultmatch = mt.matches();
		return resultmatch;

	}
	
	public static boolean checkUsername(String text)
	{
		if(text == null)
		{
			return false;
		}
		String regexp = "^[A-Za-z0-9]{5,30}+$";
		// in javascript vanno inserite tra /regexrp/ in java NO
		Pattern pt = Pattern.compile(regexp);
		Matcher mt = pt.matcher(text);
		boolean resultmatch = mt.matches();
		return resultmatch;
	}
	
}
