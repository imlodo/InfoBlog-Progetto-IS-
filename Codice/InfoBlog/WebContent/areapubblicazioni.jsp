<%@page import="model.bean.Articolo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utils.Utils"%>
<%@page import="storage.DriverManagerConnectionPool"%>
<%@page import="model.manager.ArticoloManagement"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Miei articoli</title>
</head>
<%

	String email = Utils.checkLogin(request.getSession(), request.getCookies());
	ArrayList<Articolo> articoli = new ArrayList<Articolo>();
	if(email != null)
	{
		DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
		ArticoloManagement articoloDM = new ArticoloManagement(pool);
		articoli = (ArrayList<Articolo>) articoloDM.doRetrieveAll("a:"+email.substring(1));
	}
%>
<body>
	<%
	for(Articolo articolo: articoli)
	{
	%>
		<div style="border:5px solid black;margin-bottom:10px;">
		<label style=font-weight:bold;>Id Articolo</label><br>
		<label><%=articolo.getId() %></label><br>
		<label style=font-weight:bold;>Titolo Articolo</label><br>
		<label><%=articolo.getTitolo() %></label><br>
		<label style=font-weight:bold;>Contenuto Articolo</label><br>
		<textarea><%=articolo.getContenuto()%></textarea><br>
		<label style=font-weight:bold;>Data Articolo</label><br>
		<label><%=articolo.getData() %></label><br>
		<label style=font-weight:bold;>Autore Articolo</label><br>
		<label><%=articolo.getAutore() %></label><br>
		<label style=font-weight:bold;>Moderatore Articolo</label><br>
		<label><%=articolo.getModeratore() %></label><br>
		<label style=font-weight:bold;>Stato Articolo</label><br>
		<label><%=articolo.getStato() %></label><br>
		<form action="ModificaArticoloControl" method="POST">
			<input name='id' value=<%=articolo.getId() %> type="hidden">
			<input name='action' value="richiestaDiModifica" type="hidden">
			<button type="submit">Modifica</button>
		</form>
		</div>
	<%
	}
	%>
</body>
</html>