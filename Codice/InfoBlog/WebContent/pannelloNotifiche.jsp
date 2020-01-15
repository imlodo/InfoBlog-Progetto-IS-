
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Crea una notifica</title>
	<link rel="stylesheet" href="css/pannelloNotifiche.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
</head>
<body>
	<p>Modulo Notifica</p>
	<form action="InviaNotificaControl" method="post" class="pann">
		<label>Email moderatore:</label>
		<input type="text" name="emailModeratore" required>
		<label>Email Autore:</label>
		<input type="text" name="emailAutore" required>
		<label>Contenuto notifica:</label> <textarea name="contenuto" rows="4" cols="40"></textarea>
		<input type="submit" value="Invia notifica" class="button">
	</form>
</body>
</html>