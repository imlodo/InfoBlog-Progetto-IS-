package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import model.bean.Articolo;
import model.bean.Moderatore;
import model.manager.ArticoloManagement;
import model.manager.ModeratoreManagement;

public class Utils
{
	public static String checkLogin(HttpSession session, Cookie[] cookies)
	{
		boolean trovato = false;
		String emailUtente=null;
		String emailAutore=null;
		String emailModeratore=null;
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
		String regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$^+=!*()@%&]).{8,30}$";
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
		String regexp = "^[A-Z][a-z][^#!@&<>\\[\\]\\\"~;$^%{}?{0-9}]{0,30}$";
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
		String regexp = "^[A-Za-z0-9.]{5,30}+$";
		// in javascript vanno inserite tra /regexrp/ in java NO
		Pattern pt = Pattern.compile(regexp);
		Matcher mt = pt.matcher(text);
		boolean resultmatch = mt.matches();
		return resultmatch;
	}

	public static boolean checkTitolo(String text)
	{
		if(text == null)
			return false;
		String regexp = "[A-Z][a-zA-Z0-9][^#@&<>\\\"~;$^%{}?]{4,50}$";
		// in javascript vanno inserite tra /regexrp/ in java NO
		Pattern pt = Pattern.compile(regexp);
		Matcher mt = pt.matcher(text);
		boolean resultmatch = mt.matches();
		return resultmatch;
	}

	public static boolean checkContenuto(String text)
	{
		if(text == null)
			return false;
		String regexp = "[a-zA-Z0-9 #.:(),!@&<>'�\\�\\�\\�\\�\\\"~;$^%{}?]{200,15000}$";
		// in javascript vanno inserite tra /regexrp/ in java NO
		Pattern pt = Pattern.compile(regexp);
		Matcher mt = pt.matcher(text);
		boolean resultmatch = mt.matches();
		return resultmatch;
	}

	public static boolean checkCategoria(String text)
	{
		if(text == null)
		{
			return false;
		}
		//controllare se la categoria text fa parte delle categorie prestabilite
		return true;
	}
	public static boolean checkFormato(String text)
	{
		String ext = text.substring(text.lastIndexOf(".")+1);
		if(ext.equals("pdf") || ext.equals("doc") || ext.equals("docx"))
			return true;
		return false;
	}

	public static boolean checkCommento(String text)
	{
		if(text == null)
		{
			return false;
		}
		String regexp = "^[A-Za-z0-9]{8,300}$";
		// in javascript vanno inserite tra /regexrp/ in java NO
		Pattern pt = Pattern.compile(regexp);
		Matcher mt = pt.matcher(text);
		boolean resultmatch = mt.matches();
		return resultmatch;
	}

	public static boolean checkNumeroStelle(String text)
	{
		if (text == null) 
		{
			return false;
		}
		try 
		{
			Integer d = Integer.parseInt(text.trim());
			if(d.intValue() < 0 || d.intValue() > 5)
				return false;
		} 
		catch (NumberFormatException nfe) 
		{
			return false;
		}
		return true;
	}
	public static boolean isNumeric(String strNum) 
	{
		if (strNum == null) 
		{
			return false;
		}
		try 
		{
			Double.parseDouble(strNum.trim());
		} 
		catch (NumberFormatException nfe) 
		{
			return false;
		}
		return true;
	}

	public static Moderatore getRiceventeNotifica(ModeratoreManagement moderatoreDM, String categoria_articolo, ArticoloManagement articoloDM)
	{
		//Moderatore management fare query
		ArrayList<Moderatore> moderatori;
		try
		{
			moderatori = (ArrayList<Moderatore>) moderatoreDM.doRetrieveAll("categoriaModerazione");
			moderatori.removeIf(x->{
				if(x.getCategoria_moderazione().equalsIgnoreCase(categoria_articolo))
					return false;
				return true;
			});
			Moderatore mod0 = moderatori.size() > 0 ? moderatori.get(0) : null;
			Moderatore modRiceventeNotifica = null;
			if(mod0 != null)
			{
				modRiceventeNotifica = mod0;
				ArrayList<Articolo> articoli;
				articoli = (ArrayList<Articolo>) articoloDM.doRetrieveAll("");
				articoli.removeIf(x->{
					Moderatore m = x.getModeratore();
					if(m != null)
					{
						if(m.getEmail().equals(mod0.getEmail()))
							return false;
					}
					return true;
				});
				int numeroArticoli = articoli.size();
				moderatori.remove(0);
				//Cerco il moderatore con il numero di articoli moderati per quella categoria pi� piccolo
				for(Moderatore mod : moderatori)
				{
					articoli = (ArrayList<Articolo>) articoloDM.doRetrieveAll("");
					articoli.removeIf(x->{
						Moderatore m = x.getModeratore();
						if(m != null)
						{
							if(m.getEmail().equals(mod0.getEmail()))
								return false;
						}
						return true;
					});
					int num = articoli.size();
					if(num < numeroArticoli)
					{
						modRiceventeNotifica = mod;
						numeroArticoli = num;
					}
				}
			}
			return modRiceventeNotifica;
		}
		catch (SQLException e)
		{
			return null;
		}

	}
}